package droiddevelopers254.droidconke.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import droiddevelopers254.droidconke.datastates.SpeakersState
import droiddevelopers254.droidconke.models.SpeakersModel

class SpeakersRepo(private val firestore: FirebaseFirestore) {

    suspend fun getSpeakersInfo(speakerId: Int): LiveData<SpeakersState> {
        val speakersStateMutableLiveData = MutableLiveData<SpeakersState>()
        try {
            val snapshot = firestore.collection("speakers")
                    .whereEqualTo("id", speakerId)
                    .get()
                    .await()
            val speakersModel = snapshot.toObjects(SpeakersModel::class.java)
            speakersStateMutableLiveData.value = SpeakersState(speakersModel, null)
        } catch (e: Exception) {
            speakersStateMutableLiveData.value = SpeakersState(null, e.message)
        }
        return speakersStateMutableLiveData
    }
}
