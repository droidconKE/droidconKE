package droiddevelopers254.droidconke.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import droiddevelopers254.droidconke.R;
import droiddevelopers254.droidconke.models.Sessions;
import droiddevelopers254.droidconke.views.activities.SessionViewActivity;

public class SessionsAdapter extends RecyclerView.Adapter<SessionsAdapter.MyViewHolder> {
    private List<Sessions> sessionsList;
    private Context context;

    private   Sessions sessions;

    public SessionsAdapter(Context context,List<Sessions> sessions){
        this.sessionsList = sessions;
        this.context=context;

    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView sessionCategoryText,sessionTimeText,sessionRoomText,sessionTitleText;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            sessionCategoryText=itemView.findViewById(R.id.sessionCategoryText);
            sessionTimeText=itemView.findViewById(R.id.sessionTimeText);
            sessionTitleText=itemView.findViewById(R.id.sessionTitleText);
            sessionRoomText=itemView.findViewById(R.id.sessionRoomText);

        }

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.session_details, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        sessions =sessionsList.get(position);

        holder.sessionTitleText.setText(sessions.getTitle());
        holder.sessionTimeText.setText(sessions.getDuration());
        holder.sessionRoomText.setText(sessions.getRoom());
        holder.sessionCategoryText.setText(sessions.getTopic());

    }

    @Override
    public int getItemCount() {

        return sessionsList.size();
    }


}
