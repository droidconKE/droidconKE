package droiddevelopers254.droidconke.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObjects
import droiddevelopers254.droidconke.database.AppDatabase
import droiddevelopers254.droidconke.database.dao.SessionsDao
import droiddevelopers254.droidconke.datastates.Result
import droiddevelopers254.droidconke.models.SessionsModel
import droiddevelopers254.droidconke.utils.await

class DayOneRepo(db: AppDatabase, private val firestore: FirebaseFirestore) {
  private val sessionsDao: SessionsDao = db.sessionsDao()

  suspend fun getDayOneSessions(): Result<List<SessionsModel>> {
    return try {
      val snapshots = firestore.collection("day_one")
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
