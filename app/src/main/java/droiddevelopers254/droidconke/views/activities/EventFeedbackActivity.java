package droiddevelopers254.droidconke.views.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import droiddevelopers254.droidconke.R;
import droiddevelopers254.droidconke.models.UserEventFeedback;
import droiddevelopers254.droidconke.viewmodels.FeedBackViewModel;

public class EventFeedbackActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progressBarEventFeedBack)
    ProgressBar progressBarEventFeedBack;
    @BindView(R.id.txtEventFeedback)
    TextInputEditText txtEventFeedback;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private String eventFeedback;
    private  UserEventFeedback userEventFeedback;
    FeedBackViewModel feedBackViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_feedback);
        ButterKnife.bind(this);
         setSupportActionBar(toolbar);

         feedBackViewModel = ViewModelProviders.of(this).get(FeedBackViewModel.class);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            getSupportActionBar().setTitle("Event Feedback");
        }

        //observe live data emitted by view model
        feedBackViewModel.getEventFeedBackResponse().observe(this,feedBackState -> {
            if (feedBackState.getFeedback() != null){
                handleFeedbackResponse(feedBackState.getFeedback());
            }
        });

    }

    private void handleFeedbackResponse(String feedback) {
        progressBarEventFeedBack.setVisibility(View.GONE);
        txtEventFeedback.setText("");
        Toast.makeText(getApplicationContext(),"Thank you for your feedback",
                Toast.LENGTH_SHORT).show();

    }

    public boolean isFeedbackValid(){

        eventFeedback = txtEventFeedback.getText().toString().trim();
        boolean isValid;

        if (eventFeedback.isEmpty()){
            txtEventFeedback.setError("Event feedback cannot be empty");
            isValid = false;
        } else {

            isValid = true;
            txtEventFeedback.setError(null);

            userEventFeedback = new UserEventFeedback();
            userEventFeedback.setEventFeedback(eventFeedback);

        }
        return isValid;
    }

    private void postUserFeedback(UserEventFeedback userEventFeedback) {

        progressBarEventFeedBack.setVisibility(View.VISIBLE);
        feedBackViewModel.sendEventFeedBack(userEventFeedback);

    }

    @OnClick(R.id.fab)
    public void onViewClicked() {

        if (isFeedbackValid()){
            postUserFeedback(userEventFeedback);
        }
    }
}
