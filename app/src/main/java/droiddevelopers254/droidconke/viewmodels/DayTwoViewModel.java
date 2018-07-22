package droiddevelopers254.droidconke.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.LiveDataReactiveStreams;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import droiddevelopers254.droidconke.database.entities.SessionsEntity;
import droiddevelopers254.droidconke.datastates.SessionsState;
import droiddevelopers254.droidconke.repository.DayTwoRepo;
import droiddevelopers254.droidconke.utils.network.Resource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DayTwoViewModel extends ViewModel{
    private DayTwoRepo dayTwoRepo;
    private LiveData<Resource<List<SessionsEntity>>> sessionsLiveData;

    public DayTwoViewModel(){
        dayTwoRepo= new DayTwoRepo();
        sessionsLiveData = LiveDataReactiveStreams.fromPublisher(dayTwoRepo.getDayTwoSessions()
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread()));
    }

    public LiveData<Resource<List<SessionsEntity>>> getSessionsLiveData() {
        return sessionsLiveData;
    }
}
