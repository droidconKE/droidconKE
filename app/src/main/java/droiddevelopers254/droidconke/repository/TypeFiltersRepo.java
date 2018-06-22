package droiddevelopers254.droidconke.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import droiddevelopers254.droidconke.database.AppDatabase;
import droiddevelopers254.droidconke.database.dao.FiltersDao;
import droiddevelopers254.droidconke.datastates.FiltersState;
import droiddevelopers254.droidconke.models.FiltersModel;
import droiddevelopers254.droidconke.utils.DroidCoin;

public class TypeFiltersRepo {
    private FiltersDao filtersDao;
    private Executor executor;
    List<FiltersModel> filtersModelList= new ArrayList<>();

    public TypeFiltersRepo(){
//        this.filtersDao= AppDatabase.getDatabase(DroidCoin.context).filtersDao();
//        this.executor= Executors.newSingleThreadExecutor();
    }

    public LiveData<FiltersState> getFilters(){
        final MutableLiveData<FiltersState> filtersStateMutableLiveData=new MutableLiveData<>();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("session_types");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (databaseReference != null){
                    for (DataSnapshot data : dataSnapshot.getChildren()){
                        FiltersModel filtersModel= data.getValue(FiltersModel.class);
                        filtersModelList.add(filtersModel);
                        filtersStateMutableLiveData.setValue(new FiltersState(filtersModelList));
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                filtersStateMutableLiveData.setValue(new FiltersState(databaseError));

            }
        });
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
