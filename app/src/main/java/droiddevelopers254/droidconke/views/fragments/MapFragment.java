package droiddevelopers254.droidconke.views.fragments;

import android.support.design.button.MaterialButton;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import droiddevelopers254.droidconke.R;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    LatLng senteuPlaza = new LatLng(-1.289256, 36.783180);
    private static final int DEFAULT_ZOOM = 17;

    private GoogleMap mMap;
    private Marker senteuMarker;
    private BottomSheetBehavior bottomSheetBehavior;
    ImageView collapseBottomSheetImg;
    MaterialButton googleDirectionsBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_map, container, false);

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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
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
    }

}
