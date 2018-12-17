package droiddevelopers254.droidconke.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import droiddevelopers254.droidconke.datastates.AgendaState
import droiddevelopers254.droidconke.repository.AgendaRepo

class AgendaViewModel : ViewModel() {
    private val agendaStateMediatorLiveData: MediatorLiveData<AgendaState> = MediatorLiveData()
    private val agendaRepo: AgendaRepo = AgendaRepo()

    val agendas: LiveData<AgendaState>
        get() = agendaStateMediatorLiveData

    fun fetchAgendas() {
        val agendaStateLiveData = agendaRepo.agendaData
        agendaStateMediatorLiveData.addSource(agendaStateLiveData
        ) { agendaStateMediatorLiveData ->
            when {
                this.agendaStateMediatorLiveData.hasActiveObservers() -> this.agendaStateMediatorLiveData.removeSource(agendaStateLiveData)
            }
            this.agendaStateMediatorLiveData.setValue(agendaStateMediatorLiveData)
        }
    }
}
