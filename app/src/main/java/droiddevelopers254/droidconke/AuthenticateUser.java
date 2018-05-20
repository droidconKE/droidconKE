package droiddevelopers254.droidconke;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.User;
import com.google.android.gms.common.SignInButton;
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
import java.util.Collections;

import butterknife.BindView;
import droiddevelopers254.droidconke.models.UserModel;

public class AuthenticateUser extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    FirebaseUser firebaseUser;
    FirebaseAuth auth;
    SignInButton googleSignInBtn;
    @BindView(R.id.coorAuthUser)
    CoordinatorLayout snackBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set layout tu fullcreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //transparent status bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.mdtp_transparent_black));
        }

        setContentView(R.layout.activity_authenticate_user);
        googleSignInBtn=findViewById(R.id.googleSignInBtn);

        //check whether the user is signed in first
         auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            // already signed in
            navigateToHome();
        } else {
            // not signed in
           showUI();
        }

    }

    private void showUI() {
        googleSignInBtn.setOnClickListener(view -> signInUser());
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

            } else {
                // Sign in failed
                if (response == null) {
                    // UserModel pressed back button
                   Toast.makeText(getApplicationContext(),"You pressed back button before log in",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                   Toast.makeText(getApplicationContext(),"Network Error",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                   Toast.makeText(getApplicationContext(),"Please try again",Toast.LENGTH_SHORT).show();

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
                                Collections.singletonList(
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
                    navigateToHome();

                } else {
                    UserModel user = new UserModel();
                    user.setEmail(currentUser.getEmail());
                    user.setUser_id(currentUser.getUid());
                    user.setUser_name(currentUser.getDisplayName());
                    user.setPhoto_url(String.valueOf(currentUser.getPhotoUrl()));

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
                .addOnSuccessListener(aVoid -> navigateToHome())
                .addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Unable to log you in at this time, please try again", Toast.LENGTH_SHORT).show());
    }

    private void navigateToHome() {
        Intent intent = new Intent(AuthenticateUser.this,HomeActivity.class);
        startActivity(intent);
        finish();

    }

}
