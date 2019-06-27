package droiddevelopers254.droidconke.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import droiddevelopers254.droidconke.datastates.Result
import droiddevelopers254.droidconke.repository.UpdateTokenRepo
import droiddevelopers254.droidconke.utils.NonNullMediatorLiveData
import kotlinx.coroutines.launch

class HomeViewModel(private val updateTokenRepo: UpdateTokenRepo) : ViewModel() {
    private val updateTokenStateMediatorLiveData = NonNullMediatorLiveData<Boolean>()
    private val updateTokenError = NonNullMediatorLiveData<String>()

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
