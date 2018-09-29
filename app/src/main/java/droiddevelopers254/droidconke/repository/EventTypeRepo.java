package droiddevelopers254.droidconke.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import droiddevelopers254.droidconke.datastates.EventTypeState;
import droiddevelopers254.droidconke.models.EventTypeModel;
import droiddevelopers254.droidconke.models.SessionsModel;

public class EventTypeRepo {
    List<EventTypeModel> eventTypeList= new ArrayList<>();

    public EventTypeRepo(){

    }
    public LiveData<EventTypeState> getSessionData(){
        final MutableLiveData<EventTypeState> sessionsModelMutableLiveData= new MutableLiveData<>();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("event_types")
                .orderBy("id", Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<EventTypeModel> eventTypeModelList=queryDocumentSnapshots.toObjects(EventTypeModel.class);
                    sessionsModelMutableLiveData.setValue(new EventTypeState(eventTypeModelList));

                })
                .addOnFailureListener(e -> sessionsModelMutableLiveData.setValue(new EventTypeState(e.getMessage())));
        return  sessionsModelMutableLiveData;
    }
}
