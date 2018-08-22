package droiddevelopers254.droidconke.datastates

import droiddevelopers254.droidconke.models.AgendaModel

data class AgendaState (
        val agendaModelList: List<AgendaModel>? =null,
        val databaseError :String? =null
)
