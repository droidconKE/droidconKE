package droiddevelopers254.droidconke.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import java.util.concurrent.Executor;

import droiddevelopers254.droidconke.models.WifiDetailsModel;

public class WifiDetailsRepo {

    public WifiDetailsRepo(){

    }

    public LiveData<WifiDetailsModel> getWifiDetails(){
        final MutableLiveData<WifiDetailsModel> wifiDetailsModelMutableLiveData= new MutableLiveData<>();
        FirebaseRemoteConfig firebaseRemoteConfig;

        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();


        return wifiDetailsModelMutableLiveData;
    }
}
