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

import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import droiddevelopers254.droidconke.R;
import droiddevelopers254.droidconke.adapters.SessionTimeAdapter;
import droiddevelopers254.droidconke.adapters.SessionsAdapter;
import droiddevelopers254.droidconke.database.entities.SessionsEntity;
import droiddevelopers254.droidconke.models.SessionTimeModel;
import droiddevelopers254.droidconke.models.SessionsModel;
import droiddevelopers254.droidconke.viewmodels.DayOneViewModel;

public class DayOneFragment extends Fragment {
    SessionsAdapter sessionsAdapter;
    List<SessionsEntity> sessionsModelList = new ArrayList<>();
    List<SessionTimeModel> sessionTimeModelList = new ArrayList<>();
    List<String> sessionIds = new ArrayList<>();
    static RecyclerView.LayoutManager mLayoutManager;
    DayOneViewModel dayOneViewModel;
    @BindView(R.id.sessionsRv)
    RecyclerView sessionsRv;
    Unbinder unbinder;
    SessionTimeAdapter sessionTimeAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day_one, container, false);

        dayOneViewModel = ViewModelProviders.of(this).get(DayOneViewModel.class);
        unbinder = ButterKnife.bind(this, view);

        //observe live data emitted by view model
        dayOneViewModel.getDayOneLiveData().observe(this,listResource -> {
            assert listResource != null;
            sessionsModelList=listResource.data;
            initView();
        });


        return view;
    }

    private void initView() {

        mLayoutManager = new LinearLayoutManager(getActivity());
        sessionsRv.setLayoutManager(mLayoutManager);
        sessionsRv.setItemAnimator(new DefaultItemAnimator());
        sessionsAdapter = new SessionsAdapter(getActivity(), sessionsModelList, "day_one");
        sessionsRv.setAdapter(sessionsAdapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
