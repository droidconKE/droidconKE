package droiddevelopers254.droidconke;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.card.MaterialCardView;
import android.support.design.chip.Chip;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import droiddevelopers254.droidconke.adapters.ChipViewAdapter;
import droiddevelopers254.droidconke.models.FiltersModel;
import droiddevelopers254.droidconke.ui.BottomNavigationBehaviour;
import droiddevelopers254.droidconke.viewmodels.HomeViewModel;
import droiddevelopers254.droidconke.views.activities.AuthenticateUserActivity;
import droiddevelopers254.droidconke.views.fragments.InfoFragment;
import droiddevelopers254.droidconke.views.fragments.MapFragment;
import droiddevelopers254.droidconke.views.fragments.ScheduleFragment;

import static droiddevelopers254.droidconke.utils.SharedPref.FIREBASE_TOKEN;
import static droiddevelopers254.droidconke.utils.SharedPref.PREF_NAME;
import static droiddevelopers254.droidconke.utils.SharedPref.TOKEN_SENT;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.topicsChipsRv)
    RecyclerView topicsChipsRv;
    @BindView(R.id.typesChipRv)
    RecyclerView typesChipRv;
    @BindView(R.id.accountImg)
    CircleImageView accountImg;
    @BindView(R.id.toolbarTitleText)
    TextView toolbarTitleText;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.container)
    ConstraintLayout container;
    @BindView(R.id.content_home)
    FrameLayout contentHome;
    @BindView(R.id.collapseBottomImg)
    ImageView collapseBottomImg;
    @BindView(R.id.bottomSheetView)
    MaterialCardView bottomSheetView;
   // @BindView(R.id.fab)
    //FloatingActionButton fab;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    SharedPreferences sharedPreferences;
    String refreshToken;
    public static int navItemIndex = 1; //controls toolbar titles and icons
    private AlertDialog.Builder builder = null;
    Button signInBtn;
    FirebaseUser firebaseUser;
    FirebaseAuth auth;
    FirebaseRemoteConfig firebaseRemoteConfig;
    public static boolean fabVisible = true;
    private static final int RC_SIGN_IN = 123;
    int tokenSent;
    private BottomSheetBehavior bottomSheetBehavior;
    @BindView(R.id.starredEventsChip)
    Chip starredEventsChip;
    boolean categoryChosen;
    AppBarLayout.LayoutParams params;
    HomeViewModel homeViewModel;
    ChipViewAdapter chipViewAdapter;
    static RecyclerView.LayoutManager mLayoutManager;
    List<FiltersModel> typeFilterList = new ArrayList<>();
    List<FiltersModel> topicFilterList = new ArrayList<>();

    private GoogleSignInClient mGoogleSignInClient;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                navItemIndex = 0;

                //hide views not required by these fragment
                accountImg.setVisibility(View.GONE);
                //fab.hide();
                toolbarTitleText.setText(R.string.info_title);

                //activate the hide toolbar
                params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
                params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
                        | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);

                InfoFragment infoFragment = new InfoFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_home, infoFragment)
                        .commit();
                return true;
            case R.id.navigation_schedule:

                navItemIndex = 1;

                //hide views not required by these fragment
                accountImg.setVisibility(View.VISIBLE);
               // fab.show();
                toolbarTitleText.setText(R.string.schedule_title);

                //activate the hide toolbar
                params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
                params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
                        | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);

                ScheduleFragment scheduleFragment = new ScheduleFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_home, scheduleFragment)
                        .commit();
                return true;
            case R.id.navigation_map:

                navItemIndex = 2;

                //hide views not required by these fragment
                accountImg.setVisibility(View.GONE);
               // fab.hide();
                toolbarTitleText.setText(R.string.map_title);

                //show toolbar if it was hidden
                params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
                params.setScrollFlags(0);  // clear all scroll flags

                MapFragment mapFragment = new MapFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_home, mapFragment)
                        .commit();
                return true;
        }
        return false;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        auth = FirebaseAuth.getInstance();
        //setup defaults for remote config
        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        //check whether refresh token is sent to db
        tokenSent = sharedPreferences.getInt(TOKEN_SENT, 0);
        if (tokenSent == 0) {
            refreshToken = sharedPreferences.getString(FIREBASE_TOKEN, null);
            firebaseUser = auth.getCurrentUser();
            //update in firestore
            homeViewModel.updateToken(firebaseUser.getUid(),refreshToken);
        }
        //get filters from firebase
        getTopicFilters();
        getTypeFilters();

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehaviour());

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView);
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

        if (auth.getCurrentUser() != null) {
            //load user profile image
            Glide.with(getApplicationContext()).load(auth.getCurrentUser().getPhotoUrl())
                    .thumbnail(Glide.with(getApplicationContext()).load(auth.getCurrentUser().getPhotoUrl()))
                    .centerCrop()
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(accountImg);
        }

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_schedule);

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
        homeViewModel.getTypeFiltersResponse().observe(this, filtersState -> {
            assert filtersState != null;
            if (filtersState.getDatabaseError() != null){
                handleDatabaseError(filtersState.getDatabaseError());
            }else{
                if (filtersState.getFiltersModel() != null){
                    handleFiltersResponse(filtersState.getFiltersModel());
                }

            }
        });
        homeViewModel.getTopicFiltersResponse().observe(this,filtersState -> {
            assert filtersState != null;
            if (filtersState.getDatabaseError() != null){
                handleDatabaseError(filtersState.getDatabaseError());
            }else {
                handleTopicFilters(filtersState.getFiltersModel());
            }
        });
        homeViewModel.getUpdateTokenResponse().observe(this,updateTokenState -> {
            assert updateTokenState != null;
            if (updateTokenState.isSuccess()){
                tokenSent=1;
                sharedPreferences.edit().putInt(TOKEN_SENT,tokenSent).apply();
            }else {
                tokenSent= 0;
                sharedPreferences.edit().putInt(TOKEN_SENT,tokenSent).apply();
            }
        });

    }
    private void handleTopicFilters(List<FiltersModel> filtersModel) {
        topicFilterList=filtersModel;
        initView(topicsChipsRv,topicFilterList);
    }

    private void getTopicFilters() {
        homeViewModel.getTopicFilters();
    }

    private void getTypeFilters() {
        homeViewModel.getTypeFilters();
    }

    private void handleFiltersResponse(List<FiltersModel> filtersModel) {
        typeFilterList =filtersModel;
        initView(typesChipRv,typeFilterList);
    }
    private void handleDatabaseError(String databaseError) {
        Toast.makeText(getApplicationContext(),databaseError,Toast.LENGTH_SHORT).show();
    }

    private void initView(RecyclerView recyclerView,List<FiltersModel> filtersModelList) {
        chipViewAdapter = new ChipViewAdapter(filtersModelList, getApplicationContext());
        mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(chipViewAdapter);
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sign_out, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {

            FirebaseAuth.getInstance().signOut();

            //google sign out
            mGoogleSignInClient.signOut().addOnCompleteListener(this,
                    new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                        }
                    });
            startActivity(new Intent(this, AuthenticateUserActivity.class));
            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    public void selectFilter(View view) {
        int id = view.getId();
//        FiltersModel filtersModel= new FiltersModel(0,id,true);
//        homeViewModel.saveFilter(filtersModel);

    }

    private void checkStatus(int id) {
//        homeViewModel.checkFilterStatus(id);
    }
}
