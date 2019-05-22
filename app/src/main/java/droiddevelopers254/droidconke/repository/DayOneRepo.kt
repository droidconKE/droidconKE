package droiddevelopers254.droidconke.repository

import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import droiddevelopers254.droidconke.database.AppDatabase
import droiddevelopers254.droidconke.database.dao.SessionsDao
import droiddevelopers254.droidconke.datastates.Result
import droiddevelopers254.droidconke.models.SessionsModel
import droiddevelopers254.droidconke.utils.DroidCon
import droiddevelopers254.droidconke.utils.await

class DayOneRepo {
    private val sessionsDao: SessionsDao = AppDatabase.getDatabase(DroidCon.context)!!.sessionsDao()

    suspend fun getDayOneSessions(): Result<List<SessionsModel>> {
        return try {
            val firebaseFirestore = Firebase.firestore
            val snapshots = firebaseFirestore.collection("day_one")
                    .orderBy("id", Query.Direction.ASCENDING)
                    .get().await()
            saveSession(snapshots.toObjects())
            Result.Success(snapshots.toObjects())
        } catch (e: FirebaseFirestoreException) {
            Result.Error(e.message)
        }
    }

    suspend fun saveSession(sessionsModelList: List<SessionsModel>) {
        sessionsDao.saveSession(sessionsModelList)
    }
}
