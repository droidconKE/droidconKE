package droiddevelopers254.droidconke.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import droiddevelopers254.droidconke.datastates.SpeakersState
import droiddevelopers254.droidconke.models.SpeakersModel

class SpeakersRepo {

    fun getSpeakersInfo(speakerId: Int): LiveData<SpeakersState> {
        val speakersStateMutableLiveData = MutableLiveData<SpeakersState>()
        val firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseFirestore.collection("speakers")
                .whereEqualTo("id", speakerId)
                .get()
                .addOnSuccessListener {
                    val speakersModel = it.toObjects(SpeakersModel::class.java)
                    speakersStateMutableLiveData.value = SpeakersState(speakersModel,null)
                }
                .addOnFailureListener {
                    speakersStateMutableLiveData.value =SpeakersState(null,it.message) }

        return speakersStateMutableLiveData
    }
}
