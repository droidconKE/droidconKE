package droiddevelopers254.droidconke.repository

import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import droiddevelopers254.droidconke.datastates.Result
import droiddevelopers254.droidconke.models.UserEventFeedback
import kotlinx.coroutines.tasks.await


class EventFeedbackRepo {

    suspend fun sendFeedBack(userEventFeedback: UserEventFeedback): Result<String> {
        return try {
            val firebaseFirestore = Firebase.firestore
            val snapshot = firebaseFirestore.collection("").add(userEventFeedback).await()
            Result.Success("Thank you for your feedback")

        } catch (e: FirebaseFirestoreException) {
            Result.Error(e.message)
        }
    }
}