package droiddevelopers254.droidconke.repository

import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import droiddevelopers254.droidconke.datastates.Result
import droiddevelopers254.droidconke.models.SpeakersModel
import droiddevelopers254.droidconke.utils.await

class SpeakersRepo {

    suspend fun getSpeakersInfo(speakerId: Int): Result<List<SpeakersModel>> {
        return try {
            val firebaseFirestore = Firebase.firestore
            val snapshot = firebaseFirestore.collection("speakers")
                    .whereEqualTo("id", speakerId)
                    .get()
                    .await()
            Result.Success(snapshot.toObjects<SpeakersModel>())
        } catch (e: FirebaseFirestoreException) {
            Result.Error(e.message)
        }
    }
}
