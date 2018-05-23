package droiddevelopers254.droidconke.views.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;

import butterknife.BindView;
import butterknife.ButterKnife;
import droiddevelopers254.droidconke.R;
import droiddevelopers254.droidconke.models.SessionsModel;
import droiddevelopers254.droidconke.viewmodels.SessionDataViewModel;

public class SessionViewActivity extends AppCompatActivity {
    BottomAppBar bottomAppBar;
    int sessionId;
    FloatingActionButton fab;

    @BindView(R.id.txtSessionTime)
    TextView txtSessionTime;
    @BindView(R.id.txtSessionRoom)
    TextView txtSessionRoom;
    @BindView(R.id.txtSessionDesc)
    TextView txtSessionDesc;
    @BindView(R.id.txtSessionCategory)
    TextView txtSessionCategory;
    @BindView(R.id.sessionViewTitleText)
    TextView sessionViewTitleText;
    @BindView(R.id.bottomSheetView)
    View bottomSheetView;

    SessionDataViewModel sessionDataViewModel;
    private BottomSheetBehavior bottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_view);

        ButterKnife.bind(this);

        sessionDataViewModel= ViewModelProviders.of(this).get(SessionDataViewModel.class);
        bottomSheetBehavior= BottomSheetBehavior.from(bottomSheetView);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //get extras
        Intent extraIntent=getIntent();
        sessionId = extraIntent.getIntExtra("sessionId",0);

        getSessionData(sessionId);

        //observe live data emitted by view model
        sessionDataViewModel.getSessionDetails().observe(this,sessionDataState -> {
            if (sessionDataState.getDatabaseError() != null){
                handleDatabaseError(sessionDataState.getDatabaseError());
            }else {
                handleFetchSessionData(sessionDataState.getSessionsModel());
            }
        });

        bottomAppBar=findViewById(R.id.bottomAppBar);
        sessionViewTitleText=findViewById(R.id.sessionViewTitleText);
        fab=findViewById(R.id.fab);
        bottomAppBar.replaceMenu(R.menu.menu_bottom_appbar);

        //handle menu items on material bottom bar
        bottomAppBar.setOnMenuItemClickListener(item -> {
            int id= item.getItemId();
            if (id == R.id.action_share){
                Intent shareSession=new Intent();
                shareSession.setAction(Intent.ACTION_SEND);
                shareSession.putExtra(Intent.EXTRA_TEXT,"Check out "+"'"+ sessionId +"' at "+getString(R.string.droidcoke_hashtag)+"\n"+getString(R.string.droidconke_site));
                shareSession.setType("text/plain");
                startActivity(shareSession);
            }
            if (id == R.id.action_map){
                CoordinatorLayout.LayoutParams layoutParams= (CoordinatorLayout.LayoutParams)fab.getLayoutParams();
                layoutParams.setAnchorId(R.id.bottomSheetView);
                fab.setLayoutParams(layoutParams);

                if(bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
            return false;
        });

        //star a session
        fab.setOnClickListener(view -> {
            fab.setImageResource(R.drawable.ic_star_blue_24dp);
        });

    }

    private void handleFetchSessionData(SessionsModel sessionsModel) {
        if (sessionsModel != null){
            //set the data on the view
            txtSessionTime.setText(sessionsModel.getTime());
            txtSessionRoom.setText(sessionsModel.getRoom());
            txtSessionDesc.setText(sessionsModel.getDescription());
            txtSessionCategory.setText(sessionsModel.getTopic());
            sessionViewTitleText.setText(sessionsModel.getTitle());
        }
    }

    private void handleDatabaseError(DatabaseError databaseError) {
    }

    private void getSessionData(int sessionId){
        sessionDataViewModel.fetchSessionDetails(sessionId);
    }

}
