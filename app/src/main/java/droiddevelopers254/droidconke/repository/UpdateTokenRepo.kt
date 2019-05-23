package droiddevelopers254.droidconke.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import droiddevelopers254.droidconke.datastates.Result
import kotlinx.coroutines.tasks.await

class UpdateTokenRepo(val firestore: FirebaseFirestore) {

    suspend fun updateToken(userId: String, refreshToken: String): Result<Boolean> {
        return try {
            firestore.collection("users").document(userId).update("refresh_token", refreshToken).await()
            Result.Success(true)
        } catch (e: FirebaseFirestoreException) {
            Result.Error(e.message)
        }


    }
}
