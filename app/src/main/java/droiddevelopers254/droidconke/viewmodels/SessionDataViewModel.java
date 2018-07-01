package droiddevelopers254.droidconke.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;

import droiddevelopers254.droidconke.datastates.RoomState;
import droiddevelopers254.droidconke.datastates.SessionDataState;
import droiddevelopers254.droidconke.datastates.SpeakersState;
import droiddevelopers254.droidconke.datastates.StarSessionState;
import droiddevelopers254.droidconke.models.StarredSessionModel;
import droiddevelopers254.droidconke.repository.RoomRepo;
import droiddevelopers254.droidconke.repository.SessionDataRepo;
import droiddevelopers254.droidconke.repository.SpeakersRepo;
import droiddevelopers254.droidconke.repository.StarSessionRepo;

public class SessionDataViewModel extends ViewModel {
    private MediatorLiveData<SessionDataState> sessionDataStateMediatorLiveData;
    private SessionDataRepo sessionDataRepo;
    private MediatorLiveData<SpeakersState> speakersStateMediatorLiveData;
    private SpeakersRepo speakersRepo;
    private MediatorLiveData<RoomState> roomStateMediatorLiveData;
    private RoomRepo roomRepo;
    private MediatorLiveData<StarSessionState> starMediatorLiveData;
    private MediatorLiveData<StarSessionState> unStarMediatorLiveData;
    private MediatorLiveData<StarSessionState> starStatusMediatorLiveData;
    private StarSessionRepo starSessionRepo;
    private MediatorLiveData<StarSessionState> starUserSessionMediatorLiveData;
    private MediatorLiveData<StarSessionState> unStarUserSessionMediatorLiveData;

    public SessionDataViewModel (){
        sessionDataStateMediatorLiveData= new MediatorLiveData<>();
        sessionDataRepo = new SessionDataRepo();
        speakersStateMediatorLiveData= new MediatorLiveData<>();
        speakersRepo= new SpeakersRepo();
        roomStateMediatorLiveData= new MediatorLiveData<>();
        roomRepo= new RoomRepo();
        starMediatorLiveData=new MediatorLiveData<>();
        unStarMediatorLiveData = new MediatorLiveData<>();
        starStatusMediatorLiveData = new MediatorLiveData<>();
        starSessionRepo= new StarSessionRepo();
        starUserSessionMediatorLiveData= new MediatorLiveData<>();
        unStarUserSessionMediatorLiveData= new MediatorLiveData<>();

    }

    public LiveData<SessionDataState> getSessionDetails(){
        return  sessionDataStateMediatorLiveData;
    }
    public LiveData<SpeakersState> getSpeakerInfo(){
        return speakersStateMediatorLiveData;
    }

    public LiveData<RoomState> getRoomInfo(){
        return roomStateMediatorLiveData;
    }

    public LiveData<StarSessionState> getStarStatus(){
        return starStatusMediatorLiveData;
    }
    public LiveData<StarSessionState> starSessionResponse(){
        return starMediatorLiveData;
    }
    public LiveData<StarSessionState> unstarSessionResponse(){
        return unStarMediatorLiveData;
    }
    public LiveData<StarSessionState> starUserSessionResponse(){
        return starUserSessionMediatorLiveData;
    }
    public LiveData<StarSessionState> unStarUserSessionResponse(){
        return unStarUserSessionMediatorLiveData;
    }

    public void fetchSessionDetails(String dayNumber,int sessionId){
        final LiveData<SessionDataState> sessionDataStateLiveData= sessionDataRepo.getSessionData(dayNumber,sessionId);
        sessionDataStateMediatorLiveData.addSource(sessionDataStateLiveData,
                sessionDataStateMediatorLiveData-> {
            if (this.sessionDataStateMediatorLiveData.hasActiveObservers()){
                this.sessionDataStateMediatorLiveData.removeSource(sessionDataStateLiveData);
            }
            this.sessionDataStateMediatorLiveData.setValue(sessionDataStateMediatorLiveData);
                });
    }

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
    public void getStarStatus(String sessionId){
        final LiveData<StarSessionState> starSessionStateLiveData=starSessionRepo.checkStarStatus(sessionId);
        starStatusMediatorLiveData.addSource(starSessionStateLiveData,
                starStatusMediatorLiveData->{
            if (this.starStatusMediatorLiveData.hasActiveObservers()){
                this.starStatusMediatorLiveData.removeSource(starSessionStateLiveData);
            }
            this.starStatusMediatorLiveData.setValue(starStatusMediatorLiveData);
                });
    }
    public void starSession(StarredSessionModel starredSessionModel){
        final LiveData<StarSessionState> starSessionStateLiveData=starSessionRepo.starSession(starredSessionModel);
        starMediatorLiveData.addSource(starSessionStateLiveData,
                starMediatorLiveData->{
            if (this.starMediatorLiveData.hasActiveObservers()){
                this.starMediatorLiveData.removeSource(starSessionStateLiveData);
            }
            this.starMediatorLiveData.setValue(starMediatorLiveData);
                });
    }
    public void unStarSession(String sessionId){
        final LiveData<StarSessionState> starSessionStateLiveData=starSessionRepo.unStarSession(sessionId);
        unStarMediatorLiveData.addSource(starSessionStateLiveData,
                unStarMediatorLiveData->{
            if (this.unStarMediatorLiveData.hasActiveObservers()){
                this.unStarMediatorLiveData.removeSource(starSessionStateLiveData);
            }
            this.unStarMediatorLiveData.setValue(unStarMediatorLiveData);
                });
    }

    public void starUserSession(StarredSessionModel starredSessionModel,String userId){
        final LiveData<StarSessionState> starSessionStateLiveData=starSessionRepo.starUserSession(starredSessionModel, userId);
        starUserSessionMediatorLiveData.addSource(starSessionStateLiveData,
                starUserSessionMediatorLiveData->{
            if (this.starUserSessionMediatorLiveData.hasActiveObservers()){
                this.starUserSessionMediatorLiveData.removeSource(starSessionStateLiveData);
            }
            this.starUserSessionMediatorLiveData.setValue(starUserSessionMediatorLiveData);
                });
    }
    public void unStarUserSession(String sessionId,String userId,boolean starred){
        final LiveData<StarSessionState> starSessionStateLiveData=starSessionRepo.unStarUserSession(sessionId, userId, starred);
        unStarUserSessionMediatorLiveData.addSource(starSessionStateLiveData,
                unStarUserSessionMediatorLiveData->{
            if (this.unStarUserSessionMediatorLiveData.hasActiveObservers()){
                this.unStarUserSessionMediatorLiveData.removeSource(starSessionStateLiveData);
            }
            this.unStarUserSessionMediatorLiveData.setValue(unStarUserSessionMediatorLiveData);
                });
    }
}
