package droiddevelopers254.droidconke.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import droiddevelopers254.droidconke.datastates.UpdateTokenState;

public class UpdateTokenRepo {

    public UpdateTokenRepo(){

    }

    public LiveData<UpdateTokenState> updateToken(String userId,String refreshToken){
        final MutableLiveData<UpdateTokenState> updateTokenStateMutableLiveData=new MutableLiveData<>();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("users").document(userId)
                .update("refresh_token",refreshToken)
                .addOnSuccessListener(aVoid -> updateTokenStateMutableLiveData.setValue(new UpdateTokenState(true)))
                .addOnFailureListener(e -> updateTokenStateMutableLiveData.setValue(new UpdateTokenState(e.getMessage())));
    return updateTokenStateMutableLiveData;
    }
}
