package droiddevelopers254.droidconke.repository

import android.content.Context
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import droiddevelopers254.droidconke.datastates.Result
import kotlinx.coroutines.tasks.await

class TravelInfoRepo(internal var context: Context) {
    private lateinit var firebaseRemoteConfig: FirebaseRemoteConfig

    suspend fun getTravelInfo(): Result<String> {
        return try {
            firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
            val cacheExpiration = (12 * 60 * 60).toLong()
            firebaseRemoteConfig.fetch(cacheExpiration).await()
            Result.Success("Fetched successfully")
        } catch (e: FirebaseRemoteConfigException) {
            Result.Error(e.message)
        }
    }
}
