package droiddevelopers254.droidconke.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import droiddevelopers254.droidconke.models.SessionsModel;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface SessionsDao {

    @Insert(onConflict = REPLACE)
    void saveSession(List<SessionsModel> sessionsModelList);

    @Query("SELECT * FROM sessionsList WHERE day_number =:dayNumber")
    LiveData<List<SessionsModel>> getDayOneSessions(String dayNumber);

    @Query("SELECT * FROM sessionsList WHERE day_number =:dayNumber")
    LiveData<List<SessionsModel>> getDayTwoSessions(String dayNumber);

    @Query("SELECT * FROM sessionsList WHERE day_number =:dayNumber AND id =:sessionId")
    LiveData<SessionsModel> getSessionDetails(String dayNumber,int sessionId);

    @Query("UPDATE sessionsList SET isStarred = :isStarred WHERE id LIKE :sessionId AND day_number LIKE :dayNumber")
    void starSession(int sessionId,String isStarred,String dayNumber);

    @Query("SELECT count(*) FROM sessionsList WHERE id=:sessionId AND day_number =:dayNumber")
    LiveData<Integer> isSessionStarred(int sessionId,String dayNumber);

    @Query("UPDATE sessionsList SET isStarred = :isStarred WHERE id LIKE :sessionId AND day_number LIKE :dayNumber")
    void unStarSession(int sessionId,String isStarred,String dayNumber);

}
