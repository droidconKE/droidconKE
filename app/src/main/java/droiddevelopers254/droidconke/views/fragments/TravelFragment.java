package droiddevelopers254.droidconke.views.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import droiddevelopers254.droidconke.R;
import droiddevelopers254.droidconke.models.TravelInfoModel;
import droiddevelopers254.droidconke.ui.CollapsibleCard;
import droiddevelopers254.droidconke.viewmodels.TravelInfoViewModel;

public class TravelFragment extends Fragment{
    private static final String TAG = "TravelFragment";

    private CollapsibleCard bikingCard;
    private CollapsibleCard shuttleServiceCard;
    private CollapsibleCard carpoolingParkingCard;
    private CollapsibleCard publicTransportationCard;
    private CollapsibleCard rideSharingCard;

    TravelInfoViewModel travelInfoViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_travel, container, false);

        travelInfoViewModel= ViewModelProviders.of(this).get(TravelInfoViewModel.class);

        bikingCard = view.findViewById(R.id.bikingCard);
        shuttleServiceCard = view.findViewById(R.id.shuttleInfoCard);
        carpoolingParkingCard = view.findViewById(R.id.carpoolingParkingCard);
        publicTransportationCard = view.findViewById(R.id.publicTransportationCard);
        rideSharingCard = view.findViewById(R.id.rideSharingCard);

        updateInfo();

        //observe live data emiited by view model
        travelInfoViewModel.getTravelInfo().observe(this,travelInfoModel -> {
            if (travelInfoModel != null){
                showInfo(travelInfoModel);
            }
        });

        return view;
    }
    private void updateInfo(){
        travelInfoViewModel.fetchRemoteConfigValues();
    }

    private void showInfo(TravelInfoModel travelInfoModel) {
            bikingCard.setCardDescription(travelInfoModel.getBikingInfo());
            shuttleServiceCard.setCardDescription(travelInfoModel.getShuttleInfo());
            carpoolingParkingCard.setCardDescription(travelInfoModel.getCarpoolingParkingInfo());
            publicTransportationCard.setCardDescription(travelInfoModel.getPublicTransportationInfo());
            rideSharingCard.setCardDescription(travelInfoModel.getRideSharingInfo());
    }
}
