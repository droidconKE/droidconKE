package droiddevelopers254.droidconke.datastates

import droiddevelopers254.droidconke.models.SpeakersModel

data class SpeakersState (
        val speakerModelList: List<SpeakersModel>?,
        val databaseError : String?
)
