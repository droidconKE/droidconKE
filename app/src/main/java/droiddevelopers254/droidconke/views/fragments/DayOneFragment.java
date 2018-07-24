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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import droiddevelopers254.droidconke.R;
import droiddevelopers254.droidconke.adapters.SessionTimeAdapter;
import droiddevelopers254.droidconke.adapters.SessionsAdapter;
import droiddevelopers254.droidconke.database.entities.StarredSessionEntity;
import droiddevelopers254.droidconke.models.SessionTimeModel;
import droiddevelopers254.droidconke.models.SessionsModel;
import droiddevelopers254.droidconke.viewmodels.DayOneViewModel;

public class DayOneFragment extends Fragment {
    SessionsAdapter sessionsAdapter;
    List<SessionsModel> sessionsModelList = new ArrayList<>();
    List<SessionTimeModel> sessionTimeModelList = new ArrayList<>();
    List<String> sessionIds = new ArrayList<>();
    static RecyclerView.LayoutManager mLayoutManager;
    DayOneViewModel dayOneViewModel;
    @BindView(R.id.sessionsRv)
    RecyclerView sessionsRv;
    Unbinder unbinder;
    SessionTimeAdapter sessionTimeAdapter;
    boolean isStarred;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day_one, container, false);

        dayOneViewModel = ViewModelProviders.of(this).get(DayOneViewModel.class);
        unbinder = ButterKnife.bind(this, view);

        dayOneViewModel.getDayOneSessions();

        //observe live data emitted by view model
        dayOneViewModel.getSessions().observe(this,sessionsState -> {
            if(sessionsState.getSessionsModel() != null){
                sessionsModelList= sessionsState.getSessionsModel();
                initView();
            }else {
                handleError(sessionsState.getDatabaseError());
            }
        });

        return view;
    }

    private void handleError(String databaseError) {
        Toast.makeText(getActivity(),databaseError,Toast.LENGTH_SHORT).show();
    }

    private void initView() {
        sessionsAdapter = new SessionsAdapter(getActivity(), sessionsModelList, "day_one");
        mLayoutManager = new LinearLayoutManager(getActivity());
        sessionsRv.setLayoutManager(mLayoutManager);
        sessionsRv.setItemAnimator(new DefaultItemAnimator());
        sessionsRv.setAdapter(sessionsAdapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void onStarSession(SessionsModel sessionsModel) {
        isStarred= sessionsModel.getIsStarred();
        if (isStarred){

        }else {
            StarredSessionEntity starredSessionEntity = new StarredSessionEntity();
            starredSessionEntity.setDay_number(sessionsModel.getDay_number());
            starredSessionEntity.setDescription(sessionsModel.getDescription());
            starredSessionEntity.setDocumentId(sessionsModel.getDocumentId());
            starredSessionEntity.setDuration(sessionsModel.getDuration());
            starredSessionEntity.setId(sessionsModel.getId());
            starredSessionEntity.setMain_tag(sessionsModel.getMain_tag());
            starredSessionEntity.setRoom(sessionsModel.getRoom());
            starredSessionEntity.setRoom_id(sessionsModel.getRoom_id());
            starredSessionEntity.setSession_color(sessionsModel.getSession_color());
            starredSessionEntity.setSpeaker_id(sessionsModel.getSpeaker_id());
            starredSessionEntity.setStarred(false);
            starredSessionEntity.setTime(sessionsModel.getTime());
            starredSessionEntity.setTimestamp(sessionsModel.getTimestamp());
        }

    }
}
