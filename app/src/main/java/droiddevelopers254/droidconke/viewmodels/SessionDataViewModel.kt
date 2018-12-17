package droiddevelopers254.droidconke.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import droiddevelopers254.droidconke.datastates.FeedBackState
import droiddevelopers254.droidconke.datastates.RoomState
import droiddevelopers254.droidconke.datastates.SessionDataState
import droiddevelopers254.droidconke.datastates.SpeakersState
import droiddevelopers254.droidconke.models.SessionsModel
import droiddevelopers254.droidconke.models.SessionsUserFeedback
import droiddevelopers254.droidconke.repository.RoomRepo
import droiddevelopers254.droidconke.repository.SessionDataRepo
import droiddevelopers254.droidconke.repository.SessionFeedbackRepo
import droiddevelopers254.droidconke.repository.SpeakersRepo


class SessionDataViewModel : ViewModel() {
    private val sessionDataStateMediatorLiveData: MediatorLiveData<SessionDataState> = MediatorLiveData()
    private val sessionDataRepo: SessionDataRepo = SessionDataRepo()
    private val speakersStateMediatorLiveData: MediatorLiveData<SpeakersState> = MediatorLiveData()
    private val speakersRepo: SpeakersRepo = SpeakersRepo()
    private val roomStateMediatorLiveData: MediatorLiveData<RoomState> = MediatorLiveData()
    private val roomRepo: RoomRepo = RoomRepo()
    private val sessionsModelMediatorLiveData: MediatorLiveData<SessionsModel> = MediatorLiveData()
    private val sessionFeedbackRepo :SessionFeedbackRepo = SessionFeedbackRepo()
    private val sessionFeedBackMediatorLiveData = MediatorLiveData<FeedBackState>()

    val session: LiveData<SessionsModel>
        get() = sessionsModelMediatorLiveData

    val sessionData: LiveData<SessionDataState>
        get() = sessionDataStateMediatorLiveData

    val speakerInfo: LiveData<SpeakersState>
        get() = speakersStateMediatorLiveData

    val roomInfo: LiveData<RoomState>
        get() = roomStateMediatorLiveData


    fun getSessionFeedBackResponse(): LiveData<FeedBackState> = sessionFeedBackMediatorLiveData


    fun fetchSpeakerDetails(speakerId: Int) {
        val speakersStateLiveData = speakersRepo.getSpeakersInfo(speakerId)
        speakersStateMediatorLiveData.addSource(speakersStateLiveData
        ) { speakersStateMediatorLiveData ->
            when {
                this.speakersStateMediatorLiveData.hasActiveObservers() -> this.speakersStateMediatorLiveData.removeSource(speakersStateLiveData)
            }
            this.speakersStateMediatorLiveData.setValue(speakersStateMediatorLiveData)
        }
    }

    fun fetchRoomDetails(roomId: Int) {
        val roomStateLiveData = roomRepo.getRoomDetails(roomId)
        roomStateMediatorLiveData.addSource(roomStateLiveData
        ) { roomStateMediatorLiveData ->
            when {
                this.roomStateMediatorLiveData.hasActiveObservers() -> this.roomStateMediatorLiveData.removeSource(roomStateLiveData)
            }
            this.roomStateMediatorLiveData.setValue(roomStateMediatorLiveData)
        }
    }

    fun getSessionDetails(dayNumber: String, sessionId: Int) {
        val sessionsModelLiveData = sessionDataRepo.getSessionData(dayNumber, sessionId)
        sessionDataStateMediatorLiveData.addSource(sessionsModelLiveData
        ) { sessionDataStateMediatorLiveData ->
            when {
                this.sessionDataStateMediatorLiveData.hasActiveObservers() -> this.sessionDataStateMediatorLiveData.removeSource(sessionsModelLiveData)
            }
            this.sessionDataStateMediatorLiveData.setValue(sessionDataStateMediatorLiveData)
        }
    }

    fun sendSessionFeedBack(userEventFeedback: SessionsUserFeedback) {
        val sessionFeedbackLiveData = sessionFeedbackRepo.sendFeedBack(userEventFeedback)
        sessionFeedBackMediatorLiveData.addSource(sessionFeedbackLiveData
        ) { sessionFeedBackMediatorLiveData ->
            when {
                this.sessionFeedBackMediatorLiveData.hasActiveObservers() -> this.sessionFeedBackMediatorLiveData.removeSource(sessionFeedbackLiveData)
            }
            this.sessionFeedBackMediatorLiveData.setValue(sessionFeedBackMediatorLiveData)
        }
    }
}
