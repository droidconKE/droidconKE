package droiddevelopers254.droidconke.views.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import droiddevelopers254.droidconke.R;
import droiddevelopers254.droidconke.models.SessionsUserFeedback;
import droiddevelopers254.droidconke.models.UserEventFeedback;

public class EventFeedback extends AppCompatActivity {

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
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_feedback);
        ButterKnife.bind(this);
         setSupportActionBar(toolbar);

        db = FirebaseFirestore.getInstance();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            getSupportActionBar().setTitle("Event Feedback");
        }

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

        db.collection("eventFeedback")
                .add(userEventFeedback)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        progressBarEventFeedBack.setVisibility(View.GONE);

                        txtEventFeedback.setText("");

                        Toast.makeText(getApplicationContext(),"Thank you for your feedback",
                                Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                progressBarEventFeedBack.setVisibility(View.GONE);
            }
        });

    }

    @OnClick(R.id.fab)
    public void onViewClicked() {

        if (isFeedbackValid()){

            postUserFeedback(userEventFeedback);
        }
    }
}
