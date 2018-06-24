package droiddevelopers254.droidconke.views.fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseError;

import java.util.List;

import droiddevelopers254.droidconke.R;
import droiddevelopers254.droidconke.adapters.EventTypeAdapter;
import droiddevelopers254.droidconke.models.EventTypeModel;
import droiddevelopers254.droidconke.viewmodels.EventTypeViewModel;

public class EventFragment extends Fragment {
    RecyclerView recyclerView;
    EventTypeAdapter eventTypeAdapter;
    static RecyclerView.LayoutManager mLayoutManager;
    EventTypeViewModel eventTypeViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event, container, false);

        eventTypeViewModel = ViewModelProviders.of(this).get(EventTypeViewModel.class);

        recyclerView = view.findViewById(R.id.eventTypesRv);

        //observe live data emiited by view model
        eventTypeViewModel.getSessions().observe(this, eventTypeModel -> {
            if (eventTypeModel.getDatabaseError() != null) {
                handleDatabaseError(eventTypeModel.getDatabaseError());
            } else {
                handleFetchEventsResponse(eventTypeModel.getEventTypeModel());
            }
        });
        //fetch data from firebase
        eventTypeViewModel.fetchSessions();

        return view;
    }

    private void handleFetchEventsResponse(List<EventTypeModel> eventTypeModelList) {
        if (eventTypeModelList != null) {
            eventTypeAdapter.submitList(eventTypeModelList);
            initView();
        }
    }

    private void handleDatabaseError(DatabaseError databaseError) {
    }

    private void initView() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        eventTypeAdapter = new EventTypeAdapter(getActivity());
        recyclerView.setAdapter(eventTypeAdapter);

    }
}
