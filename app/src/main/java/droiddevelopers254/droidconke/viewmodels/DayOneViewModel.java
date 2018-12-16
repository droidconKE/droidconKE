package droiddevelopers254.droidconke.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import droiddevelopers254.droidconke.datastates.SessionsState;
import droiddevelopers254.droidconke.repository.DayOneRepo;
import droiddevelopers254.droidconke.repository.RoomStarrSessionRepo;

public class DayOneViewModel extends ViewModel {
    private MediatorLiveData<SessionsState> sessionsStateMediatorLiveData;
    private DayOneRepo dayOneRepo;
    private RoomStarrSessionRepo roomStarrSessionRepo;

    public DayOneViewModel(){
        dayOneRepo= new DayOneRepo();
        sessionsStateMediatorLiveData = new MediatorLiveData<>();
        roomStarrSessionRepo = new RoomStarrSessionRepo();
    }

    public LiveData<SessionsState> getSessions(){
        return sessionsStateMediatorLiveData;
    }

    public void getDayOneSessions(){
        final LiveData<SessionsState> sessionsStateLiveData = dayOneRepo.getDayOneSessions();
        sessionsStateMediatorLiveData.addSource(sessionsStateLiveData,
                sessionsStateMediatorLiveData ->{
            if (this.sessionsStateMediatorLiveData.hasActiveObservers()){
                this.sessionsStateMediatorLiveData.removeSource(sessionsStateLiveData);
            }
            this.sessionsStateMediatorLiveData.setValue(sessionsStateMediatorLiveData);
                });
    }
}
