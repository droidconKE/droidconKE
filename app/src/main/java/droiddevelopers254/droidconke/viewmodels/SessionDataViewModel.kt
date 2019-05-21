package droiddevelopers254.droidconke.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
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
import droiddevelopers254.droidconke.utils.launchIdling


class SessionDataViewModel(
        private val sessionDataRepo: SessionDataRepo,
        private val speakersRepo: SpeakersRepo,
        private val roomRepo: RoomRepo,
        private val sessionFeedbackRepo: SessionFeedbackRepo
) : BaseViewModel() {
    private val sessionDataStateMediatorLiveData: MediatorLiveData<SessionDataState> = MediatorLiveData()
    private val speakersStateMediatorLiveData: MediatorLiveData<SpeakersState> = MediatorLiveData()
    private val roomStateMediatorLiveData: MediatorLiveData<RoomState> = MediatorLiveData()
    private val sessionsModelMediatorLiveData: MediatorLiveData<SessionsModel> = MediatorLiveData()
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


    fun fetchSpeakerDetails(speakerId: Int) = launchIdling {
        val speakersStateLiveData = speakersRepo.getSpeakersInfo(speakerId)
        speakersStateMediatorLiveData.addSource(speakersStateLiveData
        ) {
            when {
                speakersStateMediatorLiveData.hasActiveObservers() -> speakersStateMediatorLiveData.removeSource(speakersStateLiveData)
            }
            speakersStateMediatorLiveData.setValue(it)
        }
    }

    fun fetchRoomDetails(roomId: Int) = launchIdling {
        val roomStateLiveData = roomRepo.getRoomDetails(roomId)
        roomStateMediatorLiveData.addSource(roomStateLiveData
        ) {
            when {
                roomStateMediatorLiveData.hasActiveObservers() -> roomStateMediatorLiveData.removeSource(roomStateLiveData)
            }
            roomStateMediatorLiveData.setValue(it)
        }
    }

    fun getSessionDetails(dayNumber: String, sessionId: Int) = launchIdling {
        val sessionsModelLiveData = sessionDataRepo.getSessionData(dayNumber, sessionId)
        sessionDataStateMediatorLiveData.addSource(sessionsModelLiveData
        ) { it ->
            when {
                sessionDataStateMediatorLiveData.hasActiveObservers() -> sessionDataStateMediatorLiveData.removeSource(sessionsModelLiveData)
            }
            sessionDataStateMediatorLiveData.setValue(it)
        }


    }

    fun sendSessionFeedBack(userEventFeedback: SessionsUserFeedback) = launchIdling {
        val sessionFeedbackLiveData = sessionFeedbackRepo.sendFeedBack(userEventFeedback)
        sessionFeedBackMediatorLiveData.addSource(sessionFeedbackLiveData
        ) {
            when {
                sessionFeedBackMediatorLiveData.hasActiveObservers() -> sessionFeedBackMediatorLiveData.removeSource(sessionFeedbackLiveData)
            }
            sessionFeedBackMediatorLiveData.setValue(it)
        }
    }
}
