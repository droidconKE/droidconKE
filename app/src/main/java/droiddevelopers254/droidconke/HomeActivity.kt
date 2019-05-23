package droiddevelopers254.droidconke

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.appbar.AppBarLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import droiddevelopers254.droidconke.utils.SharedPref.FIREBASE_TOKEN
import droiddevelopers254.droidconke.utils.SharedPref.PREF_NAME
import droiddevelopers254.droidconke.utils.SharedPref.TOKEN_SENT
import droiddevelopers254.droidconke.utils.nonNull
import droiddevelopers254.droidconke.utils.observe
import droiddevelopers254.droidconke.viewmodels.HomeViewModel
import droiddevelopers254.droidconke.ui.authentication.AuthenticateUserActivity
import droiddevelopers254.droidconke.ui.feedback.EventFeedbackActivity
import droiddevelopers254.droidconke.ui.info.InfoFragment
import droiddevelopers254.droidconke.ui.venuemap.MapFragment
import droiddevelopers254.droidconke.ui.schedule.ScheduleFragment
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.android.ext.android.inject

class HomeActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var firebaseRemoteConfig: FirebaseRemoteConfig
    private lateinit var params: AppBarLayout.LayoutParams
    private val homeViewModel: HomeViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        //setup defaults for remote config
        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

        //check whether refresh token is sent to db
        val tokenSent = sharedPreferences.getInt(TOKEN_SENT, 0)

        when (tokenSent) {
            0 -> {
                val refreshToken = sharedPreferences.getString(FIREBASE_TOKEN, null)
                val firebaseUser = FirebaseAuth.getInstance().currentUser
                //update in firestore
                homeViewModel.updateToken(firebaseUser?.uid.toString(), refreshToken!!)
            }
        }

        when {
            FirebaseAuth.getInstance().currentUser != null -> //load user profile image
                Glide.with(applicationContext).load(FirebaseAuth.getInstance().currentUser?.photoUrl)
                        .thumbnail(Glide.with(applicationContext).load(FirebaseAuth.getInstance().currentUser?.photoUrl))
                        .apply(RequestOptions()
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.ALL))
                        .transition(DrawableTransitionOptions()
                                .crossFade())
                        .into(accountImg)
        }
        navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    navItemIndex = 0

                    //hide views not required by these fragment
                    accountImg.visibility = View.GONE
                    //fab.hide();
                    toolbarTitleText.setText(R.string.info_title)

                    //activate the hide toolbar
                    params = toolbar.layoutParams as AppBarLayout.LayoutParams
                    params.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS

                    val infoFragment = InfoFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.content_home, infoFragment)
                            .commit()
                }
                R.id.navigation_schedule -> {

                    navItemIndex = 1

                    //hide views not required by these fragment
                    accountImg.visibility = View.VISIBLE
                    toolbarTitleText.setText(R.string.schedule_title)

                    //activate the hide toolbar
                    params = toolbar.layoutParams as AppBarLayout.LayoutParams
                    params.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS

                    val scheduleFragment = ScheduleFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.content_home, scheduleFragment)
                            .commit()

                }
                R.id.navigation_map -> {

                    navItemIndex = 2

                    //hide views not required by these fragment
                    accountImg!!.visibility = View.GONE
                    // fab.hide();
                    toolbarTitleText.setText(R.string.map_title)

                    //show toolbar if it was hidden
                    params = toolbar.layoutParams as AppBarLayout.LayoutParams
                    params.scrollFlags = 0  // clear all scroll flags

                    val mapFragment = MapFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.content_home, mapFragment)
                            .commit()
                }
            }
            false
        }
        navigation.selectedItemId = R.id.navigation_schedule

        //observe live data emitted by view model
        observeLiveData()

    }

    private fun observeLiveData() {
        homeViewModel.getUpdateTokenResponse().nonNull().observe(this) {
            when {
                it -> {
                    sharedPreferences.edit().putInt(TOKEN_SENT, 1).apply()
                }
                else -> {
                    sharedPreferences.edit().putInt(TOKEN_SENT, 0).apply()
                }
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_sign_out, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        when (id) {
            R.id.action_settings -> {
                FirebaseAuth.getInstance().signOut()
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

    companion object {
        var navItemIndex = 1 //controls toolbar titles and icons
        var fabVisible = true
    }
}
