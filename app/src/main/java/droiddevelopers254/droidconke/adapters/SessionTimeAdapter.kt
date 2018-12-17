package droiddevelopers254.droidconke.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import droiddevelopers254.droidconke.R
import droiddevelopers254.droidconke.models.SessionTimeModel
import kotlinx.android.synthetic.main.session_time_details.view.*

class SessionTimeAdapter(private val context: Context, private val sessionTimeModelList: List<SessionTimeModel>) : RecyclerView.Adapter<SessionTimeAdapter.MyViewHolder>() {
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var timeSessionText = itemView.timeSessionText
        internal var amSessionTimeText = itemView.amSessionTimeText

        fun bindSessionTime(sessionTimeModel: SessionTimeModel){
            with(sessionTimeModel){
                timeSessionText.text = sessionHour
                amSessionTimeText.text = amPm
            }
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.session_time_details, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindSessionTime(sessionTimeModelList[position])
    }
    override fun getItemCount(): Int {
        return sessionTimeModelList.size
    }


}
