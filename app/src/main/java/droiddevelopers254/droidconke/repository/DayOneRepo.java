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

import droiddevelopers254.droidconke.datastates.EventTypeState;
import droiddevelopers254.droidconke.datastates.SessionsState;
import droiddevelopers254.droidconke.models.EventTypeModel;
import droiddevelopers254.droidconke.models.SessionsModel;

public class DayOneRepo {
    List<SessionsModel> sessionList= new ArrayList<>();

    public DayOneRepo(){

    }
    public LiveData<SessionsState> getSessionData(){
        final MutableLiveData<SessionsState> sessionsModelMutableLiveData= new MutableLiveData<>();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("day_one");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(databaseReference!=null){
                    for(DataSnapshot data :dataSnapshot.getChildren()) {
                        SessionsModel sessionsModel = data.getValue(SessionsModel.class);
                        sessionList.add(sessionsModel);
                        sessionsModelMutableLiveData.setValue(new SessionsState(sessionList));
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                sessionsModelMutableLiveData.setValue(new SessionsState(databaseError));
            }
        });

        return  sessionsModelMutableLiveData;
    }
}
