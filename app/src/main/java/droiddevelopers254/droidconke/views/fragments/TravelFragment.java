package droiddevelopers254.droidconke.views.fragments;

import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import droiddevelopers254.droidconke.BuildConfig;
import droiddevelopers254.droidconke.R;
import droiddevelopers254.droidconke.models.TravelInfoModel;
import droiddevelopers254.droidconke.models.WifiDetailsModel;
import droiddevelopers254.droidconke.ui.CollapsibleCard;
import droiddevelopers254.droidconke.viewmodels.TravelInfoViewModel;

public class TravelFragment extends Fragment{
    private static final String TAG = "TravelFragment";

    private CollapsibleCard shuttleServiceCard;
    private CollapsibleCard carpoolingParkingCard;
    private CollapsibleCard publicTransportationCard;
    private CollapsibleCard rideSharingCard;
    FirebaseRemoteConfig firebaseRemoteConfig;
    
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_travel, container, false);

        firebaseRemoteConfig=FirebaseRemoteConfig.getInstance();

        // [START enable_dev_mode]
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        firebaseRemoteConfig.setConfigSettings(configSettings);

        shuttleServiceCard = view.findViewById(R.id.shuttleInfoCard);
        carpoolingParkingCard = view.findViewById(R.id.carpoolingParkingCard);
        publicTransportationCard = view.findViewById(R.id.publicTransportationCard);
        rideSharingCard = view.findViewById(R.id.rideSharingCard);

        //get remote config values
        getRemoteConfigValues();

        return view;
    }

    private void getRemoteConfigValues() {
        long cacheExpiration= 3600;

        if (firebaseRemoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
            cacheExpiration = 0;
        }
        firebaseRemoteConfig.fetch(cacheExpiration)
                .addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful()) {
                        // After config data is successfully fetched, it must be activated before newly fetched
                        // values are returned.
                        firebaseRemoteConfig.activateFetched();
                    } else {

                    }
                    TravelInfoModel travelInfoModel = new TravelInfoModel(firebaseRemoteConfig.getString("driving_directions"), firebaseRemoteConfig.getString("public_transportation"), firebaseRemoteConfig.getString("car_pooling_parking_info")
                            ,firebaseRemoteConfig.getString("ride_sharing"));
                    showInfo(travelInfoModel);

                });
    }

    private void showInfo(TravelInfoModel travelInfoModel) {

            shuttleServiceCard.setCardDescription(travelInfoModel.getShuttleInfo());
            carpoolingParkingCard.setCardDescription(travelInfoModel.getCarpoolingParkingInfo());
            publicTransportationCard.setCardDescription(travelInfoModel.getPublicTransportationInfo());
            rideSharingCard.setCardDescription(travelInfoModel.getRideSharingInfo());
    }
}
