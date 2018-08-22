package droiddevelopers254.droidconke.datastates

import droiddevelopers254.droidconke.models.FiltersModel

data class FiltersState (
        val filtersModelList: List<FiltersModel>?,
        val databaseError : String?
)
