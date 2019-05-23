package droiddevelopers254.droidconke.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import droiddevelopers254.droidconke.datastates.Result
import droiddevelopers254.droidconke.models.EventTypeModel
import droiddevelopers254.droidconke.utils.await

class EventTypeRepo(val firestore: FirebaseFirestore) {

    suspend fun getSessionData(): Result<List<EventTypeModel>> {
        return try {
            val snapshots = firestore.collection("event_types")
                    .orderBy("id", Query.Direction.ASCENDING)
                    .get()
                    .await()
            Result.Success(snapshots.toObjects())
        } catch (e: FirebaseFirestoreException) {
            Result.Error(e.message)
        }
    }
}
