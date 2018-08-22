package droiddevelopers254.droidconke.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import droiddevelopers254.droidconke.R
import droiddevelopers254.droidconke.models.EventTypeModel
import kotlinx.android.synthetic.main.event_type_details.view.*

class EventTypeAdapter(private val eventTypesList: List<EventTypeModel>, private val context: Context) : RecyclerView.Adapter<EventTypeAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val eventDescriptionText = itemView.eventDescriptionText
        private val eventImg = itemView.eventImg

        fun bindEvents(eventTypeModel: EventTypeModel){
            with(eventTypeModel){
                eventImg.setImageResource(R.drawable.event_image)
                eventDescriptionText.text = description
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventTypeAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.event_type_details, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EventTypeAdapter.MyViewHolder, position: Int) {
      holder.bindEvents(eventTypesList[position])
    }

    override fun getItemCount(): Int {
        return eventTypesList.size
    }


}
