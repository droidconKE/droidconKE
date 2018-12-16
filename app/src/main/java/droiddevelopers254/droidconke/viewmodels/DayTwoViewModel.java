package droiddevelopers254.droidconke.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import droiddevelopers254.droidconke.datastates.SessionsState;
import droiddevelopers254.droidconke.repository.DayTwoRepo;
import droiddevelopers254.droidconke.repository.RoomStarrSessionRepo;

public class DayTwoViewModel extends ViewModel{
    private MediatorLiveData<SessionsState> sessionsStateMediatorLiveData;
    private DayTwoRepo dayTwoRepo;
    private RoomStarrSessionRepo roomStarrSessionRepo;

    public DayTwoViewModel(){
        dayTwoRepo = new DayTwoRepo();
        sessionsStateMediatorLiveData = new MediatorLiveData<>();
        roomStarrSessionRepo = new RoomStarrSessionRepo();
    }

    public LiveData<SessionsState> getSessions(){
        return sessionsStateMediatorLiveData;
    }

    public void getDayTwoSessions(){
        final LiveData<SessionsState> sessionsStateLiveData = dayTwoRepo.getDayTwoSessions();
        sessionsStateMediatorLiveData.addSource(sessionsStateLiveData,
                sessionsStateMediatorLiveData ->{
                    if (this.sessionsStateMediatorLiveData.hasActiveObservers()){
                        this.sessionsStateMediatorLiveData.removeSource(sessionsStateLiveData);
                    }
                    this.sessionsStateMediatorLiveData.setValue(sessionsStateMediatorLiveData);
                });
    }

}
