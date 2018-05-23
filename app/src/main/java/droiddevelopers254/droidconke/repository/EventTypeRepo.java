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
import droiddevelopers254.droidconke.models.EventTypeModel;
import droiddevelopers254.droidconke.models.SessionsModel;

public class EventTypeRepo {
    List<EventTypeModel> eventTypeList= new ArrayList<>();

    public EventTypeRepo(){

    }
    public LiveData<EventTypeState> getSessionData(){
        final MutableLiveData<EventTypeState> sessionsModelMutableLiveData= new MutableLiveData<>();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("event_types");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(databaseReference!=null){
                    for(DataSnapshot data :dataSnapshot.getChildren()) {
                        EventTypeModel eventTypeModel = data.getValue(EventTypeModel.class);
                        eventTypeList.add(eventTypeModel);
                        sessionsModelMutableLiveData.setValue(new EventTypeState(eventTypeList));
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                sessionsModelMutableLiveData.setValue(new EventTypeState(databaseError));
            }
        });

        return  sessionsModelMutableLiveData;
    }
}
