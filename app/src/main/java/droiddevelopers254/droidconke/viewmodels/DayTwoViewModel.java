package droiddevelopers254.droidconke.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;

import droiddevelopers254.droidconke.datastates.SessionsState;
import droiddevelopers254.droidconke.repository.DayTwoRepo;

public class DayTwoViewModel extends ViewModel{
    private MediatorLiveData<SessionsState> sessionsStateMediatorLiveData;
    private DayTwoRepo dayTwoRepo;

    public DayTwoViewModel(){
        sessionsStateMediatorLiveData= new MediatorLiveData<>();
        dayTwoRepo= new DayTwoRepo();
    }

    public LiveData<SessionsState> getSessions(){
        return sessionsStateMediatorLiveData;
    }
    public void fetchDayTwoSessions(){
        final LiveData<SessionsState> sessionsStateLiveData= dayTwoRepo.getSessionData();
        sessionsStateMediatorLiveData.addSource(sessionsStateLiveData,
                sessionsStateMediatorLiveData ->{
                    if (this.sessionsStateMediatorLiveData.hasActiveObservers()){
                        this.sessionsStateMediatorLiveData.removeSource(sessionsStateLiveData);
                    }
                    this.sessionsStateMediatorLiveData.setValue(sessionsStateMediatorLiveData);
                });
    }
}
