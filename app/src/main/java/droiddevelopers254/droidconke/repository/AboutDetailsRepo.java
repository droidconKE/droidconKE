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

import droiddevelopers254.droidconke.datastates.AboutDetailsState;
import droiddevelopers254.droidconke.models.AboutDetailsModel;

public class AboutDetailsRepo {

    public AboutDetailsRepo(){

    }

    public LiveData<AboutDetailsState> getAboutDetails(String aboutType){
        final MutableLiveData<AboutDetailsState> detailsStateMutableLiveData= new MutableLiveData<>();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection(aboutType)
                .orderBy("id", Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<AboutDetailsModel> aboutDetailsModelList=queryDocumentSnapshots.toObjects(AboutDetailsModel.class);
                    detailsStateMutableLiveData.setValue(new AboutDetailsState(aboutDetailsModelList));
                })
                .addOnFailureListener(e -> {
                    detailsStateMutableLiveData.setValue(new AboutDetailsState(e.getMessage()));
                });
        return detailsStateMutableLiveData;
    }
}
