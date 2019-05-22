package droiddevelopers254.droidconke.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import droiddevelopers254.droidconke.datastates.EventTypeState
import droiddevelopers254.droidconke.datastates.Result
import droiddevelopers254.droidconke.models.EventTypeModel

class EventTypeRepo {

    suspend fun getSessionData():Result<List<EventTypeModel>>{
        return try {
            val firebaseFirestore = Firebase.firestore
            val snapshots =  firebaseFirestore.collection("event_types")
                    .orderBy("id", Query.Direction.ASCENDING)
                    .get()
                    .await()
            Result.Success(snapshots.toObjects())
        }catch (e : FirebaseFirestoreException){
            Result.Error(e.message)
        }
    }
}
