package droiddevelopers254.droidconke.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import droiddevelopers254.droidconke.database.AppDatabase
import droiddevelopers254.droidconke.database.dao.SessionsDao
import droiddevelopers254.droidconke.datastates.SessionsState
import droiddevelopers254.droidconke.models.SessionsModel
import droiddevelopers254.droidconke.utils.DroidCon
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class DayOneRepo {
    private val sessionsDao: SessionsDao = AppDatabase.getDatabase(DroidCon.context)!!.sessionsDao()
    private val executor: Executor =  Executors.newSingleThreadExecutor()

    val dayOneSessions: LiveData<SessionsState>
        get() {
            val sessionsStateMutableLiveData = MutableLiveData<SessionsState>()
            val firebaseFirestore = FirebaseFirestore.getInstance()
            firebaseFirestore.collection("day_one")
                    .orderBy("id", Query.Direction.ASCENDING)
                    .get()
                    .addOnSuccessListener {
                        when {
                            !it.isEmpty -> {
                                val sessionsModelList = it.toObjects(SessionsModel::class.java)
                                sessionsStateMutableLiveData.value = SessionsState(sessionsModelList)
                                executor.execute { sessionsDao.saveSession(sessionsModelList) }
                            }
                        }

                    }
                    .addOnFailureListener {
                        sessionsStateMutableLiveData.value = SessionsState(null,it.message)}

            return sessionsStateMutableLiveData
        }


}
