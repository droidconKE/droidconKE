package droiddevelopers254.droidconke.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import droiddevelopers254.droidconke.datastates.AboutDetailsState
import droiddevelopers254.droidconke.repository.AboutDetailsRepo
import droiddevelopers254.droidconke.utils.launchIdling

class AboutDetailsViewModel(private val aboutDetailsRepo: AboutDetailsRepo) : BaseViewModel() {
    private val detailsStateMediatorLiveData: MutableLiveData<AboutDetailsState> = MutableLiveData()

    val aboutDetails: LiveData<AboutDetailsState>
        get() = detailsStateMediatorLiveData

    fun fetchAboutDetails(aboutType: String) {
        launchIdling {
            val state = aboutDetailsRepo.getAboutDetails(aboutType)
            detailsStateMediatorLiveData.postValue(state)
        }
    }
}
