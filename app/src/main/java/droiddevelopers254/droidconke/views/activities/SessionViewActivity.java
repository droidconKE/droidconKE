package droiddevelopers254.droidconke.views.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import droiddevelopers254.droidconke.R;
import droiddevelopers254.droidconke.adapters.SpeakersAdapter;
import droiddevelopers254.droidconke.models.RoomModel;
import droiddevelopers254.droidconke.models.SessionsModel;
import droiddevelopers254.droidconke.models.SpeakersModel;
import droiddevelopers254.droidconke.models.StarredSessionModel;
import droiddevelopers254.droidconke.viewmodels.SessionDataViewModel;
import io.reactivex.disposables.CompositeDisposable;

import static droiddevelopers254.droidconke.utils.SharedPref.PREF_NAME;

public class SessionViewActivity extends AppCompatActivity {
    int sessionId, roomId;

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
    @BindView(R.id.speakersRV)
    RecyclerView recyclerView;
    @BindView(R.id.speakersLinear)
    LinearLayout speakersLinear;
    @BindView(R.id.roomDetailsText)
    TextView roomDetailsText;

    SessionDataViewModel sessionDataViewModel;
    @BindView(R.id.sessionLabelText)
    TextView sessionLabelText;
    @BindView(R.id.bottomAppBar)
    BottomAppBar bottomAppBar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.rommLocationImg)
    ImageView rommLocationImg;
    @BindView(R.id.sessionCoordinatorLayout)
    CoordinatorLayout sessionCoordinatorLayout;
    private BottomSheetBehavior bottomSheetBehavior;
    String starStatus, dayNumber, documentId;
    SessionsModel sessionsModel1;
    private DatabaseReference databaseReference;
    List<SpeakersModel> speakersList = new ArrayList<>();
    List<Integer> speakerId = new ArrayList<>();
    SpeakersAdapter speakersAdapter;
    static RecyclerView.LayoutManager mLayoutManager;
    StarredSessionModel starredSessionModel;
    boolean starred = false;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private static final String TAG = SessionViewActivity.class.getSimpleName();
    SharedPreferences sharedPreferences;
    String isStarred;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_view);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        starredSessionModel = new StarredSessionModel();
        sharedPreferences = getSharedPreferences(PREF_NAME,MODE_PRIVATE);

        //get extras
        Intent extraIntent = getIntent();
        sessionId = extraIntent.getIntExtra("sessionId", 0);
        dayNumber = extraIntent.getStringExtra("dayNumber");
        starStatus = extraIntent.getStringExtra("starred");
        speakerId = extraIntent.getIntegerArrayListExtra("speakerId");
        roomId = extraIntent.getIntExtra("roomId", 0);

        ButterKnife.bind(this);

        sessionDataViewModel = ViewModelProviders.of(this).get(SessionDataViewModel.class);

        getSessionData(dayNumber, sessionId);

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView);
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

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        for (Integer i : speakerId) {
            getSpeakerDetails(i);
        }

        getRoomDetails(roomId);
        //observe live data emitted by view model
       sessionDataViewModel.getSessionData().observe(this,sessionDataState -> {
           assert sessionDataState != null;
           if (sessionDataState.getDatabaseError() != null){
               handleDatabaseError(sessionDataState.getDatabaseError());
           }else {
               this.handleFetchSessionData(sessionDataState.getSessionsModel());
           }
       });
        sessionDataViewModel.getSpeakerInfo().observe(this, speakersState -> {
            assert speakersState != null;
            if (speakersState.getDatabaseError() != null) {
                handleDatabaseError(speakersState.getDatabaseError());
            } else {
                handleFetchSpeakerDetails(speakersState.getSpeakersModel());
            }
        });

        sessionDataViewModel.getRoomInfo().observe(this, roomState -> {
            assert roomState != null;
            if (roomState.getDatabaseError() != null) {
                handleDatabaseError(roomState.getDatabaseError());
            } else {
                handleFetchRoomDetails(roomState.getRoomModel());
            }
        });
        sessionDataViewModel.starSessionResponse().observe(this, starSessionState -> {
            assert starSessionState != null;
            if (starSessionState.getError() != null) {
                handleStarResponse(starSessionState.getError());
            }
            if (starSessionState.isStarred()){

            }
        });
        sessionDataViewModel.unstarSessionResponse().observe(this, starSessionState -> {
            assert starSessionState != null;
            if (starSessionState.getError() != null) {
                handleStarResponse(starSessionState.getError());
            }
            if (!starSessionState.isStarred()){

            }
        });

        sessionDataViewModel.getStarStatus().observe(this, s -> {
           if (s != null){
               if (s.equals("0")){
                   isStarred = "0";
                   fab.setImageResource(R.drawable.ic_star_border_black_24dp);
               }
               if (s.equals("1")) {
                   isStarred = "1";
                   fab.setImageResource(R.drawable.ic_star_blue_24dp);
               }
           }else {
               isStarred = "0";
           }

        });
        bottomAppBar.replaceMenu(R.menu.menu_bottom_appbar);

        //handle menu items on material bottom bar
        bottomAppBar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.action_share) {
                Intent shareSession = new Intent();
                shareSession.setAction(Intent.ACTION_SEND);
                shareSession.putExtra(Intent.EXTRA_TEXT, "Check out " + "'" + sessionId + "' at " + getString(R.string.droidcoke_hashtag) + "\n" + getString(R.string.droidconke_site));
                shareSession.setType("text/plain");
                startActivity(shareSession);
            }
            if (id == R.id.action_map) {

                if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

                }
            }
            return false;
        });
        //star a session
        fab.setOnClickListener(view -> {
            if (isStarred.equals("0")) {
                isStarred = "1";
                sessionDataViewModel.starrSessionInDb(sessionId,isStarred,dayNumber);
                Toast.makeText(getApplicationContext(),getString(R.string.starred_desc),Toast.LENGTH_SHORT).show();

                fab.setImageResource(R.drawable.ic_star_blue_24dp);
                starredSessionModel.setDay(dayNumber);
                starredSessionModel.setSession_id(String.valueOf(sessionsModel1.getId()));
                starredSessionModel.setUser_id(FirebaseAuth.getInstance().getCurrentUser().getUid());
                starredSessionModel.setStarred(true);
                starredSessionModel.setDocumentId(sessionsModel1.getDocumentId());
                //star session in starred_sessions collection
                //this will aid in tracking every starred session and then send a push notification
                sessionDataViewModel.starSession(starredSessionModel,FirebaseAuth.getInstance().getCurrentUser().getUid());

            }else if (isStarred.equals("1")){
                isStarred = "0";
                Toast.makeText(getApplicationContext(),getString(R.string.unstarred_desc),Toast.LENGTH_SHORT).show();

                //star a session
                fab.setImageResource(R.drawable.ic_star_border_black_24dp);

                sessionDataViewModel.unstarrSessionInDb(sessionId,isStarred,dayNumber);
                //unstar session in starred_sessions collection
                sessionDataViewModel.unStarSession(String.valueOf(sessionsModel1.getDocumentId()),FirebaseAuth.getInstance().getCurrentUser().getUid(),false);

            }
        });
        //collapse bottom bar
        collapseBottomImg.setOnClickListener(view -> {
            if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

    }

    private void getSessionData(String dayNumber, int sessionId) {
        sessionDataViewModel.getSessionDetails(dayNumber, sessionId);
    }

    private void handleStarUserSession(int starMessage) {
       Toast.makeText(getApplicationContext(),getString(starMessage),Toast.LENGTH_SHORT).show();
    }

    private void handleStarResponse(String error) {
        Toast.makeText(getApplicationContext(),error,Toast.LENGTH_SHORT).show();
    }

    private void getRoomDetails(int roomId) {
        sessionDataViewModel.fetchRoomDetails(roomId);
    }

    private void handleFetchRoomDetails(RoomModel roomModel) {
        if (roomModel != null) {
            roomDetailsText.setText(roomModel.getName() + "Room capacity is: " + String.valueOf(roomModel.getCapacity()));
        }
    }

    private void getSpeakerDetails(int speakerId) {
        sessionDataViewModel.fetchSpeakerDetails(speakerId);
    }

    private void handleFetchSpeakerDetails(List<SpeakersModel> speakersModel) {
        if (speakersModel != null) {
            speakersList = speakersModel;
            initView();
        } else {
            //if there are no speakers for this session hide views
            speakersLinear.setVisibility(View.GONE);
        }
    }

    private void initView() {
        speakersAdapter = new SpeakersAdapter(speakersList, getApplicationContext());
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(speakersAdapter);
    }
    private void handleFetchSessionData(SessionsModel sessionsModel) {
        if (sessionsModel != null) {
            sessionsModel1 = sessionsModel;
            //check star status
            sessionDataViewModel.isSessionStarredInDb(sessionId,dayNumber);

            //set the data on the view
            txtSessionTime.setText(sessionsModel.getTime());
            txtSessionRoom.setText(sessionsModel.getRoom());
            txtSessionDesc.setText(sessionsModel.getDescription());
            txtSessionCategory.setText(sessionsModel.getTopic());
            sessionViewTitleText.setText(sessionsModel.getTitle());


        }
    }
    private void handleDatabaseError(String databaseError) {
        Toast.makeText(getApplicationContext(),databaseError,Toast.LENGTH_SHORT).show();
    }

}
