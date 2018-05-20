package droiddevelopers254.droidconke.views.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import droiddevelopers254.droidconke.R;
import droiddevelopers254.droidconke.adapters.EventTypeAdapter;
import droiddevelopers254.droidconke.models.EventTypesModel;

public class EventFragment extends Fragment{
    RecyclerView recyclerView;
    EventTypeAdapter eventTypeAdapter;
    static RecyclerView.LayoutManager mLayoutManager;
    List<EventTypesModel> eventTypesList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event, container, false);

        recyclerView =view.findViewById(R.id.eventTypesRv);

        initView();

        return view;
    }

    private void initView() {
        mLayoutManager= new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        eventTypeAdapter = new EventTypeAdapter(eventTypesList, getActivity());
        recyclerView.setAdapter(eventTypeAdapter);
    }
}
