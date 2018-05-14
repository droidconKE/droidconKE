package droiddevelopers254.droidconke;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

import butterknife.BindView;
import droiddevelopers254.droidconke.models.UserModel;

public class AuthenticateUser extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    FirebaseUser firebaseUser;
    FirebaseAuth auth;
    @BindView(R.id.coorAuthUser)
    CoordinatorLayout snackBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticate_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //check whether the user is signed in first
         auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            // already signed in
            navigateToHome();
        } else {
            // not signed in
            signInUser();
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == RESULT_OK) {

                //save the user now to db
                firebaseUser = auth.getCurrentUser();

                if(firebaseUser!=null){
                    checkUserExistence(firebaseUser);

                }else {
                    Toast.makeText(getApplicationContext(),"User is null",Toast.LENGTH_LONG).show();
                }

               // navigateToHome();

                return;
            } else {
                // Sign in failed
                if (response == null) {
                    // UserModel pressed back button
                    showSnackbar("You pressed back button before log in");
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                    showSnackbar("Network Error");
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    showSnackbar("PLease try again");
                    return;
                }
            }

        }
    }

    //function to log in
    private void signInUser(){

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setIsSmartLockEnabled(false)
                        .setAvailableProviders(
                                Arrays.asList(
                                        new AuthUI.IdpConfig.EmailBuilder().build(),
                                        new AuthUI.IdpConfig.GoogleBuilder().build()
                                ))
                        .build(),
                RC_SIGN_IN);
    }

    private void showSnackbar(String message){

        Snackbar.make(snackBarView,message,Snackbar.LENGTH_SHORT).show();

    }

    private void checkUserExistence(final FirebaseUser currentUser) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference users = database.getReference("users");
        users.child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {

                    Toast.makeText(AuthenticateUser.this,"UserModel exists " + currentUser.getDisplayName(),
                            Toast.LENGTH_LONG).show();

                    navigateToHome();

                } else {


                    Toast.makeText(AuthenticateUser.this,"NO user",Toast.LENGTH_LONG).show();
                   // User user = new User();

                    UserModel user = new UserModel();

                    user.setEmail(currentUser.getEmail());
                    user.setUser_id(currentUser.getUid());

                    saveCurrentUser(currentUser, user);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Unable to log you in at this time, please try again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveCurrentUser(final FirebaseUser currentUser,UserModel user) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference users = database.getReference("users");
        users.child(currentUser.getUid()).setValue(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        navigateToHome();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Unable to log you in at this time, please try again", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void navigateToHome() {

        Intent intent = new Intent(AuthenticateUser.this,HomeActivity.class);
        startActivity(intent);
        finish();

    }

}
