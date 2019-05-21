package droiddevelopers254.droidconke.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.nhaarman.mockitokotlin2.mock
import droiddevelopers254.droidconke.CoroutinesRule
import droiddevelopers254.droidconke.datastates.FeedBackState
import droiddevelopers254.droidconke.datastates.RoomState
import droiddevelopers254.droidconke.datastates.SessionDataState
import droiddevelopers254.droidconke.datastates.SpeakersState
import droiddevelopers254.droidconke.models.RoomModel
import droiddevelopers254.droidconke.models.SessionsModel
import droiddevelopers254.droidconke.models.SessionsUserFeedback
import droiddevelopers254.droidconke.observeOnce
import droiddevelopers254.droidconke.repository.RoomRepo
import droiddevelopers254.droidconke.repository.SessionDataRepo
import droiddevelopers254.droidconke.repository.SessionFeedbackRepo
import droiddevelopers254.droidconke.repository.SpeakersRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.mock.declareMock
import org.mockito.Mockito.`when`

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class SessionDataViewModelTest : KoinTest {

    private val speakersRepo: SpeakersRepo by inject()
    private val roomRepo: RoomRepo by inject()
    private val sessionDataRepo: SessionDataRepo by inject()
    private val sessionDataViewModel: SessionDataViewModel by inject()
    private val sessionFeedbackRepo: SessionFeedbackRepo by inject()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesRule = CoroutinesRule()

    @Test
    fun `test fetchSpeakerDetails`() = runBlocking {
        declareMock<SpeakersRepo>()

        val state = SpeakersState(emptyList(), null)
        `when`(speakersRepo.getSpeakersInfo(1)).thenReturn(MutableLiveData<SpeakersState>()
                .apply { value = state })

        sessionDataViewModel.fetchSpeakerDetails(1)

        sessionDataViewModel.speakerInfo.observeOnce {
            Assert.assertTrue(it.speakerModelList?.isEmpty() ?: false)
        }
    }

    @Test
    fun `test fetchRoomDetails`() = runBlocking {
        declareMock<RoomRepo>()
        val model = RoomModel()
        val state = RoomState(model, null)
        `when`(roomRepo.getRoomDetails(1)).thenReturn(MutableLiveData<RoomState>()
                .apply { value = state })
        sessionDataViewModel.fetchRoomDetails(1)
        sessionDataViewModel.roomInfo.observeOnce {
            Assert.assertNotNull(it.roomModel)
        }
    }

    @Test
    fun `test getSessionDetails`() = runBlocking {
        declareMock<SessionDataRepo>()
        val model = SessionsModel()
        val state = SessionDataState(model, null)
        `when`(sessionDataRepo.getSessionData("", 1)).thenReturn(MutableLiveData<SessionDataState>()
                .apply { value = state })

        sessionDataViewModel.getSessionDetails("", 1)
        sessionDataViewModel.session.observeOnce {
            Assert.assertNotNull(it)
        }
    }

    @Test
    fun `test sendSessionFeedBack`() = runBlocking {
        declareMock<SessionFeedbackRepo>()
        val feedback = mock<SessionsUserFeedback>()
        val state = FeedBackState("")
        `when`(sessionFeedbackRepo.sendFeedBack(feedback)).thenReturn(MutableLiveData<FeedBackState>()
                .apply { value = state })
        sessionDataViewModel.sendSessionFeedBack(feedback)
        sessionDataViewModel.getSessionFeedBackResponse().observeOnce {
            Assert.assertEquals(state, it)
        }
    }
}