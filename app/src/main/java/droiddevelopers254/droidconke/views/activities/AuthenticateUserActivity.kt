package droiddevelopers254.droidconke.views.activities

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog
import droiddevelopers254.droidconke.HomeActivity
import droiddevelopers254.droidconke.R
import kotlinx.android.synthetic.main.content_authenticate_user.*
import org.jetbrains.anko.toast

class AuthenticateUserActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    private var pDialog: SweetAlertDialog? = null
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_NoActionBar)
        super.onCreate(savedInstanceState)

        //set layout tu fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        //transparent status bar
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                window.statusBarColor = ContextCompat.getColor(this, R.color.mdtp_transparent_black)
            }
        }

        setContentView(R.layout.activity_authenticate_user)

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)


        // Progress dialog
        pDialog = SweetAlertDialog(this@AuthenticateUserActivity, SweetAlertDialog.PROGRESS_TYPE)
        pDialog?.progressHelper?.barColor = Color.parseColor("#863B96")
        pDialog?.setCancelable(false)

        //check whether the user is signed in first
        auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            // already signed in
            navigateToHome()
        } else {
            // not signed in
            showUI()
        }
    }

    private fun showUI() {
        googleSignInBtn.setOnClickListener {
            pDialog?.titleText = "Signing in"
            showDialog()
            signInUser()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            RC_SIGN_IN -> {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)
                    account?.let { firebaseAuthWithGoogle(it) }
                } catch (e: ApiException) {
                    // Google Sign In failed
                    hideDialog()
                    toast("Google sign in failed")
                }
            }
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this) {
                    when {
                        it.isSuccessful -> {
                            // Sign in success, update UI with the signed-in user's information
                            val user = auth.currentUser
                            navigateToHome()
                        }
                        else -> // If sign in fails, display a message to the user.
                            toast("Authentication Failed.")
                    }
                    hideDialog()
                }
    }

    //function to log in
    private fun signInUser() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun navigateToHome() {
        val intent = Intent(this@AuthenticateUserActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()

    }

    private fun showDialog() {
        when {
            !pDialog!!.isShowing -> pDialog?.show()
        }
    }

    private fun hideDialog() {
        when {
            pDialog!!.isShowing -> pDialog?.dismiss()
        }

    }

    companion object {
        private const val RC_SIGN_IN = 9001
    }
}
