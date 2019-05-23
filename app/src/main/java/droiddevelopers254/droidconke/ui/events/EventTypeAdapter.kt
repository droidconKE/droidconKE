package droiddevelopers254.droidconke.ui.events

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import droiddevelopers254.droidconke.R
import droiddevelopers254.droidconke.models.EventTypeModel
import kotlinx.android.synthetic.main.event_type_details.view.*

class EventTypeAdapter(private val eventTypesList: List<EventTypeModel>, private val context: Context) : RecyclerView.Adapter<EventTypeAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val eventDescriptionText = itemView.eventDescriptionText
        private val eventImg = itemView.eventImg

        fun bindEvents(eventTypeModel: EventTypeModel){
            with(eventTypeModel){

                Glide.with(itemView.context).load(eventImageUrl)
                        .thumbnail(Glide.with(itemView.context).load(eventImageUrl))
                        .apply(RequestOptions()
                                .diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(eventImg)
                eventDescriptionText.text = description
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.event_type_details, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
      holder.bindEvents(eventTypesList[position])
    }

    override fun getItemCount(): Int {
        return eventTypesList.size
    }


}
