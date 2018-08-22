package droiddevelopers254.droidconke.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import droiddevelopers254.droidconke.R
import droiddevelopers254.droidconke.models.SpeakersModel
import kotlinx.android.synthetic.main.speaker_details.view.*

class SpeakersAdapter(private val speakersList: List<SpeakersModel>, private val context: Context) : RecyclerView.Adapter<SpeakersAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var speakerNameText = itemView.speakerNameText
        internal var speakerCompanyText = itemView.speakerCompanyText
        internal var speakerImg = itemView.speakerImg

       fun bindSpeakerDetails(speakersModel: SpeakersModel){
           with(speakersModel){
               Glide.with(itemView.context).load(photoUrl)
                       .thumbnail(Glide.with(itemView.context).load(photoUrl))
                       .apply(RequestOptions()
                               .centerCrop()
                               .diskCacheStrategy(DiskCacheStrategy.ALL))
                       .transition(DrawableTransitionOptions()
                               .crossFade())
                       .into(speakerImg)

               speakerNameText.text = name
               speakerCompanyText.text = company

           }
       }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpeakersAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.speaker_details, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SpeakersAdapter.MyViewHolder, position: Int) {
     holder.bindSpeakerDetails(speakersList[position])
    }

    override fun getItemCount(): Int {
        return speakersList.size
    }

}
