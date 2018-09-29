package droiddevelopers254.droidconke.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;

import droiddevelopers254.droidconke.datastates.EventTypeState;
import droiddevelopers254.droidconke.models.EventTypeModel;
import droiddevelopers254.droidconke.models.SessionsModel;
import droiddevelopers254.droidconke.models.WifiDetailsModel;
import droiddevelopers254.droidconke.repository.EventTypeRepo;
import droiddevelopers254.droidconke.repository.WifiDetailsRepo;

public class EventTypeViewModel extends ViewModel {
    private MediatorLiveData<EventTypeState> eventTypeModelMediatorLiveData;
    private EventTypeRepo eventTypeRepo;
    private MediatorLiveData<WifiDetailsModel> wifiDetailsModelMediatorLiveData;
    private WifiDetailsRepo wifiDetailsRepo;

    public EventTypeViewModel(){
        eventTypeModelMediatorLiveData= new MediatorLiveData<>();
        eventTypeRepo= new EventTypeRepo();
        wifiDetailsModelMediatorLiveData= new MediatorLiveData<>();
        wifiDetailsRepo= new WifiDetailsRepo();
    }
    public LiveData<EventTypeState> getSessions(){
        return eventTypeModelMediatorLiveData;
    }

    public LiveData<WifiDetailsModel> getWifiDetails(){
        return wifiDetailsModelMediatorLiveData;
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
    public void fetchRemoteConfigValues(){
        final LiveData<WifiDetailsModel>wifiDetailsModelLiveData=wifiDetailsRepo.getWifiDetails();
        wifiDetailsModelMediatorLiveData.addSource(wifiDetailsModelLiveData,
                wifiDetailsModelMediatorLiveData->{
            if (this.wifiDetailsModelMediatorLiveData.hasActiveObservers()){
                this.wifiDetailsModelMediatorLiveData.removeSource(wifiDetailsModelLiveData);
            }
            this.wifiDetailsModelMediatorLiveData.setValue(wifiDetailsModelMediatorLiveData);
                });
    }
}
