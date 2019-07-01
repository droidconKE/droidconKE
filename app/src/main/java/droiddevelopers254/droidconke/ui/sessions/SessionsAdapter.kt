package droiddevelopers254.droidconke.ui.sessions

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import droiddevelopers254.droidconke.R
import droiddevelopers254.droidconke.models.SessionsModel
import kotlinx.android.synthetic.main.item_session.view.*

class SessionsAdapter(private var sessionsModelList: List<SessionsModel>, private val itemClickListener: (SessionsModel) -> Unit) : RecyclerView.Adapter<SessionsAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View, val itemClickListener: (SessionsModel) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val sessionTitleText = itemView.sessionTitleText
        private val sessionRoomText = itemView.sessionRoomText
        private val sessionInAmPmText = itemView.sessionInAmPmText

        @SuppressLint("Range")
        fun bindSession(sessionsModel: SessionsModel) {
            with(sessionsModel) {
                sessionTitleText.text = title
                sessionRoomText.text = "$room"
                sessionInAmPmText.text ="$time_in_am $am_pm_label"

                itemView.setOnClickListener {
                    itemClickListener(this)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_session, parent, false)
        return MyViewHolder(itemView, itemClickListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindSession(sessionsModelList[position])
    }

    override fun getItemCount(): Int {
        return sessionsModelList.size
    }

    fun setSessionsAdapter(sessionsModelList: List<SessionsModel>) {
        this.sessionsModelList = sessionsModelList
        notifyDataSetChanged()
    }


}
