package droiddevelopers254.droidconke.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import droiddevelopers254.droidconke.datastates.FeedBackState;
import droiddevelopers254.droidconke.models.SessionsUserFeedback;
import droiddevelopers254.droidconke.models.UserEventFeedback;
import droiddevelopers254.droidconke.repository.EventFeedBackRepo;
import droiddevelopers254.droidconke.repository.SessionFeedbackRepo;

public class FeedBackViewModel extends ViewModel {
    private MediatorLiveData<FeedBackState> feedBackStateMediatorLiveData;
    private EventFeedBackRepo eventFeedBackRepo;


    public FeedBackViewModel(){
        feedBackStateMediatorLiveData = new MediatorLiveData<>();
        eventFeedBackRepo = new EventFeedBackRepo();
    }


    public LiveData<FeedBackState> getEventFeedBackResponse(){
        return feedBackStateMediatorLiveData;
    }

    public void sendEventFeedBack(UserEventFeedback userEventFeedback){
        final LiveData<FeedBackState> feedBackStateLiveData = eventFeedBackRepo.sendFeedBack(userEventFeedback);
        feedBackStateMediatorLiveData.addSource(feedBackStateLiveData,
                feedBackStateMediatorLiveData ->{
                    if (this.feedBackStateMediatorLiveData.hasActiveObservers()){
                        this.feedBackStateMediatorLiveData.removeSource(feedBackStateLiveData);
                    }
                    this.feedBackStateMediatorLiveData.setValue(feedBackStateMediatorLiveData);
                });
    }
}
