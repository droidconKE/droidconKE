package droiddevelopers254.droidconke.repository

import android.arch.lifecycle.LiveData
import droiddevelopers254.droidconke.database.AppDatabase
import droiddevelopers254.droidconke.database.dao.SessionsDao
import droiddevelopers254.droidconke.database.dao.StarredSessionDao
import droiddevelopers254.droidconke.models.StarredSessionModel
import droiddevelopers254.droidconke.utils.DroidCoin
import io.reactivex.Single
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class RoomStarrSessionRepo {
    private val starredSessionDao: StarredSessionDao =AppDatabase.getDatabase(DroidCoin.context)!!.starredSessionDao()
    private val executor: Executor = Executors.newSingleThreadExecutor()
    private val sessionsDao: SessionsDao =AppDatabase.getDatabase(DroidCoin.context)!!.sessionsDao()

    val starredSessions: Single<List<StarredSessionModel>>
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
