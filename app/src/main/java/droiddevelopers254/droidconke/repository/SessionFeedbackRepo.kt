package droiddevelopers254.droidconke.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import droiddevelopers254.droidconke.datastates.Result
import droiddevelopers254.droidconke.models.SessionsUserFeedback
import kotlinx.coroutines.tasks.await

class SessionFeedbackRepo(private val firestore: FirebaseFirestore) {


    suspend fun sendFeedBack(userSessionFeedback: SessionsUserFeedback): Result<String> {
        return try {
            firestore.collection("sessionsFeedback")
                    .add(userSessionFeedback)
                    .await()
            Result.Success("Thank you for your feedback")
        } catch (e: FirebaseFirestoreException) {
            Result.Error(e.message)
        }
    }
}