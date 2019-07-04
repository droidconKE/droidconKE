package droiddevelopers254.droidconke.models

data class SessionsUserFeedback(
        val user_id: String,
        val session_id: String,
        val day_number: String,
        val session_title: String,
        val session_feedback: String
)