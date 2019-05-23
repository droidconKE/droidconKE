package droiddevelopers254.droidconke.repository

import com.google.firebase.firestore.FirebaseFirestore
import droiddevelopers254.droidconke.database.AppDatabase
import droiddevelopers254.droidconke.database.dao.SessionsDao
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import droiddevelopers254.droidconke.datastates.Result
import droiddevelopers254.droidconke.models.SessionsModel
import droiddevelopers254.droidconke.utils.await

class SessionDataRepo(db: AppDatabase, private val firestore: FirebaseFirestore) {
    private val sessionsDao: SessionsDao = db.sessionsDao()

    suspend fun getSessionData(dayNumber: String, sessionId: Int): Result<SessionsModel> {
        return try {
            val snapshot = firestore.collection(dayNumber)
                    .whereEqualTo("id", sessionId)
                    .get()
                    .await()
            val doc = snapshot.documents.first()
            val sessionsModel = doc.toObject(SessionsModel::class.java)
            val newSessionsModel = sessionsModel?.copy(documentId = doc.id)
            Result.Success(newSessionsModel!!)
        } catch (e: FirebaseFirestoreException) {
            Result.Error(e.message)
        }

    }
}
