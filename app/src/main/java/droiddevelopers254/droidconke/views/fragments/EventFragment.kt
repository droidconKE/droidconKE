package droiddevelopers254.droidconke.views.fragments


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import droiddevelopers254.droidconke.BuildConfig
import droiddevelopers254.droidconke.R
import droiddevelopers254.droidconke.adapters.EventTypeAdapter
import droiddevelopers254.droidconke.models.EventTypeModel
import droiddevelopers254.droidconke.models.WifiDetailsModel
import droiddevelopers254.droidconke.viewmodels.EventTypeViewModel
import kotlinx.android.synthetic.main.fragment_event.*

class EventFragment : Fragment() {
    lateinit var eventTypeViewModel: EventTypeViewModel
    lateinit var firebaseRemoteConfig: FirebaseRemoteConfig

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_event, container, false)

        eventTypeViewModel = ViewModelProviders.of(this).get(EventTypeViewModel::class.java)
        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

        // [START enable_dev_mode]
        val configSettings = FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build()
        firebaseRemoteConfig.setConfigSettings(configSettings)

        firebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults)

        //observe live data emitted by view model
        eventTypeViewModel.sessions.observe(this, Observer{
            if (it?.databaseError != null) {
                handleDatabaseError(it.databaseError)
            } else {
                handleFetchEventsResponse(it?.eventTypeModelList)
            }
        })
        //fetch data from firebase
        eventTypeViewModel.fetchSessions()

        //get remote config values
        getRemoteConfigValues()

        return view
    }

    private fun getRemoteConfigValues() {
        var cacheExpiration: Long = 3600

        if (firebaseRemoteConfig.info.configSettings.isDeveloperModeEnabled) {
            cacheExpiration = 0
        }
        firebaseRemoteConfig.fetch(cacheExpiration)
                .addOnCompleteListener(activity!!) {
                    if (it.isSuccessful) {
                        // After config data is successfully fetched, it must be activated before newly fetched
                        // values are returned.
                        firebaseRemoteConfig.activateFetched()
                    } else {

                    }
                    val wifiDetailsModel = WifiDetailsModel(firebaseRemoteConfig.getString("wifi_ssid"), firebaseRemoteConfig.getString("wifi_password"))
                    updateViews(wifiDetailsModel)

                }
    }

    private fun updateViews(wifiDetailsModel: WifiDetailsModel) {
        wifiSsidText.text = wifiDetailsModel.wifiSsid
        wifiPasswordText.text = wifiDetailsModel.wifiPassword
    }

    private fun handleFetchEventsResponse(eventTypeModelList: List<EventTypeModel>?) {
        if (eventTypeModelList != null) {
            initView(eventTypeModelList)
        }
    }

    private fun handleDatabaseError(databaseError: String?) {
        Toast.makeText(activity, databaseError, Toast.LENGTH_SHORT).show()
    }

    private fun initView(eventTypeModelList: List<EventTypeModel>) {
        val layoutManager = LinearLayoutManager(activity)
        eventTypesRv.layoutManager = layoutManager
        eventTypesRv.isNestedScrollingEnabled = false
        eventTypesRv.itemAnimator = DefaultItemAnimator()
        eventTypesRv.adapter = EventTypeAdapter(eventTypeModelList,activity!!)

    }

}
