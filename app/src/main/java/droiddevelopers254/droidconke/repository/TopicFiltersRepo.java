package droiddevelopers254.droidconke.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import droiddevelopers254.droidconke.database.dao.FiltersDao;
import droiddevelopers254.droidconke.datastates.FiltersState;
import droiddevelopers254.droidconke.models.FiltersModel;

public class TopicFiltersRepo {
    private FiltersDao filtersDao;
    private Executor executor;

    public TopicFiltersRepo(){
//        this.filtersDao= AppDatabase.getDatabase(DroidCoin.context).filtersDao();
//        this.executor= Executors.newSingleThreadExecutor();
    }

    public LiveData<FiltersState> getFilters(){
        final MutableLiveData<FiltersState> filtersStateMutableLiveData=new MutableLiveData<>();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("session_topics")
                .orderBy("id", Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<FiltersModel> filtersModelList= queryDocumentSnapshots.toObjects(FiltersModel.class);
                    filtersStateMutableLiveData.setValue(new FiltersState(filtersModelList));

                })
                .addOnFailureListener(e -> filtersStateMutableLiveData.setValue(new FiltersState(e.getMessage())));
        return filtersStateMutableLiveData;
    }

//    public LiveData<List<FiltersModel>> getTypeFilters(){
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
