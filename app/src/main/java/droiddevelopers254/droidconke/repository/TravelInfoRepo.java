package droiddevelopers254.droidconke.repository;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import java.util.concurrent.Executor;

import droiddevelopers254.droidconke.models.TravelInfoModel;

public class TravelInfoRepo {
    FirebaseRemoteConfig firebaseRemoteConfig;
    Context context;

    public TravelInfoRepo(Context context){
        this.context=context;
    }

    public LiveData<TravelInfoModel> getTravelInfo() {
        final MutableLiveData<TravelInfoModel> travelInfoModelMutableLiveData = new MutableLiveData<>();
        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        long cacheExpiration= 12*60*60;
        firebaseRemoteConfig.fetch(cacheExpiration)
                .addOnCompleteListener((Activity) context, task -> {
                    if (task.isSuccessful()) {

                        // After config data is successfully fetched, it must be activated before newly fetched
                        // values are returned.
                        firebaseRemoteConfig.activateFetched();
                    } else {

                    }

                });
        return travelInfoModelMutableLiveData;
    }
}
