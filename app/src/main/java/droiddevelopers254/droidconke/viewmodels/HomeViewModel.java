package droiddevelopers254.droidconke.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import droiddevelopers254.droidconke.models.FiltersModel;
import droiddevelopers254.droidconke.repository.FiltersRepo;

public class HomeViewModel extends ViewModel {
    private LiveData<List<FiltersModel>> filtersList;
    private FiltersRepo filtersRepo;
    private MediatorLiveData<FiltersModel> filtersModelMediatorLiveData;



    public HomeViewModel (){
//        filtersRepo=new FiltersRepo();
//        filtersList=filtersRepo.getFilters();
//        filtersModelMediatorLiveData= new MediatorLiveData<>();
    }

    public LiveData<List<FiltersModel>> getFiltersList(){
        return filtersList;
    }
    public LiveData<FiltersModel> getFilterStatus(){
        return filtersModelMediatorLiveData;
    }

//    public void saveFilter(FiltersModel filtersModel){
//        filtersRepo.saveFilter(filtersModel);
//    }
//
//    public void checkFilterStatus(int filterId){
//        final LiveData<FiltersModel> filtersModelLiveData= filtersRepo.checkFilterStatus(filterId);
//        filtersModelMediatorLiveData.addSource(filtersModelLiveData,
//                filtersModelMediatorLiveData->{
//            if (this.filtersModelMediatorLiveData.hasActiveObservers()){
//                this.filtersModelMediatorLiveData.removeSource(filtersModelLiveData);
//            }
//            this.filtersModelMediatorLiveData.setValue(filtersModelMediatorLiveData);
//                });
//    }
}
