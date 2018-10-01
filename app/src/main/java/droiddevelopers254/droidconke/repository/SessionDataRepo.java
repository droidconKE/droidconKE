package droiddevelopers254.droidconke.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import droiddevelopers254.droidconke.database.AppDatabase;
import droiddevelopers254.droidconke.database.dao.SessionsDao;
import droiddevelopers254.droidconke.datastates.SessionDataState;
import droiddevelopers254.droidconke.models.SessionsModel;
import droiddevelopers254.droidconke.utils.DroidCoin;

public class SessionDataRepo {
    public SessionDataRepo(){

    }

    public LiveData<SessionDataState> getSessionData(String dayNumber, int sessionId){
        final MutableLiveData<SessionDataState> sessionsModelMutableLiveData= new MutableLiveData<>();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection(dayNumber)
                .whereEqualTo("id",sessionId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                            SessionsModel sessionsModel=queryDocumentSnapshot.toObject(SessionsModel.class);
                            sessionsModel.setDocumentId(queryDocumentSnapshot.getId());
                            sessionsModelMutableLiveData.setValue(new SessionDataState(sessionsModel));
                        }
                    }else {
                        sessionsModelMutableLiveData.setValue(new SessionDataState("Error getting session details"));
                    }
                });

        return  sessionsModelMutableLiveData;
    }
}
