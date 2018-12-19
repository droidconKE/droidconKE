package droiddevelopers254.droidconke.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import droiddevelopers254.droidconke.datastates.AboutDetailsState
import droiddevelopers254.droidconke.repository.AboutDetailsRepo
import droiddevelopers254.droidconke.utils.EspressoIdlingResource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AboutDetailsViewModel(private val aboutDetailsRepo: AboutDetailsRepo) : ViewModel() {
    private val detailsStateMediatorLiveData: MutableLiveData<AboutDetailsState> = MutableLiveData()

    val aboutDetails: LiveData<AboutDetailsState>
        get() = detailsStateMediatorLiveData

    fun fetchAboutDetails(aboutType: String) {
        EspressoIdlingResource.increment()
        val job = GlobalScope.launch {
            val state = aboutDetailsRepo.getAboutDetails(aboutType)
            detailsStateMediatorLiveData.postValue(state)
        }
        job.invokeOnCompletion { EspressoIdlingResource.decrement() }
    }
}
