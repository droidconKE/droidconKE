package droiddevelopers254.droidconke.views.fragments;

import android.os.Bundle;
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
import droiddevelopers254.droidconke.models.SessionsModel;

public class DayOneFragment extends Fragment{
    RecyclerView recyclerView;
    SessionsAdapter sessionsAdapter;
    List<SessionsModel> sessionsModelList = new ArrayList<>();
    List<String> sessionIds = new ArrayList<>();
    static RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day_one, container, false);

        recyclerView=view.findViewById(R.id.sessionsRv);

        initView();
        getDayOneSessions();

        return view;
    }

    private void getDayOneSessions(){

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("day_one");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(databaseReference!=null){

                    for(DataSnapshot data :dataSnapshot.getChildren()){

                        SessionsModel sessionsModel = data.getValue(SessionsModel.class);
                        sessionsModelList.add(sessionsModel);
                    }

                    //notify data change
                    sessionsAdapter.notifyDataSetChanged();

                }else{

                    Toast.makeText(getActivity(),"Null data",Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void initView() {

        mLayoutManager= new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        sessionsAdapter = new SessionsAdapter(getActivity(), sessionsModelList);
        recyclerView.setAdapter(sessionsAdapter);

    }
}
