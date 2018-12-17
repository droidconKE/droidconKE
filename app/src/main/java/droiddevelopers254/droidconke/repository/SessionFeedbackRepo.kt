package droiddevelopers254.droidconke.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import droiddevelopers254.droidconke.datastates.FeedBackState
import droiddevelopers254.droidconke.models.SessionsUserFeedback
import droiddevelopers254.droidconke.models.UserEventFeedback

class SessionFeedbackRepo{
    fun sendFeedBack(userSessionFeedback: SessionsUserFeedback): LiveData<FeedBackState> {
        val feedBackStateMutableLiveData = MutableLiveData<FeedBackState>()
        val firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseFirestore.collection("sessionsFeedback")
                .add(userSessionFeedback)
                .addOnSuccessListener { documentReference -> feedBackStateMutableLiveData.setValue(FeedBackState("Thank you for your feedback")) }
                .addOnFailureListener { error -> feedBackStateMutableLiveData.setValue(FeedBackState(error.message.toString())) }

        return feedBackStateMutableLiveData
    }
}