package droiddevelopers254.droidconke.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "starredSessions")
data class StarredSessionModel (
        @PrimaryKey
        val documentId: String,
        val day: String,
        val session_id: String,
        val user_id: String,
        val isStarred: Boolean
)
