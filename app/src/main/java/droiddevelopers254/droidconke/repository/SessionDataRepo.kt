package droiddevelopers254.droidconke.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore
import droiddevelopers254.droidconke.database.AppDatabase
import droiddevelopers254.droidconke.database.dao.SessionsDao
import droiddevelopers254.droidconke.datastates.SessionDataState
import droiddevelopers254.droidconke.models.SessionsModel
import droiddevelopers254.droidconke.utils.DroidCoin

class SessionDataRepo {
    internal var databaseReference: DatabaseReference? = null
    private val sessionsDao: SessionsDao = AppDatabase.getDatabase(DroidCoin.context)!!.sessionsDao()

    fun getSessionData(dayNumber: String, sessionId: Int): LiveData<SessionDataState> {
        val sessionsModelMutableLiveData = MutableLiveData<SessionDataState>()
        val firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseFirestore.collection(dayNumber)
                .whereEqualTo("id", sessionId)
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        for (queryDocumentSnapshot in it.result) {
                            val sessionsModel = queryDocumentSnapshot.toObject(SessionsModel::class.java)
                            val newSessionsModel= sessionsModel.copy(documentId = queryDocumentSnapshot.id)
                            sessionsModelMutableLiveData.value = SessionDataState(newSessionsModel,null)
                        }
                    } else {
                        sessionsModelMutableLiveData.value = SessionDataState(null,"Error getting session details")
                    }
                }

        return sessionsModelMutableLiveData
    }
}
