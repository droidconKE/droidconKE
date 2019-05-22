package droiddevelopers254.droidconke.repository

import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import droiddevelopers254.droidconke.datastates.Result
import droiddevelopers254.droidconke.models.RoomModel

class RoomRepo {
    var roomModel : RoomModel= RoomModel()

    suspend fun getRoomDetails(roomId: Int) : Result<RoomModel>{
        return try {
            val firebaseFirestore = Firebase.firestore
            val snapshot = firebaseFirestore.collection("rooms")
                    .whereEqualTo("id", roomId)
                    .get()
                    .await()
            val snap = snapshot.toObjects<RoomModel>()

            snap.forEach {
                roomModel = it
            }
            Result.Success(roomModel)

        }catch (e : FirebaseFirestoreException){
            Result.Error(e.message)
        }

    }
}
