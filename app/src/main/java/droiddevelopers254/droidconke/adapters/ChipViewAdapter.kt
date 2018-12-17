package droiddevelopers254.droidconke.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import droiddevelopers254.droidconke.R
import droiddevelopers254.droidconke.models.FiltersModel
import kotlinx.android.synthetic.main.chip_details.view.*

class ChipViewAdapter(private val filtersModelList: List<FiltersModel>, private val context: Context) : RecyclerView.Adapter<ChipViewAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var categoryChip = itemView.categoryChip

        fun bindFilters(filtersModel: FiltersModel){
            with(filtersModel){

            }
        }


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.chip_details, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindFilters(filtersModelList[position])
    }
    override fun getItemCount(): Int {
        return filtersModelList.size
    }
}
