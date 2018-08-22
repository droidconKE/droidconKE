package droiddevelopers254.droidconke.datastates

import droiddevelopers254.droidconke.models.StarredSessionModel

data class StarSessionState (
        val isStarred : Boolean = false,
        val databaseError : String?,
        val starredSessionModel: StarredSessionModel?,
        val starMessage : Int?
)