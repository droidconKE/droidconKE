package droiddevelopers254.droidconke.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import droiddevelopers254.droidconke.datastates.Result
import droiddevelopers254.droidconke.datastates.SpeakersState
import droiddevelopers254.droidconke.models.SpeakersModel

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
