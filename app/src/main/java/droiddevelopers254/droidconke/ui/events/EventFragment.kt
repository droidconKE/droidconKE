package droiddevelopers254.droidconke.ui.events


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import droiddevelopers254.droidconke.BuildConfig
import droiddevelopers254.droidconke.R
import droiddevelopers254.droidconke.models.EventTypeModel
import droiddevelopers254.droidconke.models.WifiDetailsModel
import droiddevelopers254.droidconke.viewmodels.EventTypeViewModel
import kotlinx.android.synthetic.main.fragment_event.*
import kotlinx.android.synthetic.main.fragment_event.view.*
import org.jetbrains.anko.toast
import org.koin.android.ext.android.inject

class EventFragment : Fragment() {
  private val eventTypeViewModel: EventTypeViewModel by inject()
  lateinit var firebaseRemoteConfig: FirebaseRemoteConfig

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val view = inflater.inflate(R.layout.fragment_event, container, false)

    firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

    val wifiSsidText = view.wifiSsidText
    val wifiPasswordText = view.wifiPasswordText
    val eventTypesRv = view.eventTypesRv

    // [START enable_dev_mode]
    val configSettings = FirebaseRemoteConfigSettings.Builder()
        .setDeveloperModeEnabled(BuildConfig.DEBUG)
        .build()
    firebaseRemoteConfig.setConfigSettings(configSettings)

    firebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults)

    //observe live data emitted by view model
    observeLiveData()
    //fetch data from firebase
    eventTypeViewModel.fetchSessions()

    //get remote config values
    getRemoteConfigValues(wifiSsidText, wifiPasswordText)

    return view
  }

  private fun observeLiveData() {
    eventTypeViewModel.getWifiDetailsResponse().observe(viewLifecycleOwner) {
      handleFetchEventsResponse(it, eventTypesRv)
    }
    eventTypeViewModel.getWifiDetailsError().observe(viewLifecycleOwner) {
      handleDatabaseError(it)
    }
  }

  private fun getRemoteConfigValues(wifiSsidText: TextView, wifiPasswordText: TextView) {
    var cacheExpiration: Long = 3600

    // After config data is successfully fetched, it must be activated before newly fetched
    // values are returned.
    when {
      firebaseRemoteConfig.info.configSettings.isDeveloperModeEnabled -> cacheExpiration = 0
    }
    firebaseRemoteConfig.fetch(cacheExpiration)
        .addOnCompleteListener(requireActivity()) {
          when {
            it.isSuccessful -> // After config data is successfully fetched, it must be activated before newly fetched
              // values are returned.
              firebaseRemoteConfig.activate()
            else -> {
            }
          }
          val wifiDetailsModel = WifiDetailsModel(firebaseRemoteConfig.getString("wifi_ssid"), firebaseRemoteConfig.getString("wifi_password"))
          updateViews(wifiDetailsModel, wifiSsidText, wifiPasswordText)

        }
  }

  private fun updateViews(wifiDetailsModel: WifiDetailsModel, wifiSsidText: TextView, wifiPasswordText: TextView) {
    wifiSsidText.text = wifiDetailsModel.wifiSsid
    wifiPasswordText.text = wifiDetailsModel.wifiPassword
  }

  private fun handleFetchEventsResponse(eventTypeModelList: List<EventTypeModel>?, eventTypesRv: RecyclerView) {
    when {
      eventTypeModelList != null -> initView(eventTypeModelList, eventTypesRv)
    }
  }

  private fun handleDatabaseError(databaseError: String?) {
    activity?.toast(databaseError.toString())
  }

  private fun initView(eventTypeModelList: List<EventTypeModel>, eventTypesRv: RecyclerView) {
    val layoutManager = LinearLayoutManager(activity)
    eventTypesRv.layoutManager = layoutManager
    eventTypesRv.isNestedScrollingEnabled = false
    eventTypesRv.itemAnimator = DefaultItemAnimator()
    eventTypesRv.adapter = EventTypeAdapter(eventTypeModelList, activity!!)

  }

}
