package droiddevelopers254.droidconke.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.google.firebase.firestore.FirebaseFirestore;
import java.util.List;

import droiddevelopers254.droidconke.datastates.SpeakersState;
import droiddevelopers254.droidconke.models.SpeakersModel;

public class SpeakersRepo {

    public SpeakersRepo(){

    }

    public LiveData<SpeakersState> getSpeakersInfo(int speakerId){
        final MutableLiveData<SpeakersState> speakersStateMutableLiveData= new MutableLiveData<>();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("speakers")
                .whereEqualTo("id",speakerId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<SpeakersModel> speakersModel= queryDocumentSnapshots.toObjects(SpeakersModel.class);
                    speakersStateMutableLiveData.setValue(new SpeakersState(speakersModel));
                })
                .addOnFailureListener(e -> speakersStateMutableLiveData.setValue(new SpeakersState(e.getMessage())));

        return speakersStateMutableLiveData;
    }
}
