package droiddevelopers254.droidconke;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import droiddevelopers254.droidconke.views.fragments.InfoFragment;
import droiddevelopers254.droidconke.views.fragments.MapFragment;
import droiddevelopers254.droidconke.views.fragments.ScheduleFragment;

public class HomeActivity extends AppCompatActivity {

    private TextView mTextMessage,toolbarTitleText;
    ImageView accountImg;
    public static final String PREF_USER_FIRST_TIME = "user_first_time";
    public  static int navItemIndex = 1; //controls toolbar titles and icons

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                switch (item.getItemId()) {
                    case R.id.navigation_home:

                        navItemIndex =0;
                        accountImg.setVisibility(View.GONE);
                        toolbarTitleText.setText(R.string.info_title);
                        InfoFragment infoFragment= new InfoFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_home,infoFragment)
                                .commit();
                        return true;
                    case R.id.navigation_schedule:

                        navItemIndex= 1;
                        accountImg.setVisibility(View.VISIBLE);
                        toolbarTitleText.setText(R.string.schedule_title);
                        ScheduleFragment scheduleFragment= new ScheduleFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_home,scheduleFragment)
                                .commit();
                        return true;
                    case R.id.navigation_map:
                        navItemIndex= 2;
                        accountImg.setVisibility(View.GONE);
                        toolbarTitleText.setText(R.string.map_title);
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
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        toolbarTitleText=findViewById(R.id.toolbarTitleText);
        accountImg=findViewById(R.id.accountImg);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_schedule);




    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
