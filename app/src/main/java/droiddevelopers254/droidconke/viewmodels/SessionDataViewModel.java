package droiddevelopers254.droidconke.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;

import droiddevelopers254.droidconke.datastates.FeedBackState;
import droiddevelopers254.droidconke.datastates.RoomState;
import droiddevelopers254.droidconke.datastates.SessionDataState;
import droiddevelopers254.droidconke.datastates.SpeakersState;
import droiddevelopers254.droidconke.datastates.StarSessionState;
import droiddevelopers254.droidconke.models.SessionsModel;
import droiddevelopers254.droidconke.models.SessionsUserFeedback;
import droiddevelopers254.droidconke.models.StarredSessionModel;
import droiddevelopers254.droidconke.repository.RoomRepo;
import droiddevelopers254.droidconke.repository.SessionDataRepo;
import droiddevelopers254.droidconke.repository.SessionFeedbackRepo;
import droiddevelopers254.droidconke.repository.SpeakersRepo;
import droiddevelopers254.droidconke.repository.FirebaseStarSessionRepo;
import droiddevelopers254.droidconke.repository.RoomStarrSessionRepo;

public class SessionDataViewModel extends ViewModel {
    private MediatorLiveData<SessionDataState> sessionDataStateMediatorLiveData;
    private SessionDataRepo sessionDataRepo;
    private MediatorLiveData<SpeakersState> speakersStateMediatorLiveData;
    private SpeakersRepo speakersRepo;
    private MediatorLiveData<RoomState> roomStateMediatorLiveData;
    private RoomRepo roomRepo;
    private MediatorLiveData<SessionsModel> sessionsModelMediatorLiveData;
    private RoomStarrSessionRepo roomStarrSessionRepo;
    private MediatorLiveData<Integer> booleanMediatorLiveData;
    private SessionFeedbackRepo sessionFeedbackRepo;
    private MediatorLiveData<FeedBackState> sessionFeedBackMediatorLiveData;

    public SessionDataViewModel (){
        sessionDataStateMediatorLiveData= new MediatorLiveData<>();
        sessionDataRepo = new SessionDataRepo();
        speakersStateMediatorLiveData= new MediatorLiveData<>();
        speakersRepo= new SpeakersRepo();
        roomStateMediatorLiveData= new MediatorLiveData<>();
        roomRepo= new RoomRepo();
        sessionsModelMediatorLiveData = new MediatorLiveData<>();
        booleanMediatorLiveData = new MediatorLiveData<>();
        roomStarrSessionRepo = new RoomStarrSessionRepo();
        sessionFeedbackRepo =  new SessionFeedbackRepo();
        sessionFeedBackMediatorLiveData = new MediatorLiveData<>();

    }

    public LiveData<SessionDataState> getSessionData(){return sessionDataStateMediatorLiveData;}

    public LiveData<SpeakersState> getSpeakerInfo(){
        return speakersStateMediatorLiveData;
    }

    public LiveData<RoomState> getRoomInfo(){
        return roomStateMediatorLiveData;
    }

    public LiveData<FeedBackState> getSessionFeedBackResponse(){return sessionFeedBackMediatorLiveData; }


    public void fetchSpeakerDetails(int speakerId){
        final LiveData<SpeakersState> speakersStateLiveData= speakersRepo.getSpeakersInfo(speakerId);
        speakersStateMediatorLiveData.addSource(speakersStateLiveData,
                speakersStateMediatorLiveData ->{
            if (this.speakersStateMediatorLiveData.hasActiveObservers()){
                this.speakersStateMediatorLiveData.removeSource(speakersStateLiveData);
            }
            this.speakersStateMediatorLiveData.setValue(speakersStateMediatorLiveData);
                });
    }

    public void fetchRoomDetails(int roomId){
        final LiveData<RoomState> roomStateLiveData=roomRepo.getRoomDetails(roomId);
        roomStateMediatorLiveData.addSource(roomStateLiveData,
                roomStateMediatorLiveData-> {
            if (this.roomStateMediatorLiveData.hasActiveObservers()){
                this.roomStateMediatorLiveData.removeSource(roomStateLiveData);
            }
            this.roomStateMediatorLiveData.setValue(roomStateMediatorLiveData);
                });
    }

    public void getSessionDetails(String dayNumber, int sessionId){
       final LiveData<SessionDataState>  sessionsModelLiveData = sessionDataRepo.getSessionData(dayNumber, sessionId);
        sessionDataStateMediatorLiveData.addSource(sessionsModelLiveData,
                sessionDataStateMediatorLiveData ->{
            if (this.sessionDataStateMediatorLiveData.hasActiveObservers()){
                this.sessionDataStateMediatorLiveData.removeSource(sessionsModelLiveData);

            }
            this.sessionDataStateMediatorLiveData.setValue(sessionDataStateMediatorLiveData);
                });
    }
    public void sendSessionFeedBack(SessionsUserFeedback userEventFeedback){
        final LiveData<FeedBackState> sessionFeedbackLiveData = sessionFeedbackRepo.sendFeedBack(userEventFeedback);
        sessionFeedBackMediatorLiveData.addSource(sessionFeedbackLiveData,
                sessionFeedBackMediatorLiveData ->{
                    if (this.sessionFeedBackMediatorLiveData.hasActiveObservers()){
                        this.sessionFeedBackMediatorLiveData.removeSource(sessionFeedbackLiveData);
                    }
                    this.sessionFeedBackMediatorLiveData.setValue(sessionFeedBackMediatorLiveData);
                });
    }
}
