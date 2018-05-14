package droiddevelopers254.droidconke.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import droiddevelopers254.droidconke.R;
import droiddevelopers254.droidconke.models.Sessions;

public class SessionsAdapter extends RecyclerView.Adapter<SessionsAdapter.MyViewHolder> {
    private List<Sessions> sessionsList;
    private Context context;

    public SessionsAdapter(List<Sessions>sessionsList,Context context){
        this.sessionsList=sessionsList;
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
        Sessions sessions=sessionsList.get(position);
        holder.sessionCategoryText.setText(sessions.getSessionCategory());
        holder.sessionTimeText.setText(sessions.getSessionDuration());
        holder.sessionRoomText.setText(sessions.getSessionRoom());
        holder.sessionTitleText.setText(sessions.getSessionTitle());

    }

    @Override
    public int getItemCount() {
        return sessionsList.size();
    }

}
