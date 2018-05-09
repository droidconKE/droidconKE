package droiddevelopers254.droidconke;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import droiddevelopers254.droidconke.views.activities.InfoActivity;
import droiddevelopers254.droidconke.views.activities.MapActivity;
import droiddevelopers254.droidconke.views.activities.ScheduleActivity;

public class HomeActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent infoIntent= new Intent(HomeActivity.this, InfoActivity.class);
                    startActivity(infoIntent);
                    return true;
                case R.id.navigation_schedule:
                    Intent scheduleIntent= new Intent(HomeActivity.this, ScheduleActivity.class);
                    startActivity(scheduleIntent);

                    return true;
                case R.id.navigation_map:
                    Intent mapsIntent = new Intent(HomeActivity.this, MapActivity.class);
                    startActivity(mapsIntent);
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
