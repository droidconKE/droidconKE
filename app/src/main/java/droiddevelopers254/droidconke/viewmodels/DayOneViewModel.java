package droiddevelopers254.droidconke.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;

import droiddevelopers254.droidconke.datastates.SessionsState;
import droiddevelopers254.droidconke.repository.DayOneRepo;

public class DayOneViewModel extends ViewModel {
    private MediatorLiveData<SessionsState> sessionsStateMediatorLiveData;
    private DayOneRepo dayOneRepo;

    public DayOneViewModel(){
        sessionsStateMediatorLiveData= new MediatorLiveData<>();
        dayOneRepo= new DayOneRepo();
    }

    public LiveData<SessionsState> getSessions(){
        return sessionsStateMediatorLiveData;
    }
    public void fetchDayOneSessions(){
        final LiveData<SessionsState> sessionsStateLiveData= dayOneRepo.getSessionData();
        sessionsStateMediatorLiveData.addSource(sessionsStateLiveData,
                sessionsStateMediatorLiveData ->{
            if (this.sessionsStateMediatorLiveData.hasActiveObservers()){
                this.sessionsStateMediatorLiveData.removeSource(sessionsStateLiveData);
            }
            this.sessionsStateMediatorLiveData.setValue(sessionsStateMediatorLiveData);
                });
    }
}
