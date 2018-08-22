package droiddevelopers254.droidconke.datastates

import droiddevelopers254.droidconke.models.SessionsModel

data class SessionsState (
        val sessionsModelList: List<SessionsModel>?=null,
        val databaseError : String?=null
)
