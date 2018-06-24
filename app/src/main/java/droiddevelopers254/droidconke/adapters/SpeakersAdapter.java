package droiddevelopers254.droidconke.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import droiddevelopers254.droidconke.R;
import droiddevelopers254.droidconke.models.SpeakersModel;

public class SpeakersAdapter extends ListAdapter<SpeakersModel, SpeakersAdapter.MyViewHolder> {

    private Context context;

    private static final DiffUtil.ItemCallback<SpeakersModel> SPEAKERS_COMPARATOR = new DiffUtil.ItemCallback<SpeakersModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull SpeakersModel oldItem, @NonNull SpeakersModel newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull SpeakersModel oldItem, @NonNull SpeakersModel newItem) {
            return oldItem == newItem;
        }
    };

    public SpeakersAdapter(Context context) {
        super(SPEAKERS_COMPARATOR);
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView speakerNameText, speakerCompanyText;
        CircleImageView speakerImg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            speakerNameText = itemView.findViewById(R.id.speakerNameText);
            speakerCompanyText = itemView.findViewById(R.id.speakerCompanyText);
            speakerImg = itemView.findViewById(R.id.speakerImg);
        }
    }

    @NonNull
    @Override
    public SpeakersAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.speaker_details, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SpeakersAdapter.MyViewHolder holder, int position) {

    }

}
