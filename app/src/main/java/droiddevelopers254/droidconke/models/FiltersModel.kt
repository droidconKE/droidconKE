package droiddevelopers254.droidconke.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity
data class FiltersModel (
        @PrimaryKey
        val id: Int,
        val isChecked: Boolean,
        val name: String
)
