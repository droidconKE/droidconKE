package droiddevelopers254.droidconke.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.chip.Chip;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import droiddevelopers254.droidconke.R;
import droiddevelopers254.droidconke.models.FiltersModel;

public class ChipViewAdapter extends RecyclerView.Adapter<ChipViewAdapter.MyViewHolder> {
    List<FiltersModel> filtersModelList= new ArrayList<>();
    private Context context;


    public ChipViewAdapter(List<FiltersModel> filtersModelList, Context context){
        this.context=context;
        this.filtersModelList=filtersModelList;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        Chip categoryChip;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryChip=itemView.findViewById(R.id.categoryChip);
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chip_details, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        FiltersModel filtersModel=filtersModelList.get(position);
        holder.categoryChip.setChipText(filtersModel.getName());
        holder.categoryChip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Toast.makeText(context,"Checked",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return filtersModelList.size();
    }
}
