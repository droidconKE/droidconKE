package droiddevelopers254.droidconke.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import droiddevelopers254.droidconke.datastates.RoomState
import droiddevelopers254.droidconke.models.RoomModel

class RoomRepo {

    fun getRoomDetails(roomId: Int): LiveData<RoomState> {
        val roomStateLiveData = MutableLiveData<RoomState>()
        val firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseFirestore.collection("rooms")
                .whereEqualTo("id", roomId)
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        for (queryDocumentSnapshot in it.result) {
                            val roomModel = queryDocumentSnapshot.toObject(RoomModel::class.java)
                            roomStateLiveData.value = RoomState(roomModel,null)

                        }
                    } else {
                        roomStateLiveData.value= RoomState(null,"Error getting room details")
                    }

                }
        return roomStateLiveData
    }
}
