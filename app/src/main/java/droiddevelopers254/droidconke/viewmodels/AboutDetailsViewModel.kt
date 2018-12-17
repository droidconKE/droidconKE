package droiddevelopers254.droidconke.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import droiddevelopers254.droidconke.datastates.AboutDetailsState
import droiddevelopers254.droidconke.repository.AboutDetailsRepo

class AboutDetailsViewModel(private val aboutDetailsRepo: AboutDetailsRepo) : ViewModel() {
    private val detailsStateMediatorLiveData: MutableLiveData<AboutDetailsState> = MutableLiveData()

    val aboutDetails: LiveData<AboutDetailsState>
        get() = detailsStateMediatorLiveData

    fun fetchAboutDetails(aboutType: String) {
        val aboutDetailsState = aboutDetailsRepo.getAboutDetails(aboutType)
        detailsStateMediatorLiveData.value = aboutDetailsState
    }
}
