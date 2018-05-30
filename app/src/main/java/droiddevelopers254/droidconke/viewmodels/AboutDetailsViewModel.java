package droiddevelopers254.droidconke.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;

import droiddevelopers254.droidconke.datastates.AboutDetailsState;
import droiddevelopers254.droidconke.repository.AboutDetailsRepo;

public class AboutDetailsViewModel extends ViewModel {
    private MediatorLiveData<AboutDetailsState> detailsStateMediatorLiveData;
    private AboutDetailsRepo aboutDetailsRepo;


    public AboutDetailsViewModel(){
        detailsStateMediatorLiveData= new MediatorLiveData<>();
        aboutDetailsRepo= new AboutDetailsRepo();
    }

    public LiveData<AboutDetailsState> getAboutDetails(){
        return detailsStateMediatorLiveData;
    }
    public void fetchAboutDetails(String aboutType){
        final LiveData<AboutDetailsState> aboutDetailsStateLiveData=aboutDetailsRepo.getAboutDetails(aboutType);
        detailsStateMediatorLiveData.addSource(aboutDetailsStateLiveData,
                detailsStateMediatorLiveData ->{
            if (this.detailsStateMediatorLiveData.hasActiveObservers()){
                this.detailsStateMediatorLiveData.removeSource(aboutDetailsStateLiveData);
            }
            this.detailsStateMediatorLiveData.setValue(detailsStateMediatorLiveData);
                });
    }
}
