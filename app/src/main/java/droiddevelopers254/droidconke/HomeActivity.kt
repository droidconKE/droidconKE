package droiddevelopers254.droidconke

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.chip.Chip
import android.support.design.widget.AppBarLayout
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.CoordinatorLayout
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.remoteconfig.FirebaseRemoteConfig

import java.util.ArrayList

import butterknife.BindView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import droiddevelopers254.droidconke.adapters.ChipViewAdapter
import droiddevelopers254.droidconke.models.FiltersModel
import droiddevelopers254.droidconke.ui.BottomNavigationBehaviour
import droiddevelopers254.droidconke.viewmodels.HomeViewModel
import droiddevelopers254.droidconke.views.activities.AuthenticateUserActivity
import droiddevelopers254.droidconke.views.fragments.InfoFragment
import droiddevelopers254.droidconke.views.fragments.MapFragment
import droiddevelopers254.droidconke.views.fragments.ScheduleFragment

import droiddevelopers254.droidconke.utils.SharedPref.FIREBASE_TOKEN
import droiddevelopers254.droidconke.utils.SharedPref.PREF_NAME
import droiddevelopers254.droidconke.utils.SharedPref.TOKEN_SENT
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.filters_bottom_sheet.*

class HomeActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private var refreshToken: String? = null
    private val builder: AlertDialog.Builder? = null
    internal var signInBtn: Button? = null
    private var firebaseUser: FirebaseUser? = null
    lateinit var auth: FirebaseAuth
    private lateinit var firebaseRemoteConfig: FirebaseRemoteConfig
    private var tokenSent: Int = 0
    private var bottomSheetBehavior: BottomSheetBehavior<*>? = null
    internal var categoryChosen: Boolean = false
    private lateinit var params: AppBarLayout.LayoutParams
    lateinit var homeViewModel: HomeViewModel
    private var typeFilterList: List<FiltersModel> = ArrayList()
    private var topicFilterList: List<FiltersModel> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        auth = FirebaseAuth.getInstance()
        //setup defaults for remote config
        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

        //check whether refresh token is sent to db
        tokenSent = sharedPreferences.getInt(TOKEN_SENT, 0)
        if (tokenSent == 0) {
            refreshToken = sharedPreferences.getString(FIREBASE_TOKEN, null)
            firebaseUser = auth.currentUser
            //update in firestore
            homeViewModel.updateToken(firebaseUser!!.uid, refreshToken!!)
        }
        //get filters from firebase
        getTopicFilters()
        getTypeFilters()

        val layoutParams = navigation.layoutParams as CoordinatorLayout.LayoutParams
        layoutParams.behavior = BottomNavigationBehaviour()

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView!!)
        //        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
        //            @Override
        //            public void onStateChanged(@NonNull View bottomSheet, int newState) {
        //                // this part hides the button immediately and waits bottom sheet
        //                // to collapse to show
        //                if (BottomSheetBehavior.STATE_EXPANDED == newState) {
        //                    fab.animate().scaleX(0).scaleY(0).setDuration(200).start();
        //                } else if (BottomSheetBehavior.STATE_COLLAPSED == newState) {
        //                    fab.animate().scaleX(1).scaleY(1).setDuration(200).start();
        //                }
        //            }
        //
        //            @Override
        //            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        //
        //            }
        //        });

        if (auth.currentUser != null) {
            //load user profile image
            Glide.with(applicationContext).load(auth.currentUser!!.photoUrl)
                    .thumbnail(Glide.with(applicationContext).load(auth.currentUser!!.photoUrl))
                    .apply(RequestOptions()
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.ALL))
                    .transition(DrawableTransitionOptions()
                            .crossFade())
                    .into(accountImg)
        }

        navigation.setOnNavigationItemSelectedListener{
            when (it.itemId) {
                R.id.navigation_home -> {
                    navItemIndex = 0

                    //hide views not required by these fragment
                    accountImg.visibility = View.GONE
                    //fab.hide();
                    toolbarTitleText!!.setText(R.string.info_title)

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
                    accountImg!!.visibility = View.VISIBLE
                    // fab.show();
                    toolbarTitleText!!.setText(R.string.schedule_title)

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
                    toolbarTitleText!!.setText(R.string.map_title)

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

        //        //open filters
        //        fab.setOnClickListener(view -> {
        //            if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
        //                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        //            } else {
        //                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        //
        //            }
        //        });
        //        //close filters
        //        collapseBottomImg.setOnClickListener(view -> {
        //            if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
        //                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        //            }
        //        });

        //check if the category was previously chosen

        //observe livedata emitted by view model
        homeViewModel.typeFiltersResponse.observe(this, Observer{
            if (it?.databaseError != null) {
                handleDatabaseError(it.databaseError)
            } else {
                if (it?.filtersModelList != null) {
                    handleFiltersResponse(it.filtersModelList)
                }

            }
        })
        homeViewModel.topicFiltersResponse.observe(this, Observer{
            if (it?.databaseError != null) {
                handleDatabaseError(it.databaseError)
            } else {
                handleTopicFilters(it?.filtersModelList)
            }
        })
        homeViewModel.updateTokenResponse.observe(this, Observer{
            if (it != null) {
                if (it.isSuccess) {
                    tokenSent = 1
                    sharedPreferences.edit().putInt(TOKEN_SENT, tokenSent).apply()
                } else {
                    tokenSent = 0
                    sharedPreferences.edit().putInt(TOKEN_SENT, tokenSent).apply()
                }
            }
        })

    }

    private fun handleTopicFilters(filtersModel: List<FiltersModel>?) {
        if (filtersModel != null){
            topicFilterList = filtersModel
            initView(topicsChipsRv!!, topicFilterList)
        }
    }

    private fun getTopicFilters() {
        homeViewModel.getTopicFilters()
    }

    private fun getTypeFilters() {
        homeViewModel.getTypeFilters()
    }

    private fun handleFiltersResponse(filtersModel: List<FiltersModel>) {
        typeFilterList = filtersModel
        initView(typesChipRv!!, typeFilterList)
    }

    private fun handleDatabaseError(databaseError: String?) {
        Toast.makeText(applicationContext, databaseError, Toast.LENGTH_SHORT).show()
    }

    private fun initView(recyclerView: RecyclerView, filtersModelList: List<FiltersModel>) {
        val layoutManager = GridLayoutManager(applicationContext, 2)
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = ChipViewAdapter(filtersModelList, applicationContext)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_sign_out, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_settings) {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, AuthenticateUserActivity::class.java))
            finish()
            return true
        }

        return super.onOptionsItemSelected(item)

    }

    companion object {
        var navItemIndex = 1 //controls toolbar titles and icons
        var fabVisible = true
        private val RC_SIGN_IN = 123
    }
}
