package droiddevelopers254.droidconke.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class FiltersModel(
    @PrimaryKey
    val id: Int = 0,
    val isChecked: Boolean = false,
    val name: String = ""
)
