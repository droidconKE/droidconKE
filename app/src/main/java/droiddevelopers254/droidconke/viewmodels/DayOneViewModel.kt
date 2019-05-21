package droiddevelopers254.droidconke.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import droiddevelopers254.droidconke.datastates.SessionsState
import droiddevelopers254.droidconke.repository.DayOneRepo
import droiddevelopers254.droidconke.utils.launchIdling

class DayOneViewModel(private val dayOneRepo: DayOneRepo) : BaseViewModel() {
    private val sessionsStateMediatorLiveData: MediatorLiveData<SessionsState> = MediatorLiveData()
//    private val roomStarrSessionRepo: RoomStarrSessionRepo = RoomStarrSessionRepo()

    val sessions: LiveData<SessionsState>
        get() = sessionsStateMediatorLiveData

    fun getDayOneSessions() {

        launchIdling {
            val sessionsStateLiveData = dayOneRepo.getSessions()
            sessionsStateMediatorLiveData.addSource(sessionsStateLiveData) {
                when {
                    sessionsStateLiveData.hasActiveObservers() -> sessionsStateMediatorLiveData.removeSource(sessionsStateLiveData)
                }
                sessionsStateMediatorLiveData.value = it
            }
        }
    }
}
