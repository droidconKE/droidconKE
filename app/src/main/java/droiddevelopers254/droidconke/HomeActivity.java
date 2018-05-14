package droiddevelopers254.droidconke;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import droiddevelopers254.droidconke.ui.RoundedDialog;
import droiddevelopers254.droidconke.utils.OpenSourceLicencesUtil;
import droiddevelopers254.droidconke.views.fragments.InfoFragment;
import droiddevelopers254.droidconke.views.fragments.MapFragment;
import droiddevelopers254.droidconke.views.fragments.ScheduleFragment;

public class HomeActivity extends AppCompatActivity {

    private TextView mTextMessage,toolbarTitleText,cancelText;
    ImageView accountImg;
    public static final String PREF_USER_FIRST_TIME = "user_first_time";
    public  static int navItemIndex = 1; //controls toolbar titles and icons
    private  AlertDialog.Builder builder= null;
    Button signInBtn;

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
        
        //show sign in dialog
        accountImg.setOnClickListener(view -> {
            showDialog();
        });




    }

    private void showDialog() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag("dialog_signup");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        new RoundedDialog().show(ft, "dialog_signup");

    }

    private void signInWithGoogle() {
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
