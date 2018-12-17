package droiddevelopers254.droidconke.viewmodels

import androidx.lifecycle.ViewModel
import droiddevelopers254.droidconke.repository.RoomStarrSessionRepo

class StarrViewModel : ViewModel() {
    private val roomStarrSessionRepo: RoomStarrSessionRepo = RoomStarrSessionRepo()

}
