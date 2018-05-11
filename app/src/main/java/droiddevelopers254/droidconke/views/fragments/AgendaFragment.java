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
import droiddevelopers254.droidconke.models.Agenda;
import droiddevelopers254.droidconke.utils.AgendaData;

public class AgendaFragment extends Fragment {
    RecyclerView recyclerView;
    AgendaAdapter agendaAdapter;
    List<Agenda> agendaList= new ArrayList<>();
    static RecyclerView.LayoutManager mLayoutManager;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_agenda, container, false);
        
        recyclerView=view.findViewById(R.id.agendaRv);
        agendaList= AgendaData.getAgendas();
        
        initView();

        return view;
    }

    private void initView() {
        agendaAdapter= new AgendaAdapter(agendaList,getActivity());
        mLayoutManager= new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(agendaAdapter);
    }
}
