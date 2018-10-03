package droiddevelopers254.droidconke;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import droiddevelopers254.droidconke.ui.BottomNavigationBehaviour;
import droiddevelopers254.droidconke.views.activities.AuthenticateUserActivity;
import droiddevelopers254.droidconke.views.activities.EventFeedback;
import droiddevelopers254.droidconke.views.fragments.InfoFragment;
import droiddevelopers254.droidconke.views.fragments.MapFragment;
import droiddevelopers254.droidconke.views.fragments.ScheduleFragment;

import static droiddevelopers254.droidconke.utils.SharedPref.PREF_NAME;

public class HomeActivity extends AppCompatActivity {

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
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    SharedPreferences sharedPreferences;
    public static int navItemIndex = 1; //controls toolbar titles and icons
    Button signInBtn;
    FirebaseAuth auth;
    FirebaseRemoteConfig firebaseRemoteConfig;
    public static boolean fabVisible = true;
    AppBarLayout.LayoutParams params;

    private GoogleSignInClient mGoogleSignInClient;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                navItemIndex = 0;

                //hide views not required by these fragment
                accountImg.setVisibility(View.GONE);
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

        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        auth = FirebaseAuth.getInstance();
        //setup defaults for remote config
        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehaviour());

        if (auth.getCurrentUser() != null) {
            //load user profile image
            Glide.with(getApplicationContext()).load(auth.getCurrentUser().getPhotoUrl())
                    .thumbnail(Glide.with(getApplicationContext()).load(auth.getCurrentUser().getPhotoUrl()))
                    .apply(new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL))
                    .transition(new DrawableTransitionOptions()
                    .crossFade())
                    .into(accountImg);
        }

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_schedule);


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
                    task -> {
                    });
            startActivity(new Intent(this, AuthenticateUserActivity.class));
            finish();

            return true;
        }else if(id==R.id.action_feedback){

            startActivity(new Intent(this, EventFeedback.class));
        }

        return super.onOptionsItemSelected(item);

    }
}
