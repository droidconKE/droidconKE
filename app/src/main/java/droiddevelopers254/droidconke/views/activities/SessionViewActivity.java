package droiddevelopers254.droidconke.views.activities;

import android.annotation.TargetApi;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUriExposedException;
import android.support.annotation.NonNull;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import droiddevelopers254.droidconke.R;
import droiddevelopers254.droidconke.adapters.SpeakersAdapter;
import droiddevelopers254.droidconke.models.RoomModel;
import droiddevelopers254.droidconke.models.SessionsModel;
import droiddevelopers254.droidconke.models.SpeakersModel;
import droiddevelopers254.droidconke.models.StarredSessionModel;
import droiddevelopers254.droidconke.utils.ItemClickListener;
import droiddevelopers254.droidconke.viewmodels.SessionDataViewModel;

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
    @BindView(R.id.sessionImg)
    ImageView sessionImg;
    private BottomSheetBehavior bottomSheetBehavior;
    String starStatus, dayNumber, documentId;
    SessionsModel sessionsModel1;
    private DatabaseReference databaseReference;
    List<SpeakersModel> speakersList = new ArrayList<>();
    List<Integer> speakerId = new ArrayList<>();
    SpeakersAdapter speakersAdapter;
    static RecyclerView.LayoutManager mLayoutManager;
    StarredSessionModel starredSessionModel;
    private static final String TAG = SessionViewActivity.class.getSimpleName();
    SharedPreferences sharedPreferences;
    String sessionName, sessionUrl, sessionColor, photoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_view);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        starredSessionModel = new StarredSessionModel();
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        //get extras
        Intent extraIntent = getIntent();
        sessionId = extraIntent.getIntExtra("sessionId", 0);
        dayNumber = extraIntent.getStringExtra("dayNumber");
        starStatus = extraIntent.getStringExtra("starred");
        speakerId = extraIntent.getIntegerArrayListExtra("speakerId");
        roomId = extraIntent.getIntExtra("roomId", 0);
        sessionName = extraIntent.getStringExtra("sessionName");
        sessionUrl = extraIntent.getStringExtra("sessionUrl");
        sessionColor = extraIntent.getStringExtra("sessionColor");
        photoUrl = extraIntent.getStringExtra("photoUrl");

        ButterKnife.bind(this);

        Glide.with(getApplicationContext()).load(photoUrl)
                .thumbnail(Glide.with(getApplicationContext()).load(photoUrl))
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(sessionImg);

        sessionDataViewModel = ViewModelProviders.of(this).get(SessionDataViewModel.class);

        getSessionData(dayNumber, sessionId);

        //show session color
        sessionLabelText.setBackgroundColor(Color.parseColor(sessionColor));

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
        sessionDataViewModel.getSessionData().observe(this, sessionDataState -> {
            assert sessionDataState != null;
            if (sessionDataState.getDatabaseError() != null) {
                handleDatabaseError(sessionDataState.getDatabaseError());
            } else {
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

        bottomAppBar.replaceMenu(R.menu.menu_bottom_appbar);

        //handle menu items on material bottom bar
        bottomAppBar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.action_feedback){
                Intent intent = new Intent(SessionViewActivity.this,FeedBack.class);
                intent.putExtra("sessionId",sessionId);
                intent.putExtra("dayNumber", dayNumber);
                startActivity(intent);
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
        //share a session
        fab.setOnClickListener(view -> {

            String shareMessage = "Check out " + sessionName + " at " + getString(R.string.droidcoke_hashtag) + " " + sessionUrl;

            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(i, "Share Session"));

            //shareItem(photoUrl,shareMessage);
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

    private void getRoomDetails(int roomId) {
        sessionDataViewModel.fetchRoomDetails(roomId);
    }

    private void handleFetchRoomDetails(RoomModel roomModel) {
        if (roomModel != null) {
            roomDetailsText.setText(roomModel.getDescription());
        }
    }

    private void getSpeakerDetails(int speakerId) {
        sessionDataViewModel.fetchSpeakerDetails(speakerId);
    }

    private void handleFetchSpeakerDetails(List<SpeakersModel> speakersModel) {
        if (speakersModel != null) {
            speakersList.addAll(speakersModel);
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
        recyclerView.addOnItemTouchListener(new ItemClickListener(getApplicationContext(), recyclerView, new ItemClickListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                String webViewLink = "https://twitter.com/" + speakersList.get(position).getTwitterHandle();

                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(webViewLink));
                startActivity(webIntent);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void handleFetchSessionData(SessionsModel sessionsModel) {
        if (sessionsModel != null) {
            sessionsModel1 = sessionsModel;
            //set the data on the view
            txtSessionTime.setText(sessionsModel.getTime());
            txtSessionRoom.setText(sessionsModel.getRoom());
            txtSessionDesc.setText(sessionsModel.getDescription());
            txtSessionCategory.setText(sessionsModel.getTopic());
            sessionViewTitleText.setText(sessionsModel.getTitle());


        }
    }

    private void handleDatabaseError(String databaseError) {
        Toast.makeText(getApplicationContext(), databaseError, Toast.LENGTH_SHORT).show();
    }

    @TargetApi(Build.VERSION_CODES.N)
    public Uri getLocalBitmapUri(Bitmap bmp) {
        Uri bmpUri = null;
        try {
            File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);

            Log.d("URIImage", bmpUri.toString());
        } catch (IOException | FileUriExposedException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

    public void shareItem(String photoUrl, String shareMessage) {
        Picasso.get().load(photoUrl).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Intent shareSession = new Intent(Intent.ACTION_SEND);
                shareSession.setType("image/*");
                // shareSession.putExtra(Intent.EXTRA_TEXT, "Check out " + "'" +sessionName + "' at " + getString(R.string.droidcoke_hashtag) + "\n" +sessionUrl);
                shareSession.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap));
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        });
    }
}
