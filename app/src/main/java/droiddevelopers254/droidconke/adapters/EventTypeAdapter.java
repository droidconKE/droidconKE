package droiddevelopers254.droidconke.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import droiddevelopers254.droidconke.R;
import droiddevelopers254.droidconke.models.EventTypeModel;

public class EventTypeAdapter extends RecyclerView.Adapter<EventTypeAdapter.MyViewHolder> {
    private List<EventTypeModel> eventTypesList;
    private Context context;

    public EventTypeAdapter(List<EventTypeModel> eventTypesList, Context context){
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
        EventTypeModel eventTypes = eventTypesList.get(position);
        holder.eventDescriptionText.setText(eventTypes.getDescription());

        //load event  image
        Glide.with(context).load(eventTypes.getEventImageUrl())
                .thumbnail(Glide.with(context).load(eventTypes.getEventImageUrl()))
                .transition(new DrawableTransitionOptions()
                        .crossFade())
                .apply(new RequestOptions()
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(holder.eventImg);

    }

    @Override
    public int getItemCount() {
        return eventTypesList.size();
    }

}
