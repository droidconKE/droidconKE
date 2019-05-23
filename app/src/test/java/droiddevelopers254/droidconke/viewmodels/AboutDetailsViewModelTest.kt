package droiddevelopers254.droidconke.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.any
import droiddevelopers254.droidconke.CoroutinesRule
import droiddevelopers254.droidconke.datastates.AboutDetailsState
import droiddevelopers254.droidconke.observeOnce
import droiddevelopers254.droidconke.repository.AboutDetailsRepo
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
import org.mockito.Mockito.`when`

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class AboutDetailsViewModelTest : KoinTest {

    private val aboutDetailsRepo: AboutDetailsRepo by inject()
    private val aboutDetailsViewModel: AboutDetailsViewModel by inject()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesRule = CoroutinesRule()

    @Before
    fun setup() {
        declareMock<AboutDetailsRepo>()
    }

    @Test
    fun `test fetchAboutDetails`() = runBlocking {
        val state = AboutDetailsState(emptyList())
        `when`(aboutDetailsRepo.getAboutDetails(any())).thenReturn(state)

        aboutDetailsViewModel.fetchAboutDetails("value")

        aboutDetailsViewModel.aboutDetails.observeOnce {
            Assert.assertTrue(it.aboutDetailsModelList?.isEmpty() ?: false)
        }

<<<<<<< HEAD
        Assert.assertTrue(aboutDetailsViewModel.getAboutDetailsResponse.value?.aboutDetailsModelList?.isEmpty()
                ?: false)
=======
>>>>>>> e38c6e6c44138357a1026e0dfdbfbfa334d66616
    }
}