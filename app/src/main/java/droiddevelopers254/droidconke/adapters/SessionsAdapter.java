package droiddevelopers254.droidconke.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import droiddevelopers254.droidconke.R;
import droiddevelopers254.droidconke.models.SessionsModel;
import droiddevelopers254.droidconke.models.StarredSessionModel;
import droiddevelopers254.droidconke.viewmodels.StarrViewModel;
import droiddevelopers254.droidconke.views.activities.SessionViewActivity;

public class SessionsAdapter extends RecyclerView.Adapter<SessionsAdapter.MyViewHolder> {
    private List<SessionsModel> sessionsModelList;
    private Context context;
    private SessionsModel sessionsModel;
    private String starStatus, dayNumber;
    private StarredSessionModel starredSessionModel;
    private StarrViewModel starrViewModel;


    public SessionsAdapter(Context context, List<SessionsModel> sessionsModelList, String dayNumber) {
        this.sessionsModelList = sessionsModelList;
        this.context = context;
        this.dayNumber = dayNumber;
        starredSessionModel = new StarredSessionModel();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.sessionTitleText)
        TextView sessionTitleText;
        @BindView(R.id.sessionLabelText)
        TextView sessionLabelText;
        @BindView(R.id.sessionCategoryText)
        TextView sessionCategoryText;
        @BindView(R.id.sessionDetailsLinear)
        LinearLayout sessionDetailsLinear;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
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
        sessionsModel = sessionsModelList.get(position);
        holder.sessionTitleText.setText(sessionsModel.getTitle());
        holder.sessionCategoryText.setText(sessionsModel.getTopic());
        holder.sessionLabelText.setBackgroundColor(Color.parseColor(sessionsModel.getSession_color()));
        sessionsModel = sessionsModelList.get(position);

    }
    @Override
    public int getItemCount() {
        return sessionsModelList.size();
    }


}
