package droiddevelopers254.droidconke.views.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings

import droiddevelopers254.droidconke.BuildConfig
import droiddevelopers254.droidconke.R
import droiddevelopers254.droidconke.models.TravelInfoModel
import droiddevelopers254.droidconke.ui.CollapsibleCard
import kotlinx.android.synthetic.main.fragment_travel.view.*

class TravelFragment : Fragment() {
    private lateinit var firebaseRemoteConfig: FirebaseRemoteConfig

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_travel, container, false)

        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

        // [START enable_dev_mode]
        val configSettings = FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build()
        firebaseRemoteConfig.setConfigSettings(configSettings)

        //get remote config values
        val bikingCard = view.bikingCard
        val shuttleServiceCard = view.shuttleInfoCard
        val carpoolingParkingCard = view.carpoolingParkingCard
        val publicTransportationCard = view.publicTransportationCard
        val rideSharingCard = view.rideSharingCard

        getRemoteConfigValues(bikingCard,shuttleServiceCard,carpoolingParkingCard,publicTransportationCard,rideSharingCard)

        return view
    }

    private fun getRemoteConfigValues(bikingCard: CollapsibleCard, shuttleServiceCard: CollapsibleCard, carpoolingParkingCard: CollapsibleCard, publicTransportationCard: CollapsibleCard, rideSharingCard: CollapsibleCard) {
        var cacheExpiration: Long = 3600

        if (firebaseRemoteConfig.info.configSettings.isDeveloperModeEnabled) {
            cacheExpiration = 0
        }
        firebaseRemoteConfig.fetch(cacheExpiration)
                .addOnCompleteListener(activity!!) { task ->
                    if (task.isSuccessful) {
                        // After config data is successfully fetched, it must be activated before newly fetched
                        // values are returned.
                        firebaseRemoteConfig.activateFetched()
                    } else {

                    }
                    val travelInfoModel = TravelInfoModel(firebaseRemoteConfig.getString("driving_directions"), firebaseRemoteConfig.getString("public_transportation"), firebaseRemoteConfig.getString("car_pooling_parking_info"),
                            firebaseRemoteConfig.getString("biking"), firebaseRemoteConfig.getString("ride_sharing"))
                    showInfo(travelInfoModel,bikingCard,shuttleServiceCard,carpoolingParkingCard,publicTransportationCard,rideSharingCard)

                }
    }

    private fun showInfo(travelInfoModel: TravelInfoModel, bikingCard: CollapsibleCard, shuttleServiceCard: CollapsibleCard, carpoolingParkingCard: CollapsibleCard, publicTransportationCard: CollapsibleCard, rideSharingCard: CollapsibleCard) {
        bikingCard.setCardDescription(travelInfoModel.bikingInfo)
        shuttleServiceCard.setCardDescription(travelInfoModel.shuttleInfo)
        carpoolingParkingCard.setCardDescription(travelInfoModel.carpoolingParkingInfo)
        publicTransportationCard.setCardDescription(travelInfoModel.publicTransportationInfo)
        rideSharingCard.setCardDescription(travelInfoModel.rideSharingInfo)
    }
}
