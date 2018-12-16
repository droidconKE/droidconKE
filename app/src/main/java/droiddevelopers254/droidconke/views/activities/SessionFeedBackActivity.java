package droiddevelopers254.droidconke.views.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import droiddevelopers254.droidconke.R;
import droiddevelopers254.droidconke.models.SessionsModel;
import droiddevelopers254.droidconke.models.SessionsUserFeedback;
import droiddevelopers254.droidconke.viewmodels.SessionDataViewModel;

public class SessionFeedBackActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txtSessionFeedbackTitle)
    TextView txtSessionFeedbackTitle;
    @BindView(R.id.txtSessionUserFeedback)
    TextInputEditText txtSessionUserFeedback;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    SessionDataViewModel sessionDataViewModel;
    SessionsModel sessionsModel1;
    @BindView(R.id.loginProgressBarFeedBack)
    ProgressBar loginProgressBarFeedBack;
    private int sessionId;
    private String dayNumber,sessionFeedback;
    SessionsUserFeedback userFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_feed_back);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            getSupportActionBar().setTitle("Sessions Feedback");
        }

        //get extras
        Intent extraIntent = getIntent();
        sessionId = extraIntent.getIntExtra("sessionId", 0);
        dayNumber = extraIntent.getStringExtra("dayNumber");

        sessionDataViewModel = ViewModelProviders.of(this).get(SessionDataViewModel.class);

        getSessionData(dayNumber, sessionId);

        //observe live data emitted by view model
        sessionDataViewModel.getSessionData().observe(this, sessionDataState -> {
            assert sessionDataState != null;
            if (sessionDataState.getDatabaseError() != null) {
                handleDatabaseError(sessionDataState.getDatabaseError());
            } else {
                this.handleFetchSessionData(sessionDataState.getSessionsModel());
            }
        });
        sessionDataViewModel.getSessionFeedBackResponse().observe(this,feedBackState -> {
            if (feedBackState.getFeedback() != null){
                handleFeedbackResponse(feedBackState.getFeedback());
            }
        });

    }

    private void handleFeedbackResponse(String feedback) {
        loginProgressBarFeedBack.setVisibility(View.GONE);

        txtSessionUserFeedback.setText("");

        Toast.makeText(getApplicationContext(),"Thank you for your feedback",
                Toast.LENGTH_SHORT).show();
    }

    private void getSessionData(String dayNumber, int sessionId) {
        sessionDataViewModel.getSessionDetails(dayNumber, sessionId);
    }

    private void handleFetchSessionData(SessionsModel sessionsModel) {
        if (sessionsModel != null) {
            sessionsModel1 = sessionsModel;
            //set the data on the view
            txtSessionFeedbackTitle.setText(sessionsModel.getTitle());

        }
    }

    private void handleDatabaseError(String databaseError) {
        Toast.makeText(getApplicationContext(), databaseError, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {

        //get data from user and post them
        if(isFeedbackValid()){
            postUserFeedback(userFeedback);
        }

    }

    public boolean isFeedbackValid(){

        sessionFeedback = txtSessionUserFeedback.getText().toString().trim();
        boolean isValid;

        if (sessionFeedback.isEmpty()){
            txtSessionUserFeedback.setError("Session feedback cannot be empty");
            isValid = false;
        } else {

            isValid = true;
            txtSessionUserFeedback.setError(null);

            userFeedback = new SessionsUserFeedback();
            userFeedback.setSession_id(String.valueOf(sessionId));
            userFeedback.setDay_number(dayNumber);
            userFeedback.setSession_title(sessionsModel1.getTitle());
            userFeedback.setSession_feedback(sessionFeedback);
        }
        return isValid;
    }

    private void postUserFeedback(SessionsUserFeedback userFeedback) {

        loginProgressBarFeedBack.setVisibility(View.VISIBLE);
        sessionDataViewModel.sendSessionFeedBack(userFeedback);

    }
}
