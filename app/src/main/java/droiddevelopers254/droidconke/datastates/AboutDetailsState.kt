package droiddevelopers254.droidconke.datastates

import droiddevelopers254.droidconke.models.AboutDetailsModel

data class AboutDetailsState (
        val aboutDetailsModelList : List<AboutDetailsModel>? = null,
        val databaseError :String? = null
)

