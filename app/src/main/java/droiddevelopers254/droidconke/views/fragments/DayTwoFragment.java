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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import droiddevelopers254.droidconke.R;
import droiddevelopers254.droidconke.adapters.SessionsAdapter;
import droiddevelopers254.droidconke.database.entities.SessionsEntity;
import droiddevelopers254.droidconke.models.SessionsModel;
import droiddevelopers254.droidconke.viewmodels.DayTwoViewModel;

public class DayTwoFragment extends Fragment {
    RecyclerView recyclerView;
    SessionsAdapter sessionsAdapter;
    List<SessionsEntity> sessionsModelList = new ArrayList<>();
    static RecyclerView.LayoutManager mLayoutManager;
    DayTwoViewModel dayTwoViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day_two, container, false);

        dayTwoViewModel= ViewModelProviders.of(this).get(DayTwoViewModel.class);

        recyclerView=view.findViewById(R.id.sessionsRv);

        //observe live data emitted by view model
        dayTwoViewModel.getSessionsLiveData().observe(this,listResource -> {
            assert listResource != null;
            sessionsModelList = listResource.data;
            initView();
        });


        return view;
    }
    private void initView() {

        mLayoutManager= new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        sessionsAdapter = new SessionsAdapter(getActivity(), sessionsModelList,"day_two");
        recyclerView.setAdapter(sessionsAdapter);
    }

}
