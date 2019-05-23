package droiddevelopers254.droidconke.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import droiddevelopers254.droidconke.CoroutinesRule
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

//        `when`(aboutDetailsRepo.getAboutDetails(any())).thenReturn(Result<List<AboutDetailsModel>>())

        aboutDetailsViewModel.fetchAboutDetails("value")

        aboutDetailsViewModel.getAboutDetailsResponse().observeOnce {
            Assert.assertTrue(it.isEmpty() ?: false)
        }

    }
}