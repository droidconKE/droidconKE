package droiddevelopers254.droidconke.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity
data class FiltersModel (
        @PrimaryKey
        val id: Int =0,
        val isChecked: Boolean = false,
        val name: String= ""
)
