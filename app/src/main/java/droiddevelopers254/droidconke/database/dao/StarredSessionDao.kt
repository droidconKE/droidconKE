package droiddevelopers254.droidconke.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import droiddevelopers254.droidconke.models.StarredSessionModel
import io.reactivex.Single

@Dao
interface StarredSessionDao {

    @get:Query("SELECT * FROM starredSessions")
    val starredSessions: Single<List<StarredSessionModel>>

    @Insert(onConflict = REPLACE)
    fun starSession(starredSessionModel: StarredSessionModel)

    @Query("UPDATE  starredSessions SET isStarred=:starred WHERE documentId =:documentId")
    fun unStarSession(starred: Boolean, documentId: String)

    @Query("SELECT isStarred FROM starredSessions WHERE documentId LIKE :documentId ")
    fun isSessionStarred(documentId: String): Boolean

}
