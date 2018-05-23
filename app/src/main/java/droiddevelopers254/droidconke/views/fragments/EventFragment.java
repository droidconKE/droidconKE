package droiddevelopers254.droidconke.views.fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import java.util.ArrayList;
import java.util.List;

import droiddevelopers254.droidconke.R;
import droiddevelopers254.droidconke.adapters.EventTypeAdapter;
import droiddevelopers254.droidconke.models.EventTypeModel;
import droiddevelopers254.droidconke.models.WifiDetailsModel;
import droiddevelopers254.droidconke.viewmodels.EventTypeViewModel;

public class EventFragment extends Fragment{
    RecyclerView recyclerView;
    EventTypeAdapter eventTypeAdapter;
    static RecyclerView.LayoutManager mLayoutManager;
    List<EventTypeModel> eventTypesList = new ArrayList<>();
    EventTypeViewModel eventTypeViewModel;
    TextView wifiSsidText,wifiPasswordText;
    FirebaseRemoteConfig firebaseRemoteConfig;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event, container, false);

        eventTypeViewModel= ViewModelProviders.of(this).get(EventTypeViewModel.class);
        firebaseRemoteConfig=FirebaseRemoteConfig.getInstance();

        recyclerView =view.findViewById(R.id.eventTypesRv);
        wifiSsidText=view.findViewById(R.id.wifiSsidText);
        wifiPasswordText=view.findViewById(R.id.wifiPasswordText);

        //observe live data emiited by view model
        eventTypeViewModel.getSessions().observe(this,eventTypeModel -> {
            if (eventTypeModel.getDatabaseError() != null){
              handleDatabaseError(eventTypeModel.getDatabaseError());
            }else {
                handleFetchEventsResponse(eventTypeModel.getEventTypeModel());
            }
        });
        //fetch data from firebase
        eventTypeViewModel.fetchSessions();

        //get remote config values
        getRemoteConfigValues();

        return view;
    }

    private void getRemoteConfigValues() {
        long cacheExpiration= 12*60*60;
        firebaseRemoteConfig.fetch(cacheExpiration)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            // After config data is successfully fetched, it must be activated before newly fetched
                            // values are returned.
                            firebaseRemoteConfig.activateFetched();
                        } else {

                        }
                        WifiDetailsModel wifiDetailsModel= new WifiDetailsModel(firebaseRemoteConfig.getString("wifi_ssid"),firebaseRemoteConfig.getString("wifi_password"));
                        updateViews(wifiDetailsModel);

                    }

                }  );
    }

    private void updateViews(WifiDetailsModel wifiDetailsModel) {
        wifiSsidText.setText(wifiDetailsModel.getWifiSsid());
        wifiPasswordText.setText(wifiDetailsModel.getWifiPassword());
    }

    private void handleFetchEventsResponse(List<EventTypeModel> eventTypeModelList) {
        if (eventTypesList != null){
            eventTypesList = eventTypeModelList;
            initView();
        }
    }

    private void handleDatabaseError(DatabaseError databaseError) {
    }

    private void initView() {
        mLayoutManager= new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        eventTypeAdapter = new EventTypeAdapter(eventTypesList, getActivity());
        recyclerView.setAdapter(eventTypeAdapter);

    }
}
