package droiddevelopers254.droidconke.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.toObjects
import droiddevelopers254.droidconke.datastates.Result
import droiddevelopers254.droidconke.models.Announcement
import droiddevelopers254.droidconke.utils.await

class AnnouncementRepo(val firestore: FirebaseFirestore) {

    suspend fun getAnnouncements(): Result<List<Announcement>> {
        return try {
            val snapshot = firestore.collection("announcements").get().await()
            return Result.Success(snapshot.toObjects())

        } catch (e: FirebaseFirestoreException) {
            Result.Error(e.message)
        }

    }
}