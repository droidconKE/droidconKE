package droiddevelopers254.droidconke.repository

import android.app.Activity
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import droiddevelopers254.droidconke.models.TravelInfoModel

class TravelInfoRepo(internal var context: Context) {
    internal lateinit var firebaseRemoteConfig: FirebaseRemoteConfig

    // After config data is successfully fetched, it must be activated before newly fetched
    // values are returned.
    val travelInfo: LiveData<TravelInfoModel>
        get() {
            val travelInfoModelMutableLiveData = MutableLiveData<TravelInfoModel>()
            firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
            val cacheExpiration = (12 * 60 * 60).toLong()
            firebaseRemoteConfig.fetch(cacheExpiration)
                    .addOnCompleteListener(context as Activity) { task ->
                        when {
                            task.isSuccessful -> firebaseRemoteConfig.activateFetched()
                            else -> {

                            }
                        }

                    }
            return travelInfoModelMutableLiveData
        }
}
