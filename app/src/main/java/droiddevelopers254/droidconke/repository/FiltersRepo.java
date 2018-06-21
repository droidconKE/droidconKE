package droiddevelopers254.droidconke.repository;

import android.arch.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import droiddevelopers254.droidconke.database.AppDatabase;
import droiddevelopers254.droidconke.database.dao.FiltersDao;
import droiddevelopers254.droidconke.models.FiltersModel;
import droiddevelopers254.droidconke.utils.DroidCoin;

public class FiltersRepo {
    private FiltersDao filtersDao;
    private Executor executor;

    public FiltersRepo(){
//        this.filtersDao= AppDatabase.getDatabase(DroidCoin.context).filtersDao();
//        this.executor= Executors.newSingleThreadExecutor();
    }

//    public LiveData<List<FiltersModel>> getFilters(){
//        return filtersDao.getFiltersList();
//    }
//
//    public void saveFilter(FiltersModel filtersModel) {
//        executor.execute(()-> filtersDao.saveFilter(filtersModel));
//    }
//
//    public LiveData<FiltersModel> checkFilterStatus(int filterId){
//        return filtersDao.checkFilterStatus(filterId);
//    }
}
