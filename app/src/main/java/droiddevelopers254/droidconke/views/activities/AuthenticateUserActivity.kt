package droiddevelopers254.droidconke.views.activities

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog
import droiddevelopers254.droidconke.HomeActivity
import droiddevelopers254.droidconke.R
import droiddevelopers254.droidconke.viewmodels.AuthenticateUserViewModel
import kotlinx.android.synthetic.main.content_authenticate_user.*

class AuthenticateUserActivity : AppCompatActivity() {
    private var firebaseUser: FirebaseUser? = null
    lateinit var auth: FirebaseAuth
    private var pDialog: SweetAlertDialog? = null
    private lateinit var authenticateUserViewModel: AuthenticateUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //set layout tu fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        //transparent status bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            window.statusBarColor = ContextCompat.getColor(this, R.color.mdtp_transparent_black)
        }

        setContentView(R.layout.activity_authenticate_user)

        authenticateUserViewModel = ViewModelProviders.of(this).get(AuthenticateUserViewModel::class.java)

        // Progress dialog
        pDialog = SweetAlertDialog(this@AuthenticateUserActivity, SweetAlertDialog.PROGRESS_TYPE)
        pDialog!!.progressHelper.barColor = Color.parseColor("#863B96")
        pDialog!!.setCancelable(false)

        //check whether the user is signed in first
        auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            // already signed in
            navigateToHome()
        } else {
            // not signed in
            showUI()
        }
        //observe livedata emitted by view model
        authenticateUserViewModel.authenticateResponse.observe(this, Observer{
            if (it != null) {
                if (it.isUserExists) {
                    navigateToHome()
                } else {
                    if (it.error != null) {
                        handleError(it.error)
                    }
                }
            }
        })

    }

    private fun handleError(error: String?) {
        Toast.makeText(applicationContext, error, Toast.LENGTH_SHORT).show()
    }

    private fun showUI() {
        googleSignInBtn.setOnClickListener {
            pDialog!!.titleText = "Signing in"
            showDialog()
            signInUser()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {
            hideDialog()
            val response = IdpResponse.fromResultIntent(data)

            // Successfully signed in
            if (resultCode == Activity.RESULT_OK) {
                //save the user now to db
                firebaseUser = auth.currentUser
                if (firebaseUser != null) {
                    authenticateUserViewModel.authenticateUser(firebaseUser!!)

                } else {
                    Toast.makeText(applicationContext, "User is null", Toast.LENGTH_LONG).show()
                }

            } else {
                // Sign in failed
                if (response == null) {
                    // UserModel pressed back button
                    Toast.makeText(applicationContext, "You pressed back button before log in", Toast.LENGTH_SHORT).show()
                    return
                }

                if (response.errorCode == ErrorCodes.NO_NETWORK) {
                    Toast.makeText(applicationContext, "Network Error", Toast.LENGTH_SHORT).show()
                    return
                }

                if (response.errorCode == ErrorCodes.UNKNOWN_ERROR) {
                    Toast.makeText(applicationContext, "Please try again", Toast.LENGTH_SHORT).show()

                }
            }

        }
    }

    //function to log in
    private fun signInUser() {

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setIsSmartLockEnabled(false)
                        .setAvailableProviders(
                                listOf(AuthUI.IdpConfig.GoogleBuilder().build()))
                        .build(),
                RC_SIGN_IN)
    }
    private fun navigateToHome() {
        val intent = Intent(this@AuthenticateUserActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()

    }

    private fun showDialog() {
        if (!pDialog!!.isShowing)
            pDialog!!.show()
    }

    private fun hideDialog() {
        if (pDialog!!.isShowing)
            pDialog!!.dismiss()

    }

    companion object {
        private const val RC_SIGN_IN = 123
    }

}
