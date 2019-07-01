package droiddevelopers254.droidconke

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.appbar.AppBarLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import droiddevelopers254.droidconke.ui.authentication.AuthenticateUserActivity
import droiddevelopers254.droidconke.ui.feedback.EventFeedbackActivity
import droiddevelopers254.droidconke.utils.SharedPref.PREF_NAME
import droiddevelopers254.droidconke.utils.SharedPref.TOKEN_SENT
import droiddevelopers254.droidconke.viewmodels.HomeViewModel
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {
    private val sharedPreferences: SharedPreferences by lazy { getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE) }
    private val firebaseRemoteConfig: FirebaseRemoteConfig by inject()
    private val firebaseMessaging: FirebaseMessaging by inject()
    private val firebaseAuth: FirebaseAuth by inject()
    private lateinit var params: AppBarLayout.LayoutParams
    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)

        setupNavigation()
        setupActionBarWithNavController(findNavController(R.id.navHostFragment), drawer_layout)

        setupNotifications()

        //observe live data emitted by view model
        observeLiveData()

    }

    private fun setupNotifications() {
        firebaseMessaging.subscribeToTopic("all")

        if (BuildConfig.DEBUG) {
            firebaseMessaging.subscribeToTopic("debug")
        }
    }

    private fun setupNavigation() {
        val navController = Navigation.findNavController(this, R.id.navHostFragment)
        // Update action bar to reflect navigation
        NavigationUI.setupActionBarWithNavController(this, navController, drawer_layout)
        NavigationUI.setupWithNavController(nav_view, navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.sessionDetailsFragment -> {
                    toolbar.visibility = View.GONE
                }
                else -> toolbar.visibility = View.VISIBLE
            }
        }

    }

    private fun observeLiveData() {
        homeViewModel.getUpdateTokenResponse().observe(this, Observer {
            when {
                it -> {
                    sharedPreferences.edit().putInt(TOKEN_SENT, 1).apply()
                }
                else -> {
                    sharedPreferences.edit().putInt(TOKEN_SENT, 0).apply()
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_sign_out, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                unsubscribeNotifications()
                firebaseAuth.signOut()
                startActivity(Intent(this, AuthenticateUserActivity::class.java))
                finish()
                return true
            }
            R.id.action_feedback -> {
                startActivity(Intent(this, EventFeedbackActivity::class.java))
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun unsubscribeNotifications() = lifecycleScope.launch {
        firebaseMessaging.unsubscribeFromTopic("all").await()
        if (BuildConfig.DEBUG) {
            firebaseMessaging.unsubscribeFromTopic("debug").await()
        }

        // TODO Add unsubscription from favourites
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    override fun onSupportNavigateUp() =
            Navigation.findNavController(this, R.id.navHostFragment).navigateUp()

    companion object {
        var navItemIndex = 1 //controls toolbar titles and icons
        var fabVisible = true
    }
}
