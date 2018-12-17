package droiddevelopers254.droidconke.viewmodels

import droiddevelopers254.droidconke.datastates.FeedBackState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import droiddevelopers254.droidconke.models.UserEventFeedback
import droiddevelopers254.droidconke.repository.EventFeedbackRepo


class FeedBackViewModel : ViewModel(){
    private val feedBackStateMediatorLiveData = MediatorLiveData<FeedBackState>()
    private val eventFeedBackRepo = EventFeedbackRepo()

    fun getEventFeedBackResponse(): LiveData<FeedBackState> {
        return feedBackStateMediatorLiveData
    }

    fun sendEventFeedBack(userEventFeedback: UserEventFeedback) {
        val feedBackStateLiveData = eventFeedBackRepo.sendFeedBack(userEventFeedback)
        feedBackStateMediatorLiveData.addSource(feedBackStateLiveData
        ) { feedBackStateMediatorLiveData ->
            when {
                this.feedBackStateMediatorLiveData.hasActiveObservers() -> this.feedBackStateMediatorLiveData.removeSource(feedBackStateLiveData)
            }
            this.feedBackStateMediatorLiveData.setValue(feedBackStateMediatorLiveData)
        }
    }
}