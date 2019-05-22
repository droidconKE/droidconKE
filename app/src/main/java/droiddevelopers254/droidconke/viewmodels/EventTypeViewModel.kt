package droiddevelopers254.droidconke.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import droiddevelopers254.droidconke.datastates.EventTypeState
import droiddevelopers254.droidconke.models.WifiDetailsModel
import droiddevelopers254.droidconke.repository.EventTypeRepo

class EventTypeViewModel : ViewModel() {
    private val eventTypeModelMediatorLiveData: MediatorLiveData<EventTypeState> = MediatorLiveData()
    private val eventTypeRepo: EventTypeRepo = EventTypeRepo()
    private val wifiDetailsModelMediatorLiveData: MediatorLiveData<WifiDetailsModel> = MediatorLiveData()
    val sessions: LiveData<EventTypeState>
        get() = eventTypeModelMediatorLiveData

    val wifiDetails: LiveData<WifiDetailsModel>
        get() = wifiDetailsModelMediatorLiveData

    fun fetchSessions() {
        val eventTypeModelLiveData = eventTypeRepo.sessionData
        eventTypeModelMediatorLiveData.addSource(eventTypeModelLiveData
        ) { sessionsModelMediatorLiveData ->
            when {
                this.eventTypeModelMediatorLiveData.hasActiveObservers() -> this.eventTypeModelMediatorLiveData.removeSource(eventTypeModelLiveData)
            }
            this.eventTypeModelMediatorLiveData.setValue(sessionsModelMediatorLiveData)
        }
    }
}
