package droiddevelopers254.droidconke.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import droiddevelopers254.droidconke.R;
import droiddevelopers254.droidconke.models.EventTypesModel;

public class EventTypeAdapter extends RecyclerView.Adapter<EventTypeAdapter.MyViewHolder> {
    private List<EventTypesModel> eventTypesList;
    private Context context;

    public EventTypeAdapter(List<EventTypesModel> eventTypesList, Context context){
        this.eventTypesList=eventTypesList;
        this.context=context;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView eventDescriptionText;
        private ImageView eventImg;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            eventImg=itemView.findViewById(R.id.eventImg);
            eventDescriptionText=itemView.findViewById(R.id.eventDescriptionText);

        }
    }
    @NonNull
    @Override
    public EventTypeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_type_details, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventTypeAdapter.MyViewHolder holder, int position) {
        EventTypesModel eventTypes = eventTypesList.get(position);
        holder.eventImg.setImageResource(R.drawable.event_image);
        holder.eventDescriptionText.setText(eventTypes.getEventDescription());

    }

    @Override
    public int getItemCount() {
        return eventTypesList.size();
    }


}
