package droiddevelopers254.droidconke.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import droiddevelopers254.droidconke.database.entities.SessionsEntity;
import droiddevelopers254.droidconke.models.SessionsModel;
import io.reactivex.Flowable;
import io.reactivex.Single;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface SessionsDao {

    @Insert(onConflict = REPLACE)
    void saveDayOneSession(List<SessionsEntity> sessionsModelList);

    @Query("SELECT * FROM sessionsList WHERE day_number =:dayNumber")
    Flowable<List<SessionsEntity>> getDayOneSessions(String dayNumber);

    @Query("SELECT * FROM sessionsList WHERE day_number =:dayNumber")
    Flowable<List<SessionsEntity>> getDayTwoSessions(String dayNumber);
}
