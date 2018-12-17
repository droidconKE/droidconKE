package droiddevelopers254.droidconke.repository

import droiddevelopers254.droidconke.datastates.FeedBackState
import com.google.firebase.firestore.FirebaseFirestore
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import droiddevelopers254.droidconke.models.UserEventFeedback


class EventFeedbackRepo {
    //we using mutable live data to handle cases for both a success and failure case
    fun sendFeedBack(userEventFeedback: UserEventFeedback): LiveData<FeedBackState> {
        val feedBackStateMutableLiveData = MutableLiveData<FeedBackState>()
        val firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseFirestore.collection("eventFeedback")
                .add(userEventFeedback)
                .addOnSuccessListener { documentReference -> feedBackStateMutableLiveData.setValue(FeedBackState("Thank you for your feedback")) }
                .addOnFailureListener { error -> feedBackStateMutableLiveData.setValue(FeedBackState(error.message.toString())) }

        return feedBackStateMutableLiveData
    }
}