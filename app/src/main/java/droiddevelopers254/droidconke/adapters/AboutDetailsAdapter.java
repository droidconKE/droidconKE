package droiddevelopers254.droidconke.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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
        TextView aboutDetailsDescText,aboutDetailsTitleText,websiteText;
        ImageView aboutDetailsImg,expandImg;
        LinearLayout detailsLinear;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            aboutDetailsDescText=itemView.findViewById(R.id.aboutDetailsDescText);
            aboutDetailsTitleText=itemView.findViewById(R.id.aboutDetailsTitleText);
            aboutDetailsImg=itemView.findViewById(R.id.aboutDetailsImg);
            detailsLinear = itemView.findViewById(R.id.detailsLinear);
            websiteText=itemView.findViewById(R.id.websiteText);
            expandImg = itemView.findViewById(R.id.expandImg);
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
        holder.websiteText.setText(aboutDetailsModel.getSite());

        // Get the state
        boolean expanded = aboutDetailsModel.isExpanded();
        // Set the visibility based on state
        holder.detailsLinear.setVisibility(expanded ? View.VISIBLE : View.GONE);
        holder.expandImg.setImageResource(expanded ? R.drawable.ic_expand_less_black_24dp :R.drawable.ic_expand_more_black_24dp);

        //load about  image
        Glide.with(context).load(aboutDetailsModel.getLogoUrl())
                .thumbnail(Glide.with(context).load(aboutDetailsModel.getLogoUrl()))
                .apply(new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(holder.aboutDetailsImg);

        holder.itemView.setOnClickListener(view -> {
            // Get the current state of the item
            boolean expand = aboutDetailsModel.isExpanded();
            // Change the state
            aboutDetailsModel.setExpanded(!expand);
            // Notify the adapter that item has changed
            notifyItemChanged(position);

        });

    }

    @Override
    public int getItemCount() {
        return aboutDetailsModelList.size();
    }

}
