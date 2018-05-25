package droiddevelopers254.droidconke.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;

import droiddevelopers254.droidconke.datastates.SessionDataState;
import droiddevelopers254.droidconke.datastates.SpeakersState;
import droiddevelopers254.droidconke.repository.SessionDataRepo;
import droiddevelopers254.droidconke.repository.SpeakersRepo;

public class SessionDataViewModel extends ViewModel {
    private MediatorLiveData<SessionDataState> sessionDataStateMediatorLiveData;
    private SessionDataRepo sessionDataRepo;
    private MediatorLiveData<SpeakersState> speakersStateMediatorLiveData;
    private SpeakersRepo speakersRepo;


    public SessionDataViewModel (){
        sessionDataStateMediatorLiveData= new MediatorLiveData<>();
        sessionDataRepo = new SessionDataRepo();
        speakersStateMediatorLiveData= new MediatorLiveData<>();
        speakersRepo= new SpeakersRepo();

    }

    public LiveData<SessionDataState> getSessionDetails(){
        return  sessionDataStateMediatorLiveData;
    }
    public LiveData<SpeakersState> getSpeakerInfo(){
        return speakersStateMediatorLiveData;
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

    public void fetchSpeakerDetails(String speakerId){
        final LiveData<SpeakersState> speakersStateLiveData= speakersRepo.getSpeakersInfo(speakerId);
        speakersStateMediatorLiveData.addSource(speakersStateLiveData,
                speakersStateMediatorLiveData ->{
            if (this.speakersStateMediatorLiveData.hasActiveObservers()){
                this.speakersStateMediatorLiveData.removeSource(speakersStateLiveData);
            }
            this.speakersStateMediatorLiveData.setValue(speakersStateMediatorLiveData);
                });
    }
}
