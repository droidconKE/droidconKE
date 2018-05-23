package droiddevelopers254.droidconke.views.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import droiddevelopers254.droidconke.R;
import droiddevelopers254.droidconke.models.TravelInfoModel;
import droiddevelopers254.droidconke.ui.CollapsibleCard;

public class TravelFragment extends Fragment{
    private static final String TAG = "TravelFragment";

    private TravelInfoModel mTravelInfoModel;

    private CollapsibleCard bikingCard;
    private CollapsibleCard shuttleServiceCard;
    private CollapsibleCard carpoolingParkingCard;
    private CollapsibleCard publicTransportationCard;
    private CollapsibleCard rideSharingCard;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_travel, container, false);

        bikingCard = view.findViewById(R.id.bikingCard);
        shuttleServiceCard = view.findViewById(R.id.shuttleInfoCard);
        carpoolingParkingCard = view.findViewById(R.id.carpoolingParkingCard);
        publicTransportationCard = view.findViewById(R.id.publicTransportationCard);
        rideSharingCard = view.findViewById(R.id.rideSharingCard);

        updateInfo();
        showInfo();

        return view;
    }
    private void updateInfo(){
        TravelInfoModel travelInfoModel = new TravelInfoModel(getString(R.string.shuttle_info), getString(R.string.public_transportation_info), getString(R.string.car_pooling_parking_info), getString(R.string.biking_info), getString(R.string.ride_sharing_info));
        mTravelInfoModel = travelInfoModel;

    }

    private void showInfo() {
        if (mTravelInfoModel != null) {
            bikingCard.setCardDescription(mTravelInfoModel.getBikingInfo());
            shuttleServiceCard.setCardDescription(mTravelInfoModel.getShuttleInfo());
            carpoolingParkingCard.setCardDescription(mTravelInfoModel.getCarpoolingParkingInfo());
            publicTransportationCard.setCardDescription(mTravelInfoModel.getPublicTransportationInfo());
            rideSharingCard.setCardDescription(mTravelInfoModel.getRideSharingInfo());
        } else {
            Log.e(TAG, "TravelInfoModel should not be null.");
        }
    }
}
