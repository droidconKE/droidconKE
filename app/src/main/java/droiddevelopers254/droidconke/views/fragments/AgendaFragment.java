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
import droiddevelopers254.droidconke.adapters.AgendaAdapter;
import droiddevelopers254.droidconke.models.AgendaModel;
import droiddevelopers254.droidconke.utils.AgendaData;
import droiddevelopers254.droidconke.viewmodels.AgendaViewModel;

public class AgendaFragment extends Fragment {
    RecyclerView recyclerView;
    AgendaAdapter agendaAdapter;
    List<AgendaModel> agendaModelList = new ArrayList<>();
    static RecyclerView.LayoutManager mLayoutManager;
    AgendaViewModel agendaViewModel;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_agenda, container, false);

        agendaViewModel= ViewModelProviders.of(this).get(AgendaViewModel.class);

        recyclerView=view.findViewById(R.id.agendaRv);

        //fetch agendas
        agendaViewModel.fetchAgendas();
        initView();
        //observe live data emiited by view model
        agendaViewModel.getAgendas().observe(this,agendaState -> {
            if (agendaState.getDatabaseError() != null){
                handleDatabaseError(agendaState.getDatabaseError());
            }else {
                handleAgendaResponse(agendaState.getAgendaModel());
            }
        });

        return view;
    }

    private void handleAgendaResponse(List<AgendaModel> agendaList) {
        if (agendaList != null){
          agendaModelList=agendaList;
        }
    }

    private void handleDatabaseError(DatabaseError databaseError) {
    }

    private void initView() {
        agendaAdapter= new AgendaAdapter(agendaModelList,getActivity());
        mLayoutManager= new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(agendaAdapter);
    }
}
