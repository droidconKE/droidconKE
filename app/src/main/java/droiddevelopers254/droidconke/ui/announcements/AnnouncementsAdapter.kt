package droiddevelopers254.droidconke.ui.announcements

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import droiddevelopers254.droidconke.R
import droiddevelopers254.droidconke.models.Announcement
import kotlinx.android.synthetic.main.item_announcements.view.*

class AnnouncementsAdapter(private var announcementList: List<Announcement>) : RecyclerView.Adapter<AnnouncementsAdapter.AnnouncementViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnnouncementViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_announcements, parent, false)
        return AnnouncementViewHolder(itemView)
    }

    override fun getItemCount(): Int = announcementList.size

    override fun onBindViewHolder(holder: AnnouncementViewHolder, position: Int) {
        holder.bindAnnouncement(announcementList[position])
    }

    fun setAnnouncements(announcementList: List<Announcement>) {
        this.announcementList = announcementList
        notifyDataSetChanged()

    }


    class AnnouncementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val announcementTitleText = itemView.announcementTitleText
        private val announcementDescriptionText = itemView.announcementDescription

        fun bindAnnouncement(announcement: Announcement) {
            with(announcement) {
                announcementTitleText.text = title
                announcementDescriptionText.text = description
            }
        }

    }
}