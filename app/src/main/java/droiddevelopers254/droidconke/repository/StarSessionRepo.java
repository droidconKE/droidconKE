package droiddevelopers254.droidconke.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import droiddevelopers254.droidconke.R;
import droiddevelopers254.droidconke.datastates.StarSessionState;
import droiddevelopers254.droidconke.models.StarredSessionModel;

public class StarSessionRepo {
    FirebaseFirestore firebaseFirestore;
    final MutableLiveData<StarSessionState> starSessionStateMutableLiveData;

    public StarSessionRepo(){
        firebaseFirestore = FirebaseFirestore.getInstance();
        starSessionStateMutableLiveData= new MutableLiveData<>();


    }
    public LiveData<StarSessionState> starSession(StarredSessionModel starredSessionModel,String userId){
        firebaseFirestore.collection("starred_sessions").document(starredSessionModel.getDocumentId())
                .set(starredSessionModel)
                .addOnSuccessListener(aVoid -> {
                    //add session to user starred sessions
                    firebaseFirestore.collection("users").document(userId).collection("starred").document(starredSessionModel.getDocumentId())
                            .set(starredSessionModel)
                            .addOnSuccessListener(documentSnapshot -> starSessionStateMutableLiveData.setValue(new StarSessionState(true)))
                            .addOnFailureListener(e -> starSessionStateMutableLiveData.setValue(new StarSessionState(e.getMessage())));
                })
                .addOnFailureListener(e -> starSessionStateMutableLiveData.setValue(new StarSessionState(e.getMessage())));
    return starSessionStateMutableLiveData;
    }

    public LiveData<StarSessionState> unStarSession(String sessionId,String userId,boolean starred){
        firebaseFirestore.collection("starred_sessions").document(sessionId)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    //change starred field in users starred sessions to false
                    firebaseFirestore.collection("users").document(userId).collection("starred").document(sessionId)
                            .update("starred",starred)
                            .addOnSuccessListener(aVoid1 -> starSessionStateMutableLiveData.setValue(new StarSessionState(false)))
                            .addOnFailureListener(e -> starSessionStateMutableLiveData.setValue(new StarSessionState(e.getMessage())));
                })
                .addOnFailureListener(e -> starSessionStateMutableLiveData.setValue(new StarSessionState(e.getMessage())));
        return starSessionStateMutableLiveData;
    }

    public LiveData<StarSessionState> checkStarStatus(String sessionId,String userId){
        firebaseFirestore.collection("users").document(userId).collection("starred").document(sessionId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if (documentSnapshot.exists()) {
                            //session already starred, un star session
                            StarredSessionModel starredSessionModel= documentSnapshot.toObject(StarredSessionModel.class);
                            starSessionStateMutableLiveData.setValue(new StarSessionState(starredSessionModel));
                        }else {
                            starSessionStateMutableLiveData.setValue(new StarSessionState(false));
                        }
                    }
                });
    return starSessionStateMutableLiveData;
    }
}
