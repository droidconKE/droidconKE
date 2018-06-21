package droiddevelopers254.droidconke;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.chip.Chip;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
import droiddevelopers254.droidconke.utils.CategoriesData;
import droiddevelopers254.droidconke.viewmodels.HomeViewModel;
import droiddevelopers254.droidconke.views.activities.AuthenticateUserActivity;
import droiddevelopers254.droidconke.views.fragments.InfoFragment;
import droiddevelopers254.droidconke.views.fragments.MapFragment;
import droiddevelopers254.droidconke.views.fragments.ScheduleFragment;

public class HomeActivity extends AppCompatActivity {

    private TextView mTextMessage,toolbarTitleText,cancelText;
    CircleImageView accountImg;
    SharedPreferences sharedPreferences;
    public static final String PREF_NAME="droidconKE_pref";
    public static final String FIREBASE_TOKEN="firebaseToken";
    public static final String TOKEN_SENT="tokenSent";
    public static final String CATEGORY_CHOSEN="categoryChosen";
    String refreshToken;
    public static final String PREF_USER_FIRST_TIME = "user_first_time";
    public  static int navItemIndex = 1; //controls toolbar titles and icons
    private  AlertDialog.Builder builder= null;
    Button signInBtn;
    FirebaseUser firebaseUser;
    FirebaseAuth auth;
    FirebaseRemoteConfig firebaseRemoteConfig;
    public FloatingActionButton fab;
    public static boolean fabVisible=true;
    private static final int RC_SIGN_IN = 123;
    int tokenSent;
    private BottomSheetBehavior bottomSheetBehavior;
    View bottomSheetView;
    ImageView collapseBottomImg;
    @BindView(R.id.starredEventsChip)
    Chip starredEventsChip;
    boolean categoryChosen;
    Toolbar toolbar;
    AppBarLayout.LayoutParams params;
    HomeViewModel homeViewModel;
    ChipViewAdapter chipViewAdapter;
    RecyclerView recyclerView;
    static RecyclerView.LayoutManager mLayoutManager;
    List<FiltersModel> filtersModelList= new ArrayList<>();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                switch (item.getItemId()) {
                    case R.id.navigation_home:

                        navItemIndex =0;

                        //hide views not required by these fragment
                        accountImg.setVisibility(View.GONE);
                        fab.hide();
                        toolbarTitleText.setText(R.string.info_title);

                        //activate the hide toolbar
                        params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
                        params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
                                | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);

                        InfoFragment infoFragment= new InfoFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_home,infoFragment)
                                .commit();
                        return true;
                    case R.id.navigation_schedule:

                        navItemIndex= 1;

                        //hide views not required by these fragment
                        accountImg.setVisibility(View.VISIBLE);
                        fab.show();
                        toolbarTitleText.setText(R.string.schedule_title);

                        //activate the hide toolbar
                        params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
                        params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
                                | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);

                        ScheduleFragment scheduleFragment= new ScheduleFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_home,scheduleFragment)
                                .commit();
                        return true;
                    case R.id.navigation_map:

                        navItemIndex= 2;

                        //hide views not required by these fragment
                        accountImg.setVisibility(View.GONE);
                        fab.hide();
                        toolbarTitleText.setText(R.string.map_title);

                        //show toolbar if it was hidden
                        params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
                        params.setScrollFlags(0);  // clear all scroll flags

                        MapFragment mapFragment= new MapFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_home,mapFragment)
                                .commit();;
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
        homeViewModel= ViewModelProviders.of(this).get(HomeViewModel.class);

        sharedPreferences=getSharedPreferences(PREF_NAME,MODE_PRIVATE);
        auth=FirebaseAuth.getInstance();
        //setup defaults for remote config
        firebaseRemoteConfig=FirebaseRemoteConfig.getInstance();

        //check whether refresh token is sent to db
        tokenSent=sharedPreferences.getInt(TOKEN_SENT,0);
        if (tokenSent == 0){
            refreshToken=sharedPreferences.getString(FIREBASE_TOKEN,null);
            firebaseUser= auth.getCurrentUser();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference users=database.getReference();
            //update in firebase
            users.child("users").child(firebaseUser.getUid()).child("refresh_token").setValue(refreshToken);
        }


        BottomNavigationView navigation = findViewById(R.id.navigation);
        CoordinatorLayout.LayoutParams layoutParams=(CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehaviour());

        toolbarTitleText=findViewById(R.id.toolbarTitleText);
        accountImg=findViewById(R.id.accountImg);
        fab=findViewById(R.id.fab);
        collapseBottomImg=findViewById(R.id.collapseBottomImg);
        recyclerView=findViewById(R.id.typesChipRv);
        bottomSheetView=findViewById(R.id.bottomSheetView);
        bottomSheetBehavior= BottomSheetBehavior.from(bottomSheetView);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                // this part hides the button immediately and waits bottom sheet
                // to collapse to show
                if (BottomSheetBehavior.STATE_EXPANDED == newState) {
                    fab.animate().scaleX(0).scaleY(0).setDuration(200).start();
                } else if (BottomSheetBehavior.STATE_COLLAPSED == newState) {
                    fab.animate().scaleX(1).scaleY(1).setDuration(200).start();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });


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

        //open filters
        fab.setOnClickListener(view ->{
            if(bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
            else {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

            }
        } );
        //close filters
        collapseBottomImg.setOnClickListener(view -> {
            if(bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        //check if the category was previously chosen

        //observe livedata emitted by view model
//        homeViewModel.getFiltersList().observe(this,filtersModels -> {
//           if (filtersModels != null){
//
//           }
//        });
//        homeViewModel.getFilterStatus().observe(this,filtersModel -> {
//            if (filtersModel != null){
//            }
//        });
        filtersModelList= CategoriesData.getCategories();
        initView();

    }

    private void initView() {
        chipViewAdapter=new ChipViewAdapter(filtersModelList,getApplicationContext());
        mLayoutManager= new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(chipViewAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //control fab visibility
        if (fabVisible){
            fab.show();
        }else {
            fab.hide();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sign_out, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id== R.id.action_settings){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, AuthenticateUserActivity.class));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    public void selectFilter(View view) {
        int id= view.getId();
//        FiltersModel filtersModel= new FiltersModel(0,id,true);
//        homeViewModel.saveFilter(filtersModel);

    }

    private void checkStatus(int id) {
//        homeViewModel.checkFilterStatus(id);
    }
}
