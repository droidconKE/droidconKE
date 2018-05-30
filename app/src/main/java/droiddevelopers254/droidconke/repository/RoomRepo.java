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
import droiddevelopers254.droidconke.datastates.RoomState;
import droiddevelopers254.droidconke.models.EventTypeModel;
import droiddevelopers254.droidconke.models.RoomModel;

public class RoomRepo {
    public RoomRepo(){

    }

    public LiveData<RoomState> getRoomDetails(String roomId){
        final MutableLiveData<RoomState> roomStateLiveData= new MutableLiveData<>();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("rooms").child(roomId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(databaseReference!=null){
                    for(DataSnapshot data :dataSnapshot.getChildren()) {
                        RoomModel roomModel = data.getValue(RoomModel.class);
                        roomStateLiveData.setValue(new RoomState(roomModel));
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                roomStateLiveData.setValue(new RoomState(databaseError));
            }
        });

        return roomStateLiveData;
    }
}
