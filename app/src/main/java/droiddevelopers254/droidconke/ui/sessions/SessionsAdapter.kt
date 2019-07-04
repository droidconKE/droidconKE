package droiddevelopers254.droidconke.ui.sessions

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import droiddevelopers254.droidconke.R
import droiddevelopers254.droidconke.models.SessionsModel
import kotlinx.android.synthetic.main.item_session.view.*

class SessionsAdapter(private var sessionsModelList: List<SessionsModel>, private val itemClickListener: (SessionsModel) -> Unit) : RecyclerView.Adapter<SessionsAdapter.SessionsViewHolder>() {

    class SessionsViewHolder(itemView: View, val itemClickListener: (SessionsModel) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val sessionTitleText = itemView.sessionTitleText
        private val sessionRoomText = itemView.sessionRoomText
        private val sessionInAmPmText = itemView.sessionInAmPmText
        private val sessionAudienceText = itemView.sessionAudienceText

        @SuppressLint("Range")
        fun bindSession(sessionsModel: SessionsModel) {
            with(sessionsModel) {
                sessionTitleText.text = title
                sessionRoomText.text = "$room"
                sessionInAmPmText.text = "$time_in_am $am_pm_label"
                sessionAudienceText.text = session_audience

                when (session_audience) {
                    "intermediate" -> sessionAudienceText.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.colorDeepOrange))
                    "advanced" -> sessionAudienceText.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.tag_text_red))
                    "beginner" -> sessionAudienceText.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.colorGreen))
                    "general" -> sessionAudienceText.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.colorLightBlue))
                }

                itemView.setOnClickListener {
                    itemClickListener(this)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_session, parent, false)
        return SessionsViewHolder(itemView, itemClickListener)
    }

    override fun onBindViewHolder(holder: SessionsViewHolder, position: Int) {
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
