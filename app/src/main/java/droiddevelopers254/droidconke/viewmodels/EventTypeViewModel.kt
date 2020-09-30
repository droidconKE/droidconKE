package droiddevelopers254.droidconke.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import droiddevelopers254.droidconke.datastates.Result
import droiddevelopers254.droidconke.models.EventTypeModel
import droiddevelopers254.droidconke.repository.EventTypeRepo
import droiddevelopers254.droidconke.utils.NonNullMediatorLiveData
import kotlinx.coroutines.launch

class EventTypeViewModel(private val eventTypeRepo: EventTypeRepo) : ViewModel() {
  private val eventTypeModelMediatorLiveData = NonNullMediatorLiveData<List<EventTypeModel>>()
  private val eventDetailsError = NonNullMediatorLiveData<String>()


  fun getWifiDetailsResponse(): LiveData<List<EventTypeModel>> = eventTypeModelMediatorLiveData

  fun getWifiDetailsError(): LiveData<String> = eventDetailsError


  fun fetchSessions() {
    viewModelScope.launch {
      when (val value = eventTypeRepo.getSessionData()) {
          is Result.Success -> eventTypeModelMediatorLiveData.postValue(value.data)
          is Result.Error -> eventDetailsError.postValue(value.exception)
      }
    }

  }
}
