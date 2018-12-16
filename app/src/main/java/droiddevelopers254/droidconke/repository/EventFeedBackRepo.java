package droiddevelopers254.droidconke.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;

import droiddevelopers254.droidconke.datastates.FeedBackState;
import droiddevelopers254.droidconke.datastates.SessionsState;
import droiddevelopers254.droidconke.models.SessionsModel;
import droiddevelopers254.droidconke.models.UserEventFeedback;

public class EventFeedBackRepo {

    public EventFeedBackRepo(){

    }
    //we using mutable live data to handle cases for both a success and failure case
    public LiveData<FeedBackState> sendFeedBack(UserEventFeedback userEventFeedback){
        final MutableLiveData<FeedBackState> feedBackStateMutableLiveData= new MutableLiveData<>();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("eventFeedback")
                .add(userEventFeedback)
                .addOnSuccessListener(documentReference -> feedBackStateMutableLiveData.setValue(new FeedBackState("Thank you for your feedback")))
                .addOnFailureListener(error ->
                        feedBackStateMutableLiveData.setValue(new FeedBackState(error.getMessage())));

        return feedBackStateMutableLiveData;
    }
}
