package droiddevelopers254.droidconke.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import droiddevelopers254.droidconke.database.entities.StarredSessionEntity;
import droiddevelopers254.droidconke.models.StarredSessionModel;
import io.reactivex.Single;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface StarredSessionDao {

    @Query("SELECT * FROM starredSessions")
    Single<List<StarredSessionModel>> getStarredSessions();

    @Insert(onConflict = REPLACE)
    void starSession(StarredSessionModel starredSessionModel);

    @Query("UPDATE  starredSessions SET starred=:starred WHERE documentId =:documentId")
    void unStarSession(boolean starred,String documentId);

    @Query("SELECT starred FROM starredSessions WHERE documentId LIKE :documentId ")
    boolean isSessionStarred(String documentId);

}
