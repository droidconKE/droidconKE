package droiddevelopers254.droidconke.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import droiddevelopers254.droidconke.datastates.SessionsState
import droiddevelopers254.droidconke.repository.DayOneRepo

class DayOneViewModel : ViewModel() {
    private val sessionsStateMediatorLiveData: MediatorLiveData<SessionsState> = MediatorLiveData()
    private val dayOneRepo: DayOneRepo = DayOneRepo()

    val sessions: LiveData<SessionsState>
        get() = sessionsStateMediatorLiveData

    fun getDayOneSessions() {
        val sessionsStateLiveData = dayOneRepo.dayOneSessions
        sessionsStateMediatorLiveData.addSource(sessionsStateLiveData
        ) { sessionsStateMediatorLiveData ->
            when {
                this.sessionsStateMediatorLiveData.hasActiveObservers() -> this.sessionsStateMediatorLiveData.removeSource(sessionsStateLiveData)
            }
            this.sessionsStateMediatorLiveData.setValue(sessionsStateMediatorLiveData)
        }
    }
}
