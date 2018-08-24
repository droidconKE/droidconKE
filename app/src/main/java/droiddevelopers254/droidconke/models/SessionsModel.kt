package droiddevelopers254.droidconke.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

import java.util.ArrayList

@Entity(tableName = "sessionsList")
data class SessionsModel (
        @PrimaryKey
       var id: Int = 0,
       var speaker_id: ArrayList<Int> = ArrayList(),
       var room_id: Int = 0,
       var main_tag: String = "",
       var room: String = "",
       var speakers: String = "",
       var starred: String = "",
       var time: String = "",
       var title: String ="",
       var topic: String = "",
       var url: String = "",
       var duration: String = "",
       var description: String ="",
       var session_color: String = "",
       var topic_id: Int = 0,
       var type_id: Int = 0,
       var type: String ="",
       var documentId: String = "",
       var timestamp: String = "",
       var day_number: String = "",
       var time_in_am: String = "",
       var am_pm_label: String = ""
)

