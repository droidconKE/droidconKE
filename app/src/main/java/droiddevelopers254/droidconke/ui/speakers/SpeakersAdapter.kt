package droiddevelopers254.droidconke.ui.speakers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import droiddevelopers254.droidconke.R
import droiddevelopers254.droidconke.models.SpeakersModel
import kotlinx.android.synthetic.main.speaker_details.view.*

class SpeakersAdapter(private val speakersList: List<SpeakersModel>, private val context: Context) : RecyclerView.Adapter<SpeakersAdapter.SpeakersViewHolder>() {

    inner class SpeakersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var speakerNameText = itemView.speakerNameText
        private var speakerCompanyText = itemView.speakerCompanyText
        private var speakerImg = itemView.speakerImg

        fun bindSpeakerDetails(speakersModel: SpeakersModel) {
            with(speakersModel) {
                Glide.with(itemView.context).load(photoUrl)
                        .thumbnail(Glide.with(itemView.context).load(photoUrl))
                        .apply(RequestOptions()
                                .diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(speakerImg)

                speakerNameText.text = name
                speakerCompanyText.text = company

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpeakersViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.speaker_details, parent, false)
        return SpeakersViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SpeakersViewHolder, position: Int) {
        holder.bindSpeakerDetails(speakersList[position])
    }

    override fun getItemCount(): Int = speakersList.size

}
