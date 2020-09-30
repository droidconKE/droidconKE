package droiddevelopers254.droidconke.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "starredSessions")
data class StarredSessionEntity(
    @PrimaryKey
    var id: Int = 0,
    var speaker_id: ArrayList<Int>? = null,
    var room_id: Int = 0,
    var main_tag: String? = null,
    var room: String? = null,
    var speakers: String? = null,
    var starred: Boolean = false,
    var time: String? = null,
    var title: String? = null,
    var topic: String? = null,
    var url: String? = null,
    var duration: String? = null,
    var description: String? = null,
    var session_color: String? = null,
    var topic_id: Int = 0,
    var type_id: Int = 0,
    var type: String? = null,
    var documentId: String? = null,
    var timestamp: String? = null,
    var day_number: String? = null
)
