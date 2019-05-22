package droiddevelopers254.droidconke.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import droiddevelopers254.droidconke.datastates.Result
import droiddevelopers254.droidconke.datastates.UpdateTokenState
import droiddevelopers254.droidconke.utils.safeFirebaseCall
import kotlinx.coroutines.tasks.await

class UpdateTokenRepo {


    suspend fun update(userId: String, refreshToken: String) = safeFirebaseCall(
            call = {updateToken(userId, refreshToken)},
            errorMessage = "An error occurred"
    )

    suspend fun updateToken(userId: String, refreshToken: String): Result<Boolean> {
        val firebaseFirestore = Firebase.firestore
        firebaseFirestore.collection("users").document(userId).update("refresh_token",refreshToken).await()
        return Result.Success(true)

    }
}
