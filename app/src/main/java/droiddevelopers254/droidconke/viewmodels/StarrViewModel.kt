package droiddevelopers254.droidconke.viewmodels

import android.arch.lifecycle.ViewModel

import droiddevelopers254.droidconke.repository.RoomStarrSessionRepo

class StarrViewModel : ViewModel() {
    private val roomStarrSessionRepo: RoomStarrSessionRepo = RoomStarrSessionRepo()

}
