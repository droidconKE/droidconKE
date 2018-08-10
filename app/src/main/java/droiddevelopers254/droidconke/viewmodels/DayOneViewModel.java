package droiddevelopers254.droidconke.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;

import droiddevelopers254.droidconke.database.entities.StarredSessionEntity;
import droiddevelopers254.droidconke.datastates.SessionsState;
import droiddevelopers254.droidconke.repository.DayOneRepo;
import droiddevelopers254.droidconke.repository.StarrSessionRepo;

public class DayOneViewModel extends ViewModel {
    private MediatorLiveData<SessionsState> sessionsStateMediatorLiveData;
    private DayOneRepo dayOneRepo;
    private StarrSessionRepo starrSessionRepo;

    public DayOneViewModel(){
        dayOneRepo= new DayOneRepo();
        sessionsStateMediatorLiveData = new MediatorLiveData<>();
        starrSessionRepo = new StarrSessionRepo();
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
