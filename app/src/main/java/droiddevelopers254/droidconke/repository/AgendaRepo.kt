package droiddevelopers254.droidconke.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import droiddevelopers254.droidconke.datastates.Result
import droiddevelopers254.droidconke.models.AgendaModel
import droiddevelopers254.droidconke.utils.await

class AgendaRepo(val firestore: FirebaseFirestore) {

    suspend fun agendaData(): Result<List<AgendaModel>> {
        return try {
            val snapshot = firestore.collection("agenda").orderBy("id", Query.Direction.ASCENDING).get().await()
            return Result.Success(snapshot.toObjects())

        } catch (e: FirebaseFirestoreException) {
            Result.Error(e.message)
        }

    }
}
