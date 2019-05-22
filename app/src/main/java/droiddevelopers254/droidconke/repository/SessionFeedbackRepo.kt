package droiddevelopers254.droidconke.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import droiddevelopers254.droidconke.datastates.FeedBackState
import droiddevelopers254.droidconke.datastates.Result
import droiddevelopers254.droidconke.models.SessionsUserFeedback
import droiddevelopers254.droidconke.models.UserEventFeedback
import kotlinx.coroutines.tasks.await

class SessionFeedbackRepo {

    suspend fun sendFeedBack(userSessionFeedback: SessionsUserFeedback): Result<String> {
        return try {
            val firebaseFirestore = Firebase.firestore
            firebaseFirestore.collection("sessionsFeedback")
                    .add(userSessionFeedback)
                    .await()
            Result.Success("Thank you for your feedback")
        } catch (e: FirebaseFirestoreException) {
            Result.Error(e.message)
        }
    }
}