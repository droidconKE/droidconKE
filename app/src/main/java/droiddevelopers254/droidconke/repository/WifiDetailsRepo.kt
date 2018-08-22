package droiddevelopers254.droidconke.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.remoteconfig.FirebaseRemoteConfig

import java.util.concurrent.Executor

import droiddevelopers254.droidconke.models.WifiDetailsModel

class WifiDetailsRepo {

    val wifiDetails: LiveData<WifiDetailsModel>
        get() {
            val wifiDetailsModelMutableLiveData = MutableLiveData<WifiDetailsModel>()
            val firebaseRemoteConfig: FirebaseRemoteConfig

            firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

            return wifiDetailsModelMutableLiveData
        }
}
