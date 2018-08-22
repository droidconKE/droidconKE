package droiddevelopers254.droidconke.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.ViewModel

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
            if (this.agendaStateMediatorLiveData.hasActiveObservers()) {
                this.agendaStateMediatorLiveData.removeSource(agendaStateLiveData)
            }
            this.agendaStateMediatorLiveData.setValue(agendaStateMediatorLiveData)
        }
    }
}
