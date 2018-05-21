package droiddevelopers254.droidconke.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;

import droiddevelopers254.droidconke.datastates.SessionDataState;
import droiddevelopers254.droidconke.repository.SessionDataRepo;

public class SessionDataViewModel extends ViewModel {
    private MediatorLiveData<SessionDataState> sessionDataStateMediatorLiveData;
    private SessionDataRepo sessionDataRepo;


    private SessionDataViewModel (){
        sessionDataStateMediatorLiveData= new MediatorLiveData<>();
        sessionDataRepo = new SessionDataRepo();

    }

    public LiveData<SessionDataState> getSessionDetails(){
        return  sessionDataStateMediatorLiveData;
    }

    public void fetchSessionDetails(int sessionId){
        final LiveData<SessionDataState> sessionDataStateLiveData= sessionDataRepo.getSessionData(sessionId);
        sessionDataStateMediatorLiveData.addSource(sessionDataStateLiveData,
                sessionDataStateMediatorLiveData-> {
            if (this.sessionDataStateMediatorLiveData.hasActiveObservers()){
                this.sessionDataStateMediatorLiveData.removeSource(sessionDataStateLiveData);
            }
            this.sessionDataStateMediatorLiveData.setValue(sessionDataStateMediatorLiveData);
                });
    }
}
