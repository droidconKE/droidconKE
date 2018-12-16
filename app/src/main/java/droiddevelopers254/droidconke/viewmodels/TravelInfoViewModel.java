package droiddevelopers254.droidconke.viewmodels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import droiddevelopers254.droidconke.models.TravelInfoModel;
import droiddevelopers254.droidconke.repository.TravelInfoRepo;

public class TravelInfoViewModel extends AndroidViewModel{
    private MediatorLiveData<TravelInfoModel> infoViewModelMediatorLiveData;
    private TravelInfoRepo travelInfoRepo;


    public TravelInfoViewModel(Application application){
        super(application);
        infoViewModelMediatorLiveData= new MediatorLiveData<>();
        travelInfoRepo= new TravelInfoRepo(application);
    }

    public LiveData<TravelInfoModel> getTravelInfo(){
        return infoViewModelMediatorLiveData;
    }

    public void fetchRemoteConfigValues(){
        final LiveData<TravelInfoModel> travelInfoModelLiveData=travelInfoRepo.getTravelInfo();
        infoViewModelMediatorLiveData.addSource(travelInfoModelLiveData,
                infoViewModelMediatorLiveData->{
            if (this.infoViewModelMediatorLiveData.hasActiveObservers()){
                this.infoViewModelMediatorLiveData.removeSource(travelInfoModelLiveData);
            }
            this.infoViewModelMediatorLiveData.setValue(infoViewModelMediatorLiveData);
                });
    }
}
