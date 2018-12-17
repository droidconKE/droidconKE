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
    fun saveSession(sessionsModelList: List<SessionsModel>)

    @Query("SELECT * FROM sessionsList WHERE day_number =:dayNumber")
    fun getDayOneSessions(dayNumber: String): LiveData<List<SessionsModel>>

    @Query("SELECT * FROM sessionsList WHERE day_number =:dayNumber")
    fun getDayTwoSessions(dayNumber: String): LiveData<List<SessionsModel>>

    @Query("SELECT * FROM sessionsList WHERE day_number =:dayNumber AND id =:sessionId")
    fun getSessionDetails(dayNumber: String, sessionId: Int): LiveData<SessionsModel>

    @Query("UPDATE sessionsList SET starred = :isStarred WHERE id LIKE :sessionId AND day_number LIKE :dayNumber")
    fun starSession(sessionId: Int, isStarred: String, dayNumber: String)

    @Query("SELECT count(*) FROM sessionsList WHERE id=:sessionId AND day_number =:dayNumber")
    fun isSessionStarred(sessionId: Int, dayNumber: String): LiveData<Int>

    @Query("UPDATE sessionsList SET starred = :isStarred WHERE id LIKE :sessionId AND day_number LIKE :dayNumber")
    fun unStarSession(sessionId: Int, isStarred: String, dayNumber: String)

}
