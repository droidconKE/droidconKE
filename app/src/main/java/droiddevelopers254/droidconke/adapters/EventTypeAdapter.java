package droiddevelopers254.droidconke.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import droiddevelopers254.droidconke.R;
import droiddevelopers254.droidconke.models.EventTypeModel;

public class EventTypeAdapter extends ListAdapter<EventTypeModel, EventTypeAdapter.MyViewHolder> {
    private Context context;

    private static DiffUtil.ItemCallback<EventTypeModel> EVENT_TYPE_COMPARATOR =
            new DiffUtil.ItemCallback<EventTypeModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull EventTypeModel oldItem,
                                       @NonNull EventTypeModel newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull EventTypeModel oldItem,
                                          @NonNull EventTypeModel newItem) {
            return oldItem == newItem;
        }
    };

    public EventTypeAdapter(Context context) {
        super(EVENT_TYPE_COMPARATOR);
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView eventDescriptionText;
        private ImageView eventImg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            eventImg = itemView.findViewById(R.id.eventImg);
            eventDescriptionText = itemView.findViewById(R.id.eventDescriptionText);

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
        EventTypeModel eventTypes = getItem(position);
        holder.eventImg.setImageResource(R.drawable.event_image);
        holder.eventDescriptionText.setText(eventTypes.getDescription());

    }

}
