package droiddevelopers254.droidconke.datastates

import droiddevelopers254.droidconke.models.EventTypeModel

data class EventTypeState (
        val eventTypeModelList: List<EventTypeModel>?,
        val databaseError : String?
)
