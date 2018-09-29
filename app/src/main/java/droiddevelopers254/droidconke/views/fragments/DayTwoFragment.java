package droiddevelopers254.droidconke.views.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import droiddevelopers254.droidconke.R;
import droiddevelopers254.droidconke.adapters.SessionsAdapter;
import droiddevelopers254.droidconke.models.SessionsModel;
import droiddevelopers254.droidconke.utils.ItemClickListener;
import droiddevelopers254.droidconke.viewmodels.DayTwoViewModel;
import droiddevelopers254.droidconke.views.activities.SessionViewActivity;

public class DayTwoFragment extends Fragment {
    SessionsAdapter sessionsAdapter;
    List<SessionsModel> sessionsModelList = new ArrayList<>();
    static RecyclerView.LayoutManager mLayoutManager;
    DayTwoViewModel dayTwoViewModel;
    @BindView(R.id.sessionsRv)
    RecyclerView sessionsRv;
    Unbinder unbinder;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day_two, container, false);

        dayTwoViewModel = ViewModelProviders.of(this).get(DayTwoViewModel.class);

        dayTwoViewModel.getDayTwoSessions();
        //observe live data emitted by view model
        dayTwoViewModel.getSessions().observe(this, sessionsState -> {
            if (sessionsState.getSessionsModel() != null) {
                sessionsModelList = sessionsState.getSessionsModel();
                initView();
            } else {
                handleError(sessionsState.getDatabaseError());
            }
        });

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void handleError(String databaseError) {
        Toast.makeText(getActivity(), databaseError, Toast.LENGTH_SHORT).show();
    }

    private void initView() {
        sessionsAdapter = new SessionsAdapter(getActivity(), sessionsModelList, "day_two");
        mLayoutManager = new LinearLayoutManager(getActivity());
        sessionsRv.setLayoutManager(mLayoutManager);
        sessionsRv.setItemAnimator(new DefaultItemAnimator());
        sessionsRv.setAdapter(sessionsAdapter);
        sessionsRv.addOnItemTouchListener(new ItemClickListener(getActivity(), sessionsRv, new ItemClickListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getContext(), SessionViewActivity.class);
                intent.putExtra("sessionId", sessionsModelList.get(position).getId());
                intent.putExtra("dayNumber", "day_two");
                intent.putExtra("starred", sessionsModelList.get(position).getIsStarred());
                intent.putIntegerArrayListExtra("speakerId", sessionsModelList.get(position).getSpeaker_id());
                intent.putExtra("roomId", sessionsModelList.get(position).getRoom_id());
                intent.putExtra("sessionName",sessionsModelList.get(position).getTitle());
                intent.putExtra("sessionUrl",sessionsModelList.get(position).getUrl());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
