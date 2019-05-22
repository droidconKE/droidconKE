package droiddevelopers254.droidconke.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import droiddevelopers254.droidconke.datastates.AboutDetailsState
import droiddevelopers254.droidconke.di.appModule
import droiddevelopers254.droidconke.di.dataModule
import droiddevelopers254.droidconke.repository.AboutDetailsRepo
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.inject
import org.koin.test.KoinTest
import org.koin.test.declareMock

class AboutDetailsViewModelTest : KoinTest {

    private val aboutDetailsRepo: AboutDetailsRepo by inject()
    private val aboutDetailsViewModel: AboutDetailsViewModel by inject()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        startKoin(listOf(appModule, dataModule))
        declareMock<AboutDetailsRepo>()
    }

    @Test
    fun `test fetchAboutDetails`() = runBlocking {
        val state = AboutDetailsState(emptyList())
        whenever(aboutDetailsRepo.getAboutDetails(any())).thenReturn(state)

        aboutDetailsViewModel.fetchAboutDetails("value")

        delay(1000)

        Assert.assertTrue(aboutDetailsViewModel.getAboutDetailsResponse.value?.aboutDetailsModelList?.isEmpty()
                ?: false)
    }
}