package droiddevelopers254.droidconke.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;

import droiddevelopers254.droidconke.datastates.EventTypeState;
import droiddevelopers254.droidconke.models.EventTypeModel;
import droiddevelopers254.droidconke.models.SessionsModel;
import droiddevelopers254.droidconke.repository.EventTypeRepo;

public class EventTypeViewModel extends ViewModel {
    private MediatorLiveData<EventTypeState> eventTypeModelMediatorLiveData;
    private EventTypeRepo eventTypeRepo;

    public EventTypeViewModel(){
        eventTypeModelMediatorLiveData= new MediatorLiveData<>();
        eventTypeRepo= new EventTypeRepo();
    }

    public LiveData<EventTypeState> getSessions(){
        return eventTypeModelMediatorLiveData;
    }

    public void fetchSessions(){
        final LiveData<EventTypeState> eventTypeModelLiveData=eventTypeRepo.getSessionData();
        eventTypeModelMediatorLiveData.addSource(eventTypeModelLiveData,
                sessionsModelMediatorLiveData -> {
            if (this.eventTypeModelMediatorLiveData.hasActiveObservers()){
                this.eventTypeModelMediatorLiveData.removeSource(eventTypeModelLiveData);
            }
            this.eventTypeModelMediatorLiveData.setValue(sessionsModelMediatorLiveData);
                });
    }
}
