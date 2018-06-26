package droiddevelopers254.droidconke.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("speakers")
                .whereEqualTo("id",roomId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                            RoomModel roomModel= queryDocumentSnapshot.toObject(RoomModel.class);
                            roomStateLiveData.setValue(new RoomState(roomModel));

                        }
                    }else {
                        roomStateLiveData.setValue(new RoomState("Error getting room details"));
                    }

                });
        return roomStateLiveData;
    }
}
