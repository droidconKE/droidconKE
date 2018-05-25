package droiddevelopers254.droidconke.views.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    @BindView(R.id.collapseBottomImg)
    ImageView collapseBottomImg;

    SessionDataViewModel sessionDataViewModel;
    private BottomSheetBehavior bottomSheetBehavior;
    String starStatus,dayNumber;
    SessionsModel sessionsModel1;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_view);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        //get extras
        Intent extraIntent=getIntent();
        sessionId = extraIntent.getIntExtra("sessionId",0);
        dayNumber=extraIntent.getStringExtra("dayNumber");
        starStatus=extraIntent.getStringExtra("starred");

        ButterKnife.bind(this);

        sessionDataViewModel= ViewModelProviders.of(this).get(SessionDataViewModel.class);
        bottomSheetBehavior= BottomSheetBehavior.from(bottomSheetView);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                // this part hides the button immediately and waits bottom sheet
                // to collapse to show
                if (BottomSheetBehavior.STATE_EXPANDED == newState) {
                    fab.animate().scaleX(0).scaleY(0).setDuration(300).start();
                } else if (BottomSheetBehavior.STATE_COLLAPSED == newState) {
                    fab.animate().scaleX(1).scaleY(1).setDuration(300).start();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        //check a session was previously starred
        if (starStatus.equals("0")){
            fab.setImageResource(R.drawable.ic_star_border_black_24dp);
        }else if (starStatus.equals("1")){
            fab.setImageResource(R.drawable.ic_star_blue_24dp);
        }

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
//                //animate fab to display on top of the bottom sheet
//                CoordinatorLayout.LayoutParams layoutParams= (CoordinatorLayout.LayoutParams)fab.getLayoutParams();
//                layoutParams.setAnchorId(R.id.bottomSheetView);
//
//                fab.setLayoutParams(layoutParams);

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
            if (starStatus.equals("0")){
                //start a session
               fab.setImageResource(R.drawable.ic_star_blue_24dp);

                //update in firebase
                databaseReference.child(dayNumber).child(String.valueOf(sessionsModel1.getId())).child("starred").setValue("1");
                Toast.makeText(getApplicationContext(),String.valueOf(sessionsModel1.getId()),Toast.LENGTH_SHORT).show();

            }else if(starStatus.equals("1")){
                fab.setImageResource(R.drawable.ic_star_border_black_24dp);

                //update in firebase
                databaseReference.child(dayNumber).child(String.valueOf(sessionsModel1.getId())).child("starred").setValue("0");
                Toast.makeText(getApplicationContext(),String.valueOf(sessionsModel1.getId()),Toast.LENGTH_SHORT).show();
            }
        });

        //collapse bottom bar
        collapseBottomImg.setOnClickListener(view -> {
            if(bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

    }

    private void handleFetchSessionData(SessionsModel sessionsModel) {
        if (sessionsModel != null){
            sessionsModel1=sessionsModel;
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
