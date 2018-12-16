package droiddevelopers254.droidconke.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import droiddevelopers254.droidconke.datastates.FiltersState;
import droiddevelopers254.droidconke.models.FiltersModel;

public class TypeFiltersRepo {
    List<FiltersModel> filtersModelList= new ArrayList<>();

    public TypeFiltersRepo(){
    }

    public LiveData<FiltersState> getFilters(){
        final MutableLiveData<FiltersState> filtersStateMutableLiveData=new MutableLiveData<>();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("session_types")
                .orderBy("id", Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<FiltersModel> filtersModelList=queryDocumentSnapshots.toObjects(FiltersModel.class);
                    filtersStateMutableLiveData.setValue(new FiltersState(filtersModelList));

                })
                .addOnFailureListener(e -> filtersStateMutableLiveData.setValue(new FiltersState(e.getMessage())));
        return filtersStateMutableLiveData;
    }

}
