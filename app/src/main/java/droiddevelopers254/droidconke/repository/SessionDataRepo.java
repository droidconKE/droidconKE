package droiddevelopers254.droidconke.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import droiddevelopers254.droidconke.datastates.EventTypeState;
import droiddevelopers254.droidconke.datastates.SessionDataState;
import droiddevelopers254.droidconke.models.EventTypeModel;
import droiddevelopers254.droidconke.models.SessionsModel;
import droiddevelopers254.droidconke.views.activities.SessionViewActivity;

public class SessionDataRepo {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public SessionDataRepo(){

    }

    public LiveData<SessionDataState> getSessionData(int sessionId){
        final MutableLiveData<SessionDataState> sessionsModelMutableLiveData= new MutableLiveData<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseReference.child("day_one").child(String.valueOf(sessionId)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    SessionsModel sessionsModel = dataSnapshot.getValue(SessionsModel.class);
                   sessionsModelMutableLiveData.setValue(new SessionDataState(sessionsModel));

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                sessionsModelMutableLiveData.setValue(new SessionDataState(error));
            }
        });

        return  sessionsModelMutableLiveData;
    }
}
