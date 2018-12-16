package droiddevelopers254.droidconke.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.google.firebase.firestore.FirebaseFirestore;

import droiddevelopers254.droidconke.datastates.FeedBackState;
import droiddevelopers254.droidconke.models.SessionsUserFeedback;

public class SessionFeedbackRepo {

    public SessionFeedbackRepo(){

    }

    //we using mutable live data to handle cases for both a success and failure case
    public LiveData<FeedBackState> sendFeedBack(SessionsUserFeedback sessionFeedBack){
        final MutableLiveData<FeedBackState> feedBackStateMutableLiveData= new MutableLiveData<>();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("sessionsFeedback")
                .add(sessionFeedBack)
                .addOnSuccessListener(documentReference -> feedBackStateMutableLiveData.setValue(new FeedBackState("Thank you for your feedback")))
                .addOnFailureListener(error ->
                        feedBackStateMutableLiveData.setValue(new FeedBackState(error.getMessage())));

        return feedBackStateMutableLiveData;
    }
}
