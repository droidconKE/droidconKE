package droiddevelopers254.droidconke.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

import java.util.ArrayList

@Entity(tableName = "sessionsList")
data class SessionsModel (
        @PrimaryKey
        val id: Int,
        val speaker_id: ArrayList<Int>,
        val room_id: Int,
        val main_tag: String,
        val room: String,
        val speakers: String,
        val isStarred: String,
        val time: String,
        val title: String,
        val topic: String,
        val url: String,
        val duration: String,
        val description: String,
        val session_color: String,
        val topic_id: Int,
        val type_id: Int,
        val type: String,
        val documentId: String,
        val timestamp: String,
        val day_number: String,
        val time_in_am: String,
        val am_pm_label: String
)

