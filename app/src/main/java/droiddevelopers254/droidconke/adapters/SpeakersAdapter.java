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

import de.hdodenhof.circleimageview.CircleImageView;
import droiddevelopers254.droidconke.R;
import droiddevelopers254.droidconke.models.SpeakersModel;

public class SpeakersAdapter extends RecyclerView.Adapter<SpeakersAdapter.MyViewHolder> {
    private List<SpeakersModel> speakersList;
    private Context context;

    public SpeakersAdapter(List<SpeakersModel> speakersList,Context context){
        this.speakersList=speakersList;
        this.context=context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView speakerNameText,speakerCompanyText;
        CircleImageView speakerImg;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            speakerNameText=itemView.findViewById(R.id.speakerNameText);
            speakerCompanyText=itemView.findViewById(R.id.speakerCompanyText);
            speakerImg=itemView.findViewById(R.id.speakerImg);
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
        SpeakersModel speakersModel=speakersList.get(position);
        holder.speakerNameText.setText(speakersModel.getName());
        holder.speakerCompanyText.setText(speakersModel.getCompany());

        //load speaker profile image
        Glide.with(context).load(speakersModel.getPhotoUrl())
                .thumbnail(Glide.with(context).load(speakersModel.getPhotoUrl()))
                .transition(new DrawableTransitionOptions()
                        .crossFade())
                .apply(new RequestOptions()
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(holder.speakerImg);

    }

    @Override
    public int getItemCount() {
        return speakersList.size();
    }

}
