package droiddevelopers254.droidconke.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import droiddevelopers254.droidconke.datastates.Result
import droiddevelopers254.droidconke.models.UserEventFeedback
import droiddevelopers254.droidconke.repository.EventFeedbackRepo
import droiddevelopers254.droidconke.utils.NonNullMediatorLiveData
import kotlinx.coroutines.launch


class FeedBackViewModel(private val eventFeedBackRepo : EventFeedbackRepo) : ViewModel() {
    private val feedBackStateMediatorLiveData = NonNullMediatorLiveData<String>()
    private val eventFeedbackError = NonNullMediatorLiveData<String>()

    fun getEventFeedBackResponse(): LiveData<String> = feedBackStateMediatorLiveData

    fun getEventFeedbackError(): LiveData<String> = eventFeedbackError


    fun sendEventFeedBack(userEventFeedback: UserEventFeedback) {
        viewModelScope.launch {
            when (val value = eventFeedBackRepo.sendFeedBack(userEventFeedback)) {
                is Result.Success -> feedBackStateMediatorLiveData.postValue(value.data)
                is Result.Error -> eventFeedbackError.postValue(value.exception)
            }
        }
    }
}