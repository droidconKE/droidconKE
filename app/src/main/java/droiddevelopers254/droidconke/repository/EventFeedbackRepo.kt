package droiddevelopers254.droidconke.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import droiddevelopers254.droidconke.datastates.Result
import droiddevelopers254.droidconke.models.UserEventFeedback
import kotlinx.coroutines.tasks.await


class EventFeedbackRepo(val firestore: FirebaseFirestore) {

  suspend fun sendFeedBack(userEventFeedback: UserEventFeedback): Result<String> {
    return try {
      firestore.collection("").add(userEventFeedback).await()
      Result.Success("Thank you for your feedback")

    } catch (e: FirebaseFirestoreException) {
      Result.Error(e.message)
    }
  }
}