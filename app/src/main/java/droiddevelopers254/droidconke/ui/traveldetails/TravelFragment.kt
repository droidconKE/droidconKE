package droiddevelopers254.droidconke.ui.traveldetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import droiddevelopers254.droidconke.BuildConfig
import droiddevelopers254.droidconke.R
import droiddevelopers254.droidconke.models.TravelInfoModel
import droiddevelopers254.droidconke.utils.CollapsibleCard
import kotlinx.android.synthetic.main.fragment_travel.*
import org.koin.android.ext.android.inject

class TravelFragment : Fragment() {
    private val firebaseRemoteConfig: FirebaseRemoteConfig by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_travel, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // [START enable_dev_mode]
        val configSettings = FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build()
        firebaseRemoteConfig.setConfigSettings(configSettings)

        getRemoteConfigValues(shuttleInfoCard, carpoolingParkingCard, publicTransportationCard, rideSharingCard)
    }

    private fun getRemoteConfigValues(shuttleServiceCard: CollapsibleCard, carpoolingParkingCard: CollapsibleCard, publicTransportationCard: CollapsibleCard, rideSharingCard: CollapsibleCard) {
        var cacheExpiration: Long = 3600

        // After config data is successfully fetched, it must be activated before newly fetched
        // values are returned.
        when {
            firebaseRemoteConfig.info.configSettings.isDeveloperModeEnabled -> cacheExpiration = 0
        }
        firebaseRemoteConfig.fetch(cacheExpiration)
                .addOnCompleteListener(activity!!) { task ->
                    when {
                        task.isSuccessful -> // After config data is successfully fetched, it must be activated before newly fetched
                            // values are returned.
                            firebaseRemoteConfig.activate()
                        else -> {

                        }
                    }
                    val travelInfoModel = TravelInfoModel(firebaseRemoteConfig.getString("driving_directions"), firebaseRemoteConfig.getString("public_transportation"), firebaseRemoteConfig.getString("car_pooling_parking_info"),
                            firebaseRemoteConfig.getString("ride_sharing"))
                    showInfo(travelInfoModel, shuttleServiceCard, carpoolingParkingCard, publicTransportationCard, rideSharingCard)

                }
    }

    private fun showInfo(travelInfoModel: TravelInfoModel, shuttleServiceCard: CollapsibleCard, carpoolingParkingCard: CollapsibleCard, publicTransportationCard: CollapsibleCard, rideSharingCard: CollapsibleCard) {
        shuttleServiceCard.setCardDescription(travelInfoModel.shuttleInfo)
        carpoolingParkingCard.setCardDescription(travelInfoModel.carpoolingParkingInfo)
        publicTransportationCard.setCardDescription(travelInfoModel.publicTransportationInfo)
        rideSharingCard.setCardDescription(travelInfoModel.rideSharingInfo)
    }
}
