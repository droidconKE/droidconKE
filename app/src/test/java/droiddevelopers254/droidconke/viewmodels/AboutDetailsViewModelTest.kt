package droiddevelopers254.droidconke.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import droiddevelopers254.droidconke.datastates.AboutDetailsState
import droiddevelopers254.droidconke.di.appModule
import droiddevelopers254.droidconke.di.dataModule
import droiddevelopers254.droidconke.repository.AboutDetailsRepo
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.inject
import org.koin.test.KoinTest
import org.koin.test.declareMock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyString

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
        `when`(aboutDetailsRepo.getAboutDetails(anyString())).thenReturn(state)

        aboutDetailsViewModel.fetchAboutDetails(anyString())

        Assert.assertTrue(aboutDetailsViewModel.aboutDetails.value?.aboutDetailsModelList?.isEmpty()
                ?: false)
    }
}