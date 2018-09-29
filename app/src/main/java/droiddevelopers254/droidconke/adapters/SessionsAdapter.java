package droiddevelopers254.droidconke.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.chip.Chip;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import droiddevelopers254.droidconke.R;
import droiddevelopers254.droidconke.models.SessionsModel;
import droiddevelopers254.droidconke.models.StarredSessionModel;
import droiddevelopers254.droidconke.viewmodels.StarrViewModel;

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
        @BindView(R.id.sessionDetailsLinear)
        LinearLayout sessionDetailsLinear;
        @BindView(R.id.sessionRoomText)
        TextView sessionRoomText;
        @BindView(R.id.sessionLabelText)
        TextView sessionLabelText;
        @BindView(R.id.sessionCategoryText)
        TextView sessionCategoryText;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
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
        holder.sessionLabelText.setBackgroundColor(Color.parseColor(sessionsModel.getSession_color()));
        holder.sessionCategoryText.setText(sessionsModel.getTopic());
        holder.sessionRoomText.setText(sessionsModel.getDuration() + " / " + sessionsModel.getRoom()+" / "+sessionsModel.getTime_in_am()+" "+sessionsModel.getAm_pm_label());
    }

    @Override
    public int getItemCount() {
        return sessionsModelList.size();
    }


    //called when we get data back from adapter

    public void setSessionsAdapter(List<SessionsModel> sessionsModelList){
        this.sessionsModelList =sessionsModelList;
        notifyDataSetChanged();
    }


}
