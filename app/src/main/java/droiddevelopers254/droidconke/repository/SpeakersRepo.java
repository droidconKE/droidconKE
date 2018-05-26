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

import droiddevelopers254.droidconke.datastates.SpeakersState;
import droiddevelopers254.droidconke.models.SpeakersModel;

public class SpeakersRepo {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    List<SpeakersModel> speakersModelList = new ArrayList<>();

    public SpeakersRepo(){

    }

    public LiveData<SpeakersState> getSpeakersInfo(String speakerId){
        final MutableLiveData<SpeakersState> speakersStateMutableLiveData= new MutableLiveData<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseReference.child("speakers").child(speakerId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    for(DataSnapshot data :dataSnapshot.getChildren()) {
                        SpeakersModel speakersModel = data.getValue(SpeakersModel.class);
                        speakersModelList.add(speakersModel);
                        speakersStateMutableLiveData.setValue(new SpeakersState(speakersModelList));
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                speakersStateMutableLiveData.setValue(new SpeakersState(error));
            }
        });

        return speakersStateMutableLiveData;
    }
}
