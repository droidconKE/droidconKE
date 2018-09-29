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
import droiddevelopers254.droidconke.models.AboutDetailsModel;

public class AboutDetailsAdapter extends RecyclerView.Adapter<AboutDetailsAdapter.MyViewHolder> {
    private List<AboutDetailsModel> aboutDetailsModelList;
    private Context context;


    public AboutDetailsAdapter(List<AboutDetailsModel> aboutDetailsModelList, Context context){
        this.aboutDetailsModelList=aboutDetailsModelList;
        this.context=context;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView aboutDetailsDescText,aboutDetailsTitleText;
        ImageView aboutDetailsImg;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            aboutDetailsDescText=itemView.findViewById(R.id.aboutDetailsDescText);
            aboutDetailsTitleText=itemView.findViewById(R.id.aboutDetailsTitleText);
            aboutDetailsImg=itemView.findViewById(R.id.aboutDetailsImg);
        }
    }

    @NonNull
    @Override
    public AboutDetailsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.about_details, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AboutDetailsAdapter.MyViewHolder holder, int position) {
        AboutDetailsModel aboutDetailsModel=aboutDetailsModelList.get(position);
        holder.aboutDetailsTitleText.setText(aboutDetailsModel.getName());
        holder.aboutDetailsDescText.setText(aboutDetailsModel.getBio());

        //load about  image
        Glide.with(context).load(aboutDetailsModel.getLogoUrl())
                .thumbnail(Glide.with(context).load(aboutDetailsModel.getLogoUrl()))
                .transition(new DrawableTransitionOptions()
                .crossFade())
                .apply(new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(holder.aboutDetailsImg);


    }

    @Override
    public int getItemCount() {
        return aboutDetailsModelList.size();
    }

}
