package droiddevelopers254.droidconke.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import droiddevelopers254.droidconke.datastates.Result
import droiddevelopers254.droidconke.repository.UpdateTokenRepo
import kotlinx.coroutines.launch

class HomeViewModel(private val updateTokenRepo: UpdateTokenRepo) : ViewModel() {
  private val updateTokenStateMediatorLiveData = MutableLiveData<Boolean>()
  private val updateTokenError = MutableLiveData<String>()

  fun getUpdateTokenResponse(): LiveData<Boolean> = updateTokenStateMediatorLiveData

  fun getUpdateTokenError(): LiveData<String> = updateTokenError


  fun updateToken(userId: String, refreshToken: String) {
    viewModelScope.launch {
      when (val value = updateTokenRepo.updateToken(userId, refreshToken)) {
          is Result.Success -> updateTokenStateMediatorLiveData.postValue(value.data)
          is Result.Error -> updateTokenError.postValue(value.exception)
      }
    }

  }
}
