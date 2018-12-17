package droiddevelopers254.droidconke.views.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import droiddevelopers254.droidconke.R
import droiddevelopers254.droidconke.utils.SharedPref.PREF_NAME
import kotlinx.android.synthetic.main.map_bottom_sheet.view.*

class MapFragment : Fragment(), OnMapReadyCallback {
    private val senteuPlaza = LatLng(-1.289256, 36.783180)
    private var mMap: GoogleMap? = null
    private var senteuMarker: Marker? = null
    private var bottomSheetBehavior: BottomSheetBehavior<*>? = null
    private val mLastKnownLocation: Location? = null
    internal var mCameraPosition: CameraPosition? = null
    lateinit var mLocationRequest: LocationRequest
    internal var currentLatLng: LatLng? = null
    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null
    internal var permissionsRequired = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
    internal var sentToSettings = false
    lateinit var sharedPreferences: SharedPreferences

    private var mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            locationResult.locations.forEach { location ->
                Log.i("MapsActivity", "Location: " + location.latitude + " " + location.longitude)
                currentLatLng = LatLng(location.latitude, location.longitude)
            }
        }

    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_CALLBACK_CONSTANT -> {
                //check if all permissions are granted
                var allgranted = false
                loop@ for (i in grantResults.indices) {
                    when {
                        grantResults[i] == PackageManager.PERMISSION_GRANTED -> allgranted = true
                        else -> {
                            allgranted = false
                            break@loop
                        }
                    }
                }
                when {
                    allgranted -> mFusedLocationProviderClient!!.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper())
                    ActivityCompat.shouldShowRequestPermissionRationale(activity!!, permissionsRequired[0]) -> {
                        val builder = AlertDialog.Builder(activity!!)
                        builder.setTitle("Need Multiple Permissions")
                        builder.setMessage("This app needs Location and Storage permissions.")
                        builder.setPositiveButton("Grant") { dialog, _ ->
                            dialog.cancel()
                            ActivityCompat.requestPermissions(activity!!, permissionsRequired, PERMISSION_CALLBACK_CONSTANT)
                        }
                        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
                        builder.show()
                    }
                    else -> Toast.makeText(activity, "Unable to get Permission", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_map, container, false)

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity!!)
        sharedPreferences = activity!!.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        //show toolbar if its hidden
        (activity as AppCompatActivity).supportActionBar?.show()
        //bottom sheet view
        val bottomSheetView = view.bottomSheetView
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView)

        //collapse bottom sheet
        view.collapseBottomSheetImg.setOnClickListener {
            when {
                bottomSheetBehavior?.state == BottomSheetBehavior.STATE_EXPANDED -> bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }

        //open google maps intent to get directions
        view.googleDirectionsBtn.setOnClickListener {
            when {
                currentLatLng != null -> {
                    val uri = "http://maps.google.com/maps?f=d&hl=en&saddr=" + currentLatLng!!.latitude + "," + currentLatLng!!.longitude + "&daddr=" + senteuPlaza.latitude + "," + senteuPlaza.longitude
                    val intent = Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri))
                    startActivity(Intent.createChooser(intent, "Open with"))
                }
                else -> Toast.makeText(activity, "A problem occured in getting your location", Toast.LENGTH_SHORT).show()
            }


        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = childFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return view
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mLocationRequest = LocationRequest.create()
        mLocationRequest.interval = 1800000 // 30 minute interval
        mLocationRequest.fastestInterval = 1200000
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest)
        val client = LocationServices.getSettingsClient(activity!!)
        val task = client.checkLocationSettings(builder.build())
        task.addOnCompleteListener { task1 ->
            try {
                task1.getResult(ApiException::class.java)
                // All location settings are satisfied. The client can initialize location
                // requests here.

            } catch (exception: ApiException) {
                when (exception.statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                        // Location settings are not satisfied. But could be fixed by showing the
                        // user a dialog.
                        mFusedLocationProviderClient?.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper())
                        try {
                            // Cast to a resolvable exception.
                            val resolvable = exception as ResolvableApiException
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            resolvable.startResolutionForResult(
                                    activity,
                                    REQUEST_CHECK_SETTINGS)
                        } catch (e: IntentSender.SendIntentException) {
                            // Ignore the error.
                        } catch (e: ClassCastException) {
                            // Ignore, should be an impossible error.
                        }

                    }
                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                    }
                }// Location settings are not satisfied. However, we have no way to fix the
                // settings so we won't show the dialog.
            }
        }
        builder.setAlwaysShow(true)

        //add marker
        senteuMarker = mMap!!.addMarker(MarkerOptions().position(senteuPlaza))
        mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(senteuPlaza, DEFAULT_ZOOM.toFloat()))
        mMap!!.setOnMarkerClickListener {
            when {
                bottomSheetBehavior?.state != BottomSheetBehavior.STATE_EXPANDED -> bottomSheetBehavior?.setState(BottomSheetBehavior.STATE_EXPANDED)
                else -> bottomSheetBehavior?.setState(BottomSheetBehavior.STATE_COLLAPSED)
            }
            true
        }
        when {
            android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP -> checkLocationPermission()
            else -> mFusedLocationProviderClient?.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper())
        }
    }

    private fun checkLocationPermission() {
        when {
            ActivityCompat.checkSelfPermission(activity!!, permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED -> {
                when {
                    ActivityCompat.shouldShowRequestPermissionRationale(activity!!, permissionsRequired[0]) -> {
                        //Show Information about why you need the permission
                        val builder = AlertDialog.Builder(activity!!)
                        builder.setTitle("Need Multiple permissions")
                        builder.setMessage("This apps needs the Location and Storage permissions")
                        builder.setPositiveButton("Grant") { dialog, _ ->
                            dialog.cancel()
                            ActivityCompat.requestPermissions(activity!!, permissionsRequired, PERMISSION_CALLBACK_CONSTANT)
                        }
                        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
                        builder.show()
                    }
                    sharedPreferences.getBoolean(permissionsRequired[0], false) -> {
                        //Previously Permission Request was cancelled with 'Dont Ask Again',
                        // Redirect to Settings after showing Information about why you need the permission
                        val builder = AlertDialog.Builder(activity!!)
                        builder.setTitle("Need Multiple Permissions")
                        builder.setMessage("This app needs Location and Storage permissions.")
                        builder.setPositiveButton("Grant") { dialog, _ ->
                            dialog.cancel()
                            sentToSettings = true
                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                            val uri = Uri.fromParts("package", activity!!.packageName, null)
                            intent.data = uri
                            startActivityForResult(intent, REQUEST_PERMISSION_SETTING)
                            Toast.makeText(activity, "Go to Permissions to Grant  Storage and Location", Toast.LENGTH_LONG).show()
                        }
                        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
                    }
                    else -> //just request the permission
                        ActivityCompat.requestPermissions(activity!!, permissionsRequired, PERMISSION_CALLBACK_CONSTANT)
                }
                sharedPreferences.edit().putBoolean(permissionsRequired[0], true).apply()
            }
            else -> //You already have the permission, just go ahead.
                mFusedLocationProviderClient?.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper())
        }
    }

    @SuppressLint("MissingPermission")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        LocationSettingsStates.fromIntent(data!!)
        when (requestCode) {
            REQUEST_CHECK_SETTINGS -> when (resultCode) {
                Activity.RESULT_OK ->
                    // All required changes were successfully made
                    //start location updates
                    mFusedLocationProviderClient?.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper())
                Activity.RESULT_CANCELED -> {
                }
                else -> {
                }
            }// The user was asked to change settings, but chose not to
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        private const val DEFAULT_ZOOM = 17
        private const val KEY_CAMERA_POSITION = "camera_position"
        private const val KEY_LOCATION = "location"
        protected const val REQUEST_CHECK_SETTINGS = 0x1
        private const val PERMISSION_CALLBACK_CONSTANT = 100
        private const val REQUEST_PERMISSION_SETTING = 101
    }
}
