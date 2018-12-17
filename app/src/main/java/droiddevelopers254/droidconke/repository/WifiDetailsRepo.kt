package droiddevelopers254.droidconke.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import droiddevelopers254.droidconke.models.WifiDetailsModel

class WifiDetailsRepo {

    val wifiDetails: LiveData<WifiDetailsModel>
        get() {
            val wifiDetailsModelMutableLiveData = MutableLiveData<WifiDetailsModel>()
            val firebaseRemoteConfig: FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

            return wifiDetailsModelMutableLiveData
        }
}
