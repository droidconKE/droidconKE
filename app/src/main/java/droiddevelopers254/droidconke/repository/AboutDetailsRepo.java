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

import droiddevelopers254.droidconke.datastates.AboutDetailsState;
import droiddevelopers254.droidconke.models.AboutDetailsModel;

public class AboutDetailsRepo {
    List<AboutDetailsModel> aboutDetailsStateList= new ArrayList<>();

    public AboutDetailsRepo(){

    }

    public LiveData<AboutDetailsState> getAboutDetails(String aboutType){
        final MutableLiveData<AboutDetailsState> detailsStateMutableLiveData= new MutableLiveData<>();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(aboutType);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(databaseReference!=null){
                    for(DataSnapshot data :dataSnapshot.getChildren()) {
                        AboutDetailsModel aboutDetailsModel = data.getValue(AboutDetailsModel.class);
                        aboutDetailsStateList.add(aboutDetailsModel);
                        detailsStateMutableLiveData.setValue(new AboutDetailsState(aboutDetailsStateList));
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                detailsStateMutableLiveData.setValue(new AboutDetailsState(databaseError));
            }
        });
        return detailsStateMutableLiveData;
    }
}
