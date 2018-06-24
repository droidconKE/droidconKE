package droiddevelopers254.droidconke.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import droiddevelopers254.droidconke.R;
import droiddevelopers254.droidconke.models.AgendaModel;

public class AgendaAdapter extends ListAdapter<AgendaModel, AgendaAdapter.MyViewHolder> {
    private Context context;

    private static DiffUtil.ItemCallback<AgendaModel> COMPARATOR = new DiffUtil.ItemCallback<AgendaModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull AgendaModel oldItem, @NonNull AgendaModel newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull AgendaModel oldItem, @NonNull AgendaModel newItem) {
            return oldItem == newItem;
        }
    };

    public AgendaAdapter(Context context) {
        super(COMPARATOR);
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView agendaTitleText, agendaTimelineText;
        ImageView agendaImg;
        LinearLayout agendaLinear;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            agendaTitleText = itemView.findViewById(R.id.agendaTitleText);
            agendaTimelineText = itemView.findViewById(R.id.agendaTimelineText);
            agendaImg = itemView.findViewById(R.id.agendaImg);
            agendaLinear = itemView.findViewById(R.id.agendaLinear);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.agenda_details, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        AgendaModel agendaModel = getItem(position);
        holder.agendaTitleText.setText(agendaModel.getTitle());
        holder.agendaTimelineText.setText(agendaModel.getTime());
        //TODO add logic for changing agenda icon
        holder.agendaLinear.setBackgroundColor(Color.parseColor(agendaModel.getBackground_color()));
    }
}
