package droiddevelopers254.droidconke.adapters;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import droiddevelopers254.droidconke.models.FeedbackModel;

public class FeedbackAdapter extends ListAdapter<FeedbackModel, FeedbackAdapter.MyViewHolder> {

    private static final DiffUtil.ItemCallback<FeedbackModel> FEEDBACK_COMPARATOR = new DiffUtil.ItemCallback<FeedbackModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull FeedbackModel oldItem, @NonNull FeedbackModel newItem) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull FeedbackModel oldItem, @NonNull FeedbackModel newItem) {
            return false;
        }
    };

    public FeedbackAdapter() {
        super(FEEDBACK_COMPARATOR);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @NonNull
    @Override
    public FeedbackAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackAdapter.MyViewHolder holder, int position) {

    }

}
