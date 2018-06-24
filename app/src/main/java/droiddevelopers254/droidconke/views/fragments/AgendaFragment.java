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
import droiddevelopers254.droidconke.adapters.AgendaAdapter;
import droiddevelopers254.droidconke.models.AgendaModel;
import droiddevelopers254.droidconke.viewmodels.AgendaViewModel;

public class AgendaFragment extends Fragment {
    RecyclerView recyclerView;
    AgendaAdapter agendaAdapter;
    static RecyclerView.LayoutManager mLayoutManager;
    AgendaViewModel agendaViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_agenda, container, false);

        agendaViewModel = ViewModelProviders.of(this).get(AgendaViewModel.class);

        recyclerView = view.findViewById(R.id.agendaRv);

        //fetch agendas
        agendaViewModel.fetchAgendas();

        //observe live data emitted by view model
        agendaViewModel.getAgendas().observe(this, agendaState -> {
            if (agendaState.getDatabaseError() != null) {
                handleDatabaseError(agendaState.getDatabaseError());
            } else {
                handleAgendaResponse(agendaState.getAgendaModel());
            }
        });

        return view;
    }

    private void handleAgendaResponse(List<AgendaModel> agendaList) {
        if (agendaList != null) {
            agendaAdapter.submitList(agendaList);
            initView();
        }
    }

    private void handleDatabaseError(DatabaseError databaseError) {
    }

    private void initView() {
        agendaAdapter = new AgendaAdapter(getActivity());
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(agendaAdapter);
    }
}
