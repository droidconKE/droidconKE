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

import droiddevelopers254.droidconke.datastates.AgendaState;
import droiddevelopers254.droidconke.models.AgendaModel;

public class AgendaRepo {

    public AgendaRepo(){

    }

    public LiveData<AgendaState> getAgendaData(){
        final MutableLiveData<AgendaState> sessionsModelMutableLiveData= new MutableLiveData<>();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("agenda");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(databaseReference!=null){
                    for(DataSnapshot data :dataSnapshot.getChildren()) {
                        AgendaModel agendaModel = data.getValue(AgendaModel.class);
                        List<AgendaModel> agendaList= new ArrayList<>();
                        agendaList.add(agendaModel);
                        sessionsModelMutableLiveData.setValue(new AgendaState(agendaList));
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                sessionsModelMutableLiveData.setValue(new AgendaState(databaseError));
            }
        });

        return  sessionsModelMutableLiveData;
    }
}
