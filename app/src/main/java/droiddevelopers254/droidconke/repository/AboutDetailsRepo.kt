package droiddevelopers254.droidconke.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObjects
import droiddevelopers254.droidconke.datastates.Result
import droiddevelopers254.droidconke.models.AboutDetailsModel
import droiddevelopers254.droidconke.utils.await

class AboutDetailsRepo(val firestore: FirebaseFirestore) {

    suspend fun getAboutDetails(aboutType: String): Result<List<AboutDetailsModel>> {
        return try {
            val snapshot = firestore.collection(aboutType)
                    .orderBy("id", Query.Direction.ASCENDING)
                    .get()
                    .await()
            val aboutDetailsModelList = snapshot.toObjects<AboutDetailsModel>()
            Result.Success(aboutDetailsModelList)
        } catch (e: Exception) {
            Result.Error( e.message)
        }
    }
}
