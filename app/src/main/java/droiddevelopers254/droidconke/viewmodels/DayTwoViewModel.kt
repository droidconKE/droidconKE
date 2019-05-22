package droiddevelopers254.droidconke.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import droiddevelopers254.droidconke.datastates.Result
import droiddevelopers254.droidconke.models.SessionsModel
import droiddevelopers254.droidconke.repository.DayTwoRepo
import droiddevelopers254.droidconke.utils.NonNullMediatorLiveData
import kotlinx.coroutines.launch

class DayTwoViewModel : ViewModel() {
    private val sessionsStateMediatorLiveData = NonNullMediatorLiveData<List<SessionsModel>>()
    private val sessionError = NonNullMediatorLiveData<String>()
    private val dayTwoRepo: DayTwoRepo = DayTwoRepo()

    fun getSessionsResponse(): LiveData<List<SessionsModel>> = sessionsStateMediatorLiveData

    fun getSessionsError(): LiveData<String> = sessionError

    fun getDayTwoSessions() {
        viewModelScope.launch {
            when (val value = dayTwoRepo.getDayTwoSessions()) {
                is Result.Success -> sessionsStateMediatorLiveData.postValue(value.data)
                is Result.Error -> sessionError.postValue(value.exception)
            }
        }
    }

}
