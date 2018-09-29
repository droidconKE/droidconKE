package droiddevelopers254.droidconke.views.activities;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.Collections;

import butterknife.BindView;
import droiddevelopers254.droidconke.HomeActivity;
import droiddevelopers254.droidconke.R;
import droiddevelopers254.droidconke.models.UserModel;
import droiddevelopers254.droidconke.viewmodels.AuthenticateUserViewModel;

public class AuthenticateUserActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    FirebaseAuth auth;
    SignInButton googleSignInBtn;
    private SweetAlertDialog pDialog;
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

        // Progress dialog
        pDialog = new SweetAlertDialog(AuthenticateUserActivity.this,SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#863B96"));
        pDialog.setCancelable(false);

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
        googleSignInBtn.setOnClickListener(view -> {
            pDialog.setTitleText("Signing in");
            showDialog();
            signInUser();
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {
            hideDialog();
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == RESULT_OK) {
                //save the user now to db
                navigateToHome();

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
    private void navigateToHome() {
        Intent intent = new Intent(AuthenticateUserActivity.this,HomeActivity.class);
        startActivity(intent);
        finish();

    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();

    } 

}
