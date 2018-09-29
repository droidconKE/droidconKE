package droiddevelopers254.droidconke.views.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;

import droiddevelopers254.droidconke.R;

import static droiddevelopers254.droidconke.utils.SharedPref.PREF_NAME;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    LatLng senteuPlaza = new LatLng(-1.289256, 36.783180);
    private static final int DEFAULT_ZOOM = 17;

    private GoogleMap mMap;
    private Marker senteuMarker;
    private BottomSheetBehavior bottomSheetBehavior;
    ImageView collapseBottomSheetImg;
    MaterialButton googleDirectionsBtn;
    private Location mLastKnownLocation;
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";
    CameraPosition mCameraPosition;
    LocationRequest mLocationRequest;
    LatLng currentLatLng;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    String [] permissionsRequired=new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    boolean sentToSettings = false;
    private static final int PERMISSION_CALLBACK_CONSTANT = 100;
    SharedPreferences sharedPreferences;
    private static final int REQUEST_PERMISSION_SETTING = 101;

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CALLBACK_CONSTANT) {
            //check if all permissions are granted
            boolean allgranted = false;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    allgranted = true;
                } else {
                    allgranted = false;
                    break;
                }
            }
            if (allgranted) {
                mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());

            } else if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permissionsRequired[0])) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Need Multiple Permissions");
                builder.setMessage("This app needs Location and Storage permissions.");
                builder.setPositiveButton("Grant", (dialog, which) -> {
                    dialog.cancel();
                    ActivityCompat.requestPermissions(getActivity(), permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
                });
                builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
                builder.show();
            } else {
                Toast.makeText(getActivity(), "Unable to get Permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_map, container, false);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        sharedPreferences=getActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        //show toolbar if its hidden
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        //bottom sheet view
        View bottomSheetView=view.findViewById(R.id.bottomSheetView);
        bottomSheetBehavior= BottomSheetBehavior.from(bottomSheetView);
        collapseBottomSheetImg=bottomSheetView.findViewById(R.id.collapseBottomSheetImg);
        googleDirectionsBtn=view.findViewById(R.id.googleDirectionsBtn);

        //collapse bottom sheet
        collapseBottomSheetImg.setOnClickListener(view1 -> {
            if(bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        //open google maps intent to get directions
        googleDirectionsBtn.setOnClickListener(view1 -> {
            if (currentLatLng != null ){
                String uri = "http://maps.google.com/maps?f=d&hl=en&saddr="+currentLatLng.latitude+","+currentLatLng.longitude+"&daddr="+senteuPlaza.latitude+","+senteuPlaza.longitude;
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(Intent.createChooser(intent, "Open with"));
            }else {
                Toast.makeText(getActivity(),"A problem occured in getting your location",Toast.LENGTH_SHORT).show();
            }


        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(1800000); // 30 minute interval
        mLocationRequest.setFastestInterval(1200000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        SettingsClient client = LocationServices.getSettingsClient(getActivity());
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnCompleteListener(task1 -> {
            try {
                LocationSettingsResponse response = task1.getResult(ApiException.class);
                // All location settings are satisfied. The client can initialize location
                // requests here.

            } catch (ApiException exception) {
                switch (exception.getStatusCode()) {
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the
                        // user a dialog.
                        mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                        try {
                            // Cast to a resolvable exception.
                            ResolvableApiException resolvable = (ResolvableApiException) exception;
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            resolvable.startResolutionForResult(
                                    getActivity(),
                                    REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        } catch (ClassCastException e) {
                            // Ignore, should be an impossible error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        break;
                }
            }
        });
        builder.setAlwaysShow(true);

        //add marker
        senteuMarker= mMap.addMarker(new MarkerOptions().position(senteuPlaza));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(senteuPlaza,DEFAULT_ZOOM));
        mMap.setOnMarkerClickListener(marker -> {
            if(bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
            else {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
            return true;
        });
        if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            checkLocationPermission();
        } else {
            mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
        }
    }

    private void checkLocationPermission() {
        if(ActivityCompat.checkSelfPermission(getActivity(),permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),permissionsRequired[0])){
                //Show Information about why you need the permission
                AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
                builder.setTitle("Need Multiple permissions");
                builder.setMessage("This apps needs the Location and Storage permissions");
                builder.setPositiveButton("Grant", (dialog, which) -> {
                    dialog.cancel();
                    ActivityCompat.requestPermissions(getActivity(),permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
                });
                builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
                builder.show();
            }else if (sharedPreferences.getBoolean(permissionsRequired[0],false)){
                //Previously Permission Request was cancelled with 'Dont Ask Again',
                // Redirect to Settings after showing Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Need Multiple Permissions");
                builder.setMessage("This app needs Location and Storage permissions.");
                builder.setPositiveButton("Grant", (dialog, which) -> {
                    dialog.cancel();
                    sentToSettings = true;
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                    intent.setData(uri);
                    startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                    Toast.makeText(getActivity(), "Go to Permissions to Grant  Storage and Location", Toast.LENGTH_LONG).show();
                });
                builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
            }else {
                //just request the permission
                ActivityCompat.requestPermissions(getActivity(),permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
            }
            sharedPreferences.edit().putBoolean(permissionsRequired[0],true).apply();
        }else {
            //You already have the permission, just go ahead.
            mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
        }
    }

    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            for (Location location : locationResult.getLocations()) {
                Log.i("MapsActivity", "Location: " + location.getLatitude() + " " + location.getLongitude());
                currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());

            }
        }

    };

    @SuppressLint("MissingPermission")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        // All required changes were successfully made
                        //start location updates
                        mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                        break;
                    case Activity.RESULT_CANCELED:
                        // The user was asked to change settings, but chose not to

                        break;
                    default:
                        break;
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
