package droiddevelopers254.droidconke.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import droiddevelopers254.droidconke.datastates.Result
import droiddevelopers254.droidconke.models.AboutDetailsModel
import droiddevelopers254.droidconke.repository.AboutDetailsRepo
import droiddevelopers254.droidconke.utils.NonNullMediatorLiveData
import kotlinx.coroutines.launch

class AboutDetailsViewModel(private val aboutDetailsRepo: AboutDetailsRepo) : ViewModel() {
    private val detailsStateMediatorLiveData = NonNullMediatorLiveData<List<AboutDetailsModel>>()
    private val detailsError = NonNullMediatorLiveData<String>()


    fun getAboutDetailsResponse(): LiveData<List<AboutDetailsModel>> = detailsStateMediatorLiveData

    fun getAboutDetailsError(): LiveData<String> = detailsError

    fun fetchAboutDetails(aboutType: String) {

        viewModelScope.launch {
            when (val value = aboutDetailsRepo.getAboutDetails(aboutType)) {
                is Result.Success -> detailsStateMediatorLiveData.postValue(value.data)
                is Result.Error -> detailsError.postValue(value.exception)
            }
        }
    }

}
