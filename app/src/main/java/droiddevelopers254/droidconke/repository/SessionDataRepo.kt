package droiddevelopers254.droidconke.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import droiddevelopers254.droidconke.database.AppDatabase
import droiddevelopers254.droidconke.database.dao.SessionsDao
import droiddevelopers254.droidconke.datastates.SessionDataState
import droiddevelopers254.droidconke.models.SessionsModel

class SessionDataRepo(db: AppDatabase, private val firestore: FirebaseFirestore) {
    private val sessionsDao: SessionsDao = db.sessionsDao()

    suspend fun getSessionData(dayNumber: String, sessionId: Int): LiveData<SessionDataState> {
        val sessionsModelMutableLiveData = MutableLiveData<SessionDataState>()
        try {
            val snapshot = firestore.collection(dayNumber)
                    .whereEqualTo("id", sessionId)
                    .get()
                    .await()
            val doc = snapshot.documents.first()
            val sessionsModel = doc.toObject(SessionsModel::class.java)
            val newSessionsModel = sessionsModel?.copy(documentId = doc.id)
            sessionsModelMutableLiveData.value = SessionDataState(newSessionsModel, null)
        } catch (e: Exception) {
            sessionsModelMutableLiveData.value = SessionDataState(null, "Error getting session details")
        }
//        val snapshot = firestore.collection(dayNumber)
//                .whereEqualTo("id", sessionId)
//                .get()
//                .addOnCompleteListener {
//                    when {
//                        it.isSuccessful -> for (queryDocumentSnapshot in it.result!!) {
//                            val sessionsModel = queryDocumentSnapshot.toObject(SessionsModel::class.java)
//                            val newSessionsModel= sessionsModel.copy(documentId = queryDocumentSnapshot.id)
//                            sessionsModelMutableLiveData.value = SessionDataState(newSessionsModel,null)
//                        }
//                        else -> sessionsModelMutableLiveData.value = SessionDataState(null,"Error getting session details")
//                    }
//                }

        return sessionsModelMutableLiveData
    }
}
