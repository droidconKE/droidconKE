package droiddevelopers254.droidconke.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

import droiddevelopers254.droidconke.datastates.SessionsState;
import droiddevelopers254.droidconke.models.SessionsModel;

public class DayTwoRepo {

    public DayTwoRepo(){

    }
    public LiveData<SessionsState> getSessionData(){
        final MutableLiveData<SessionsState> sessionsModelMutableLiveData= new MutableLiveData<>();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("day_two")
                .orderBy("id", Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()){
                        List<SessionsModel> sessionsModelList=queryDocumentSnapshots.toObjects(SessionsModel.class);
                        sessionsModelMutableLiveData.setValue(new SessionsState(sessionsModelList));
                    }

                })
                .addOnFailureListener(e -> sessionsModelMutableLiveData.setValue(new SessionsState(e.getMessage())));
        return  sessionsModelMutableLiveData;
    }
}
