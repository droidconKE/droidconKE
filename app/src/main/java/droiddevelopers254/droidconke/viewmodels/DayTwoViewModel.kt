package droiddevelopers254.droidconke.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import droiddevelopers254.droidconke.datastates.SessionsState
import droiddevelopers254.droidconke.repository.DayTwoRepo
import droiddevelopers254.droidconke.utils.launchIdling

class DayTwoViewModel(private val dayTwoRepo: DayTwoRepo) : BaseViewModel() {
    private val sessionsStateMediatorLiveData: MediatorLiveData<SessionsState> = MediatorLiveData()
//    private val roomStarrSessionRepo: RoomStarrSessionRepo = RoomStarrSessionRepo()

    val sessions: LiveData<SessionsState>
        get() = sessionsStateMediatorLiveData

    fun getDayTwoSessions() {
        launchIdling {
            val sessionsStateLiveData = dayTwoRepo.getSessions()
            sessionsStateMediatorLiveData.addSource(sessionsStateLiveData) {
                when {
                    sessionsStateLiveData.hasActiveObservers() -> sessionsStateMediatorLiveData.removeSource(sessionsStateLiveData)
                }
                sessionsStateMediatorLiveData.value = it
            }
        }
    }

}
