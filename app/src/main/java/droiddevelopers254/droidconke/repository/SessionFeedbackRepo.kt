package droiddevelopers254.droidconke.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import droiddevelopers254.droidconke.datastates.FeedBackState
import droiddevelopers254.droidconke.models.SessionsUserFeedback

class SessionFeedbackRepo(private val firestore: FirebaseFirestore) {
    suspend fun sendFeedBack(userSessionFeedback: SessionsUserFeedback): LiveData<FeedBackState> {
        val feedBackStateMutableLiveData = MutableLiveData<FeedBackState>()
        try {
            firestore.collection("sessionsFeedback").add(userSessionFeedback).awaitRef()
            feedBackStateMutableLiveData.value = FeedBackState("Thank you for your feedback")
        } catch (e: Exception) {
            feedBackStateMutableLiveData.value = FeedBackState(e.message.toString())
        }

        return feedBackStateMutableLiveData
    }
}