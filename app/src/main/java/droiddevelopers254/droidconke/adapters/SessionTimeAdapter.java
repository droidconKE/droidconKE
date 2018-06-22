package droiddevelopers254.droidconke.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import droiddevelopers254.droidconke.R;
import droiddevelopers254.droidconke.models.SessionTimeModel;

public class SessionTimeAdapter extends RecyclerView.Adapter<SessionTimeAdapter.MyViewHolder> {
    private List<SessionTimeModel> sessionTimeModelList;
    private Context context;

    public SessionTimeAdapter(Context context,List<SessionTimeModel> sessionTimeModelList){
        this.context=context;
        this.sessionTimeModelList=sessionTimeModelList;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.timeSessionText)
        TextView timeSessionText;
        @BindView(R.id.amSessionTimeText)
        TextView amSessionTimeText;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.session_time_details, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SessionTimeModel sessionTimeModel= sessionTimeModelList.get(position);
        holder.timeSessionText.setText(sessionTimeModel.getSessionHour());
        holder.amSessionTimeText.setText(sessionTimeModel.getAmPm());

    }

    @Override
    public int getItemCount() {
        return sessionTimeModelList.size();
    }


}
