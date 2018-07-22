package droiddevelopers254.droidconke.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.LiveDataReactiveStreams;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import droiddevelopers254.droidconke.database.entities.SessionsEntity;
import droiddevelopers254.droidconke.datastates.SessionsState;
import droiddevelopers254.droidconke.repository.DayOneRepo;
import droiddevelopers254.droidconke.utils.network.Resource;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DayOneViewModel extends ViewModel {
    private LiveData<Resource<List<SessionsEntity>>> dayOneLiveData;
    private DayOneRepo dayOneRepo;

    public DayOneViewModel(){
        dayOneRepo= new DayOneRepo();
        dayOneLiveData= LiveDataReactiveStreams.fromPublisher(dayOneRepo.getDayOneSessions()
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread()));
    }

    public LiveData<Resource<List<SessionsEntity>>> getDayOneLiveData() {
        return dayOneLiveData;
    }
}
