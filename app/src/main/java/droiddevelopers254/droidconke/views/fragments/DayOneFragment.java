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

import java.util.ArrayList;
import java.util.List;

import droiddevelopers254.droidconke.R;
import droiddevelopers254.droidconke.adapters.SessionsAdapter;
import droiddevelopers254.droidconke.models.SessionsModel;
import droiddevelopers254.droidconke.viewmodels.DayOneViewModel;

public class DayOneFragment extends Fragment {
    RecyclerView recyclerView;
    SessionsAdapter sessionsAdapter;
    List<String> sessionIds = new ArrayList<>();
    static RecyclerView.LayoutManager mLayoutManager;
    DayOneViewModel dayOneViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day_one, container, false);

        dayOneViewModel = ViewModelProviders.of(this).get(DayOneViewModel.class);

        recyclerView = view.findViewById(R.id.sessionsRv);

        getDayOneSessions();

        //observe live data emitted by view model
        dayOneViewModel.getSessions().observe(this, sessionsState -> {
            if (sessionsState.getDatabaseError() != null) {
                handleDatabaseError(sessionsState.getDatabaseError());
            } else {
                handleDayOneSessions(sessionsState.getSessionsModel());
            }
        });

        return view;
    }

    private void handleDayOneSessions(List<SessionsModel> sessionsList) {
        if (sessionsList != null) {
            sessionsAdapter.submitList(sessionsList);
            initView();
        }
    }

    private void handleDatabaseError(DatabaseError databaseError) {
    }

    private void getDayOneSessions() {
        dayOneViewModel.fetchDayOneSessions();
    }

    private void initView() {

        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        sessionsAdapter = new SessionsAdapter(getActivity());
        recyclerView.setAdapter(sessionsAdapter);

    }
}
