package droiddevelopers254.droidconke.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import droiddevelopers254.droidconke.database.entities.StarredSessionEntity;
import io.reactivex.Single;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface StarredSessionDao {

    @Query("SELECT * FROM starredSessions")
    Single<List<StarredSessionEntity>> getStarredSessions();

    @Insert(onConflict = REPLACE)
    void starSession(StarredSessionEntity starredSessionEntity);

    @Query("DELETE FROM starredSessions WHERE id=:sessionId")
    void unStarSession(int sessionId);

}
