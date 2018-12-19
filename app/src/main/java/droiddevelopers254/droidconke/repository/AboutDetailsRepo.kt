package droiddevelopers254.droidconke.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import droiddevelopers254.droidconke.datastates.AboutDetailsState
import droiddevelopers254.droidconke.models.AboutDetailsModel

class AboutDetailsRepo(val firestore: FirebaseFirestore) {

    suspend fun getAboutDetails(aboutType: String): AboutDetailsState {
        return try {
            val snapshot = firestore.collection(aboutType)
                    .orderBy("id", Query.Direction.ASCENDING)
                    .get()
                    .await()
            val aboutDetailsModelList = snapshot.toObjects(AboutDetailsModel::class.java)
            AboutDetailsState(aboutDetailsModelList)
        } catch (e: Exception) {
            AboutDetailsState(null, e.message)
        }
    }
}
