package droiddevelopers254.droidconke.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import droiddevelopers254.droidconke.datastates.StarSessionState;
import droiddevelopers254.droidconke.models.StarredSessionModel;

public class StarSessionRepo {
    FirebaseFirestore firebaseFirestore;
    final MutableLiveData<StarSessionState> starSessionStateMutableLiveData;

    public StarSessionRepo(){
        firebaseFirestore = FirebaseFirestore.getInstance();
        starSessionStateMutableLiveData= new MutableLiveData<>();


    }
    public LiveData<StarSessionState> starSession(StarredSessionModel starredSessionModel){
        firebaseFirestore.collection("starred_sessions").document(starredSessionModel.getDocumentId())
                .set(starredSessionModel)
                .addOnSuccessListener(aVoid -> starSessionStateMutableLiveData.setValue(new StarSessionState("Session Starred")))
                .addOnFailureListener(e -> starSessionStateMutableLiveData.setValue(new StarSessionState(e.getMessage())));
    return starSessionStateMutableLiveData;
    }

    public LiveData<StarSessionState> unStarSession(String sessionId){
        firebaseFirestore.collection("starred_sessions").document(sessionId)
                .delete()
                .addOnSuccessListener(aVoid -> starSessionStateMutableLiveData.setValue(new StarSessionState("Session unstarred")))
                .addOnFailureListener(e -> starSessionStateMutableLiveData.setValue(new StarSessionState(e.getMessage())));
        return starSessionStateMutableLiveData;
    }

    public LiveData<StarSessionState> checkStarStatus(String sessionId){
        firebaseFirestore.collection("starred_sessions").document(sessionId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if (documentSnapshot.exists()) {
                            //session already starred, un star session
                            starSessionStateMutableLiveData.setValue(new StarSessionState(true));
                        }else {
                            starSessionStateMutableLiveData.setValue(new StarSessionState(false));
                        }
                    }
                });
    return starSessionStateMutableLiveData;
    }
    public LiveData<StarSessionState> starUserSession(StarredSessionModel starredSessionModel,String userId){
        firebaseFirestore.collection("users").document(userId).collection("starred").document(starredSessionModel.getDocumentId())
                .set(starredSessionModel)
                .addOnSuccessListener(aVoid -> starSessionStateMutableLiveData.setValue(new StarSessionState("Starred")))
                .addOnFailureListener(e -> starSessionStateMutableLiveData.setValue(new StarSessionState(e.getMessage())));
    return starSessionStateMutableLiveData;
    }

    public LiveData<StarSessionState> unStarUserSession(String sessionId,String userId,boolean starred){
        firebaseFirestore.collection("users").document(userId).collection("starred").document(sessionId)
                .update("starred",starred)
                .addOnSuccessListener(aVoid -> starSessionStateMutableLiveData.setValue(new StarSessionState("Unstarred")))
                .addOnFailureListener(e -> starSessionStateMutableLiveData.setValue(new StarSessionState(e.getMessage())));
    return starSessionStateMutableLiveData;
    }
}
