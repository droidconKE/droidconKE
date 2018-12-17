package droiddevelopers254.droidconke.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import droiddevelopers254.droidconke.R
import droiddevelopers254.droidconke.models.AboutDetailsModel
import kotlinx.android.synthetic.main.about_details.view.*
import droiddevelopers254.droidconke.R.id.expandImg
import droiddevelopers254.droidconke.R.id.detailsLinear



class AboutDetailsAdapter(private val aboutDetailsModelList: List<AboutDetailsModel>, private val context: Context, private val itemClick: (AboutDetailsModel) -> Unit) : RecyclerView.Adapter<AboutDetailsAdapter.MyViewHolder>() {

     class MyViewHolder(itemView: View, private val itemClick: (AboutDetailsModel) -> Unit) : RecyclerView.ViewHolder(itemView) {
         private val aboutDetailsDescText = itemView.aboutDetailsDescText
         private val aboutDetailsTitleText = itemView.aboutDetailsTitleText
         private val aboutDetailsImg = itemView.aboutDetailsImg
         private val detailsLinear = itemView.detailsLinear
         private val expandImg = itemView.expandImg

         fun bindAboutDetails(aboutDetailsModel: AboutDetailsModel){
             with(aboutDetailsModel){
                 Glide.with(itemView.context).load(logoUrl)
                         .thumbnail(Glide.with(itemView.context).load(logoUrl))
                         .apply(RequestOptions()
                                 .diskCacheStrategy(DiskCacheStrategy.ALL))
                         .into(aboutDetailsImg)

                 aboutDetailsDescText.text = bio
                 aboutDetailsTitleText.text = name


                 itemView.setOnClickListener {

             }
                 // Get the state
                 val expanded = aboutDetailsModel.expanded
                 // Set the visibility based on state
                 detailsLinear.visibility = when {
                     expanded -> View.VISIBLE
                     else -> View.GONE
                 }
                 expandImg.setImageResource(when {
                     expanded -> R.drawable.ic_expand_less_black_24dp
                     else -> R.drawable.ic_expand_more_blak_24dp
                 })

             }
         }
     }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.about_details, parent, false)
        return MyViewHolder(itemView,itemClick)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindAboutDetails(aboutDetailsModelList[position])
        val aboutDetailsModel = aboutDetailsModelList.get(position)
        holder.itemView.setOnClickListener {
            // Get the current state of the item
            val expand = aboutDetailsModel.expanded
            // Change the state
            aboutDetailsModel.expanded = !expand
            // Notify the adapter that item has changed
            notifyItemChanged(position)
        }
    }
    override fun getItemCount(): Int {
        return aboutDetailsModelList.size

    }

}
