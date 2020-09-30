package droiddevelopers254.droidconke.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import droiddevelopers254.droidconke.datastates.Result
import droiddevelopers254.droidconke.models.RoomModel
import droiddevelopers254.droidconke.utils.await

class RoomRepo(private val firestore: FirebaseFirestore) {

  suspend fun getRoomDetails(roomId: Int): Result<RoomModel> {
    return try {
      val snapshot = firestore.collection("rooms")
          .whereEqualTo("id", roomId)
          .get()
          .await()
      val doc = snapshot.documents.first()
      val roomModel = doc.toObject(RoomModel::class.java)
      Result.Success(roomModel!!)

    } catch (e: FirebaseFirestoreException) {
      Result.Error(e.message)
    }

  }
}
