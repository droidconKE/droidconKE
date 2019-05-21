package droiddevelopers254.droidconke.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import droiddevelopers254.droidconke.database.AppDatabase
import droiddevelopers254.droidconke.database.dao.SessionsDao
import droiddevelopers254.droidconke.datastates.SessionsState
import droiddevelopers254.droidconke.models.SessionsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DayOneRepo(db: AppDatabase, private val firestore: FirebaseFirestore) {
    private val sessionsDao: SessionsDao = db.sessionsDao()

    suspend fun getSessions(): LiveData<SessionsState> {
        val sessionsStateMutableLiveData = MutableLiveData<SessionsState>()
        try {
            val snapshot = firestore.collection("day_one")
                    .orderBy("id", Query.Direction.ASCENDING)
                    .get()
                    .await()
            if (!snapshot.isEmpty) {
                val sessionsModelList = snapshot.toObjects(SessionsModel::class.java)
                sessionsStateMutableLiveData.value = SessionsState(sessionsModelList)
                withContext(Dispatchers.IO) {
                    sessionsDao.saveSession(sessionsModelList)
                }
            }
        } catch (e: Exception) {
            sessionsStateMutableLiveData.value = SessionsState(null, e.message)
        }
        return sessionsStateMutableLiveData
    }

}
