package droiddevelopers254.droidconke.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import droiddevelopers254.droidconke.CoroutinesRule
import droiddevelopers254.droidconke.datastates.SessionsState
import droiddevelopers254.droidconke.observeOnce
import droiddevelopers254.droidconke.repository.DayTwoRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.mock.declareMock
import org.mockito.Mockito

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class DayTwoViewModelTest : KoinTest {

    private val dayTwoRepo: DayTwoRepo by inject()
    private val dayTwoViewModel: DayTwoViewModel by inject()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesRule = CoroutinesRule()

    @Before
    fun setup() {
        declareMock<DayTwoRepo>()
    }

    @Test
    fun `test getDayTwoSessions`() = runBlocking {
        val state = SessionsState(emptyList())
        Mockito.`when`(dayTwoRepo.getSessions()).thenReturn(MutableLiveData<SessionsState>().apply { value = state })

        dayTwoViewModel.getDayTwoSessions()

        dayTwoViewModel.sessions.observeOnce {
            Assert.assertTrue(it.sessionsModelList?.isEmpty() ?: false)
        }

    }
}