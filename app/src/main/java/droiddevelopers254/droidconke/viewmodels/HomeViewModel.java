package droiddevelopers254.droidconke.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import droiddevelopers254.droidconke.datastates.FiltersState;
import droiddevelopers254.droidconke.datastates.UpdateTokenState;
import droiddevelopers254.droidconke.models.FiltersModel;
import droiddevelopers254.droidconke.repository.TopicFiltersRepo;
import droiddevelopers254.droidconke.repository.TypeFiltersRepo;
import droiddevelopers254.droidconke.repository.UpdateTokenRepo;

public class HomeViewModel extends ViewModel {
    private LiveData<List<FiltersModel>> filtersList;
    private TypeFiltersRepo typeFiltersRepo;
    private MediatorLiveData<FiltersState> filtersStateMediatorLiveData;
    private MediatorLiveData<FiltersState> stateMediatorLiveData;
    private MediatorLiveData<UpdateTokenState> updateTokenStateMediatorLiveData;
    private TopicFiltersRepo topicFiltersRepo;
    private UpdateTokenRepo updateTokenRepo;


    public HomeViewModel (){
        typeFiltersRepo =new TypeFiltersRepo();
        filtersStateMediatorLiveData= new MediatorLiveData<>();
        stateMediatorLiveData= new MediatorLiveData<>();
        updateTokenStateMediatorLiveData= new MediatorLiveData<>();
        topicFiltersRepo= new TopicFiltersRepo();
        updateTokenRepo= new UpdateTokenRepo();
    }

    public LiveData<FiltersState> getTypeFiltersResponse(){
        return filtersStateMediatorLiveData;
    }
    public LiveData<FiltersState> getTopicFiltersResponse(){
        return stateMediatorLiveData;
    }
    public LiveData<UpdateTokenState> getUpdateTokenResponse(){
        return updateTokenStateMediatorLiveData;
    }

//    public void saveFilter(FiltersModel filtersModel){
//        typeFiltersRepo.saveFilter(filtersModel);
//    }
//
//    public void checkFilterStatus(int filterId){
//        final LiveData<FiltersModel> filtersModelLiveData= typeFiltersRepo.checkFilterStatus(filterId);
//        filtersModelMediatorLiveData.addSource(filtersModelLiveData,
//                filtersModelMediatorLiveData->{
//            if (this.filtersModelMediatorLiveData.hasActiveObservers()){
//                this.filtersModelMediatorLiveData.removeSource(filtersModelLiveData);
//            }
//            this.filtersModelMediatorLiveData.setValue(filtersModelMediatorLiveData);
//                });
//    }

    public void getTypeFilters(){
        final  LiveData<FiltersState> filtersStateLiveData= typeFiltersRepo.getFilters();
        filtersStateMediatorLiveData.addSource(filtersStateLiveData,
                filtersStateMediatorLiveData ->{
            if (this.filtersStateMediatorLiveData.hasActiveObservers()){
                this.filtersStateMediatorLiveData.removeSource(filtersStateLiveData);
            }
            this.filtersStateMediatorLiveData.setValue(filtersStateMediatorLiveData);
                });
    }
    public void getTopicFilters(){
        final  LiveData<FiltersState> filtersStateLiveData= topicFiltersRepo.getFilters();
        stateMediatorLiveData.addSource(filtersStateLiveData,
                stateMediatorLiveData ->{
                    if (this.stateMediatorLiveData.hasActiveObservers()){
                        this.stateMediatorLiveData.removeSource(filtersStateLiveData);
                    }
                    this.stateMediatorLiveData.setValue(stateMediatorLiveData);
                });

    }
    public void updateToken(String userId,String refreshToken){
        final LiveData<UpdateTokenState> updateTokenStateLiveData=updateTokenRepo.updateToken(userId, refreshToken);
        updateTokenStateMediatorLiveData.addSource(updateTokenStateLiveData,
                updateTokenStateMediatorLiveData->{
            if (this.updateTokenStateMediatorLiveData.hasActiveObservers()){
                this.updateTokenStateMediatorLiveData.removeSource(updateTokenStateLiveData);
            }
            this.updateTokenStateMediatorLiveData.setValue(updateTokenStateMediatorLiveData);
                });
    }
}
