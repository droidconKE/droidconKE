package droiddevelopers254.droidconke.views.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import droiddevelopers254.droidconke.R;
import droiddevelopers254.droidconke.adapters.AgendaAdapter;
import droiddevelopers254.droidconke.adapters.SessionsAdapter;
import droiddevelopers254.droidconke.models.Sessions;
import droiddevelopers254.droidconke.utils.SessionData;

public class DayOneFragment extends Fragment{
    RecyclerView recyclerView;
    SessionsAdapter sessionsAdapter;
    List<Sessions> sessionsList = new ArrayList<>();
    static RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day_one, container, false);

        recyclerView=view.findViewById(R.id.sessionsRv);
        sessionsList= SessionData.getSessions();

        initView();

        return view;
    }

    private void initView() {
        sessionsAdapter= new SessionsAdapter(sessionsList,getActivity());
        mLayoutManager= new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(sessionsAdapter);
    }
}
