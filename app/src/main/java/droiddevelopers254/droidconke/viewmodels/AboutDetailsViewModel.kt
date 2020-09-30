package droiddevelopers254.droidconke.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import droiddevelopers254.droidconke.datastates.Result
import droiddevelopers254.droidconke.models.AboutDetailsModel
import droiddevelopers254.droidconke.repository.AboutDetailsRepo
import kotlinx.coroutines.launch

class AboutDetailsViewModel(private val aboutDetailsRepo: AboutDetailsRepo) : ViewModel() {
  private val detailsStateMediatorLiveData = MutableLiveData<List<AboutDetailsModel>>()
  private val detailsError = MutableLiveData<String>()


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
