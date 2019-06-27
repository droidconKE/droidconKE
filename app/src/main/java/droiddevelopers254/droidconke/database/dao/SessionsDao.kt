package droiddevelopers254.droidconke.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import droiddevelopers254.droidconke.models.SessionsModel

@Dao
interface SessionsDao {

    @Insert(onConflict = REPLACE)
    suspend fun saveSession(sessionsModelList: List<SessionsModel>)

    @Query("SELECT * FROM sessionsList WHERE day_number =:dayNumber")
    suspend fun getDayOneSessions(dayNumber: String): List<SessionsModel>

    @Query("SELECT * FROM sessionsList WHERE day_number =:dayNumber")
    suspend fun getDayTwoSessions(dayNumber: String): List<SessionsModel>

    @Query("SELECT * FROM sessionsList WHERE day_number =:dayNumber AND id =:sessionId")
    suspend fun getSessionDetails(dayNumber: String, sessionId: Int): SessionsModel

    @Query("UPDATE sessionsList SET starred = :isStarred WHERE id LIKE :sessionId AND day_number LIKE :dayNumber")
    suspend fun starSession(sessionId: Int, isStarred: String, dayNumber: String)

    @Query("SELECT count(*) FROM sessionsList WHERE id=:sessionId AND day_number =:dayNumber")
    suspend fun isSessionStarred(sessionId: Int, dayNumber: String): Int

    @Query("UPDATE sessionsList SET starred = :isStarred WHERE id LIKE :sessionId AND day_number LIKE :dayNumber")
    suspend fun unStarSession(sessionId: Int, isStarred: String, dayNumber: String)

}
