package droiddevelopers254.droidconke.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

import droiddevelopers254.droidconke.database.AppDatabase;
import droiddevelopers254.droidconke.database.dao.SessionsDao;
import droiddevelopers254.droidconke.database.entities.SessionsEntity;
import droiddevelopers254.droidconke.datastates.EventTypeState;
import droiddevelopers254.droidconke.datastates.SessionDataState;
import droiddevelopers254.droidconke.models.EventTypeModel;
import droiddevelopers254.droidconke.models.SessionsModel;
import droiddevelopers254.droidconke.utils.DroidCoin;
import droiddevelopers254.droidconke.views.activities.SessionViewActivity;
import io.reactivex.Flowable;

public class SessionDataRepo {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private SessionsDao sessionsDao;

    public SessionDataRepo(){
        sessionsDao= AppDatabase.getDatabase(DroidCoin.context).sessionsDao();

    }

    public Flowable<SessionsEntity> getSessionDetails(String dayNumber, int sessionId){
        return sessionsDao.getSessionDetails(dayNumber, sessionId);
    }

//    public LiveData<SessionDataState> getSessionData(String dayNumber,int sessionId){
//        final MutableLiveData<SessionDataState> sessionsModelMutableLiveData= new MutableLiveData<>();
//        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
//        firebaseFirestore.collection(dayNumber)
//                .whereEqualTo("id",sessionId)
//                .get()
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()){
//                        for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
//                            SessionsModel sessionsModel=queryDocumentSnapshot.toObject(SessionsModel.class);
//                            sessionsModel.setDocumentId(queryDocumentSnapshot.getId());
//                            sessionsModelMutableLiveData.setValue(new SessionDataState(sessionsModel));
//                        }
//                    }else {
//                        sessionsModelMutableLiveData.setValue(new SessionDataState("Error getting session details"));
//                    }
//                });
//
//        return  sessionsModelMutableLiveData;
//    }
}
