package droiddevelopers254.droidconke.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import droiddevelopers254.droidconke.database.AppDatabase;
import droiddevelopers254.droidconke.database.dao.SessionsDao;
import droiddevelopers254.droidconke.datastates.SessionsState;
import droiddevelopers254.droidconke.models.SessionsModel;
import droiddevelopers254.droidconke.utils.DroidCoin;

public class DayTwoRepo {
    private SessionsDao sessionsDao;
    private Executor executor;

    public DayTwoRepo(){
        sessionsDao = AppDatabase.getDatabase(DroidCoin.context).sessionsDao();
        executor = Executors.newSingleThreadExecutor();

    }
    public LiveData<SessionsState> getDayTwoSessions(){
        final MutableLiveData<SessionsState> sessionsStateMutableLiveData= new MutableLiveData<>();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("day_two")
                .orderBy("id", Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()){
                        List<SessionsModel> sessionsModelList=queryDocumentSnapshots.toObjects(SessionsModel.class);
                        sessionsStateMutableLiveData.setValue(new SessionsState(sessionsModelList));
                        executor.execute(()-> sessionsDao.saveSession(sessionsModelList));
                    }

                })
                .addOnFailureListener(error -> sessionsStateMutableLiveData.setValue(new SessionsState(error.getMessage())));

        return sessionsStateMutableLiveData;
    }
}
