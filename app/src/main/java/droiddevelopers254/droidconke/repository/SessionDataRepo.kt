package droiddevelopers254.droidconke.repository

import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import droiddevelopers254.droidconke.datastates.Result
import droiddevelopers254.droidconke.models.SessionsModel
import droiddevelopers254.droidconke.utils.await

class SessionDataRepo {
    var sessionsModel: SessionsModel = SessionsModel()

    suspend fun getSessionData(dayNumber: String, sessionId: Int): Result<SessionsModel> {
        return try {
            val firebaseFirestore = Firebase.firestore
            val snapshot = firebaseFirestore.collection(dayNumber)
                    .whereEqualTo("id", sessionId)
                    .get()
                    .await()
            val snap = snapshot.toObjects<SessionsModel>()
            snap.forEach {
                sessionsModel = it.copy(documentId = it.documentId)
            }
            Result.Success(sessionsModel)
        } catch (e: FirebaseFirestoreException) {
            Result.Error(e.message)
        }

    }
}
