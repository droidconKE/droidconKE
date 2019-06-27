package droiddevelopers254.droidconke.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.toObjects
import droiddevelopers254.droidconke.datastates.Result
import droiddevelopers254.droidconke.models.SpeakersModel
import droiddevelopers254.droidconke.utils.await

class SpeakersRepo(private val firestore: FirebaseFirestore) {

    suspend fun getSpeakersInfo(speakerId: Int): Result<List<SpeakersModel>> {
        return try {
            val snapshot = firestore.collection("speakers")
                    .whereEqualTo("id", speakerId)
                    .get()
                    .await()
            Result.Success(snapshot.toObjects<SpeakersModel>())
        } catch (e: FirebaseFirestoreException) {
            Result.Error(e.message)
        }
    }
}
