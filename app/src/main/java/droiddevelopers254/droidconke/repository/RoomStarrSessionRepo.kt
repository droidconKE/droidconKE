package droiddevelopers254.droidconke.repository

import androidx.lifecycle.LiveData
import droiddevelopers254.droidconke.database.AppDatabase
import droiddevelopers254.droidconke.database.dao.SessionsDao
import droiddevelopers254.droidconke.database.dao.StarredSessionDao
import droiddevelopers254.droidconke.models.StarredSessionModel
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class RoomStarrSessionRepo(db: AppDatabase) {
    private val starredSessionDao: StarredSessionDao = db.starredSessionDao()
    private val executor: Executor = Executors.newSingleThreadExecutor()
    private val sessionsDao: SessionsDao = db.sessionsDao()

    val starredSessions: LiveData<List<StarredSessionModel>>
        get() = starredSessionDao.starredSessions

    fun starrSession(sessionId: Int, isStarred: String, dayNumber: String) {
        executor.execute { sessionsDao.starSession(sessionId, isStarred, dayNumber) }
    }

    fun unStarrSession(sessionId: Int, isStarred: String, dayNumber: String) {
        executor.execute { sessionsDao.unStarSession(sessionId, isStarred, dayNumber) }
    }

    fun isSessionStarred(sessionId: Int, dayNumber: String): LiveData<Int> {

        return sessionsDao.isSessionStarred(sessionId, dayNumber)
    }
}
