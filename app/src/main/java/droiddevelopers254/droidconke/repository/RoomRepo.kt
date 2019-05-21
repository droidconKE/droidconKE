package droiddevelopers254.droidconke.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import droiddevelopers254.droidconke.datastates.RoomState
import droiddevelopers254.droidconke.models.RoomModel

class RoomRepo(private val firestore: FirebaseFirestore) {

    suspend fun getRoomDetails(roomId: Int): LiveData<RoomState> {
        val roomStateLiveData = MutableLiveData<RoomState>()
        try {
            val snapshot = firestore.collection("rooms")
                    .whereEqualTo("id", roomId)
                    .get()
                    .await()
            val doc = snapshot.documents.first()
            val roomModel = doc.toObject(RoomModel::class.java)
            roomStateLiveData.value = RoomState(roomModel, null)

        } catch (e: Exception) {
            roomStateLiveData.value = RoomState(null, "Error getting room details")
        }
        return roomStateLiveData
    }
}
