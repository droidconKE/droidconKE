package droiddevelopers254.droidconke.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;

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

import droiddevelopers254.droidconke.datastates.AgendaState;
import droiddevelopers254.droidconke.models.AgendaModel;

public class AgendaRepo {
    public AgendaRepo(){

    }

    public LiveData<AgendaState> getAgendaData(){
        final MutableLiveData<AgendaState> sessionsModelMutableLiveData= new MutableLiveData<>();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("agenda")
                .orderBy("id", Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()){
                        List<AgendaModel> agendaModelList= queryDocumentSnapshots.toObjects(AgendaModel.class);
                        sessionsModelMutableLiveData.setValue(new AgendaState(agendaModelList));
                    }

                })
                .addOnFailureListener(e -> sessionsModelMutableLiveData.setValue(new AgendaState(e.getMessage())));

        return  sessionsModelMutableLiveData;
    }
}
