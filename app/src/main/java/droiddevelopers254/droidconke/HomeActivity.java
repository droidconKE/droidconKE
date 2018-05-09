package droiddevelopers254.droidconke;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import droiddevelopers254.droidconke.views.fragments.InfoFragment;
import droiddevelopers254.droidconke.views.fragments.MapFragment;
import droiddevelopers254.droidconke.views.fragments.ScheduleFragment;

public class HomeActivity extends AppCompatActivity {

    private TextView mTextMessage;
    public static final String PREF_USER_FIRST_TIME = "user_first_time";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    InfoFragment infoFragment= new InfoFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_home,infoFragment)
                            .commit();
                    return true;
                case R.id.navigation_schedule:
                    ScheduleFragment scheduleFragment= new ScheduleFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_home,scheduleFragment)
                            .commit();
                    return true;
                case R.id.navigation_map:
                    MapFragment mapFragment= new MapFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_home,mapFragment)
                            .commit();;
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_schedule);
    }

}
