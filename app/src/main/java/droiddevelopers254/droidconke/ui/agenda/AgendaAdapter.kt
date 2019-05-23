package droiddevelopers254.droidconke.ui.agenda

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import droiddevelopers254.droidconke.R
import droiddevelopers254.droidconke.models.AgendaModel
import kotlinx.android.synthetic.main.agenda_details.view.*

class AgendaAdapter(private val agendaModelList: List<AgendaModel>, private val context: Context) : RecyclerView.Adapter<AgendaAdapter.MyViewHolder>() {
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val agendaTitleText = itemView.agendaTitleText
        private val agendaTimelineText = itemView.agendaTimelineText
        private val agendaImg = itemView.agendaImg
        private val agendaLinear = itemView.agendaLinear

        @SuppressLint("Range")
        fun bindAgendas(agendaModel: AgendaModel) {
            with(agendaModel) {
                Glide.with(itemView.context).load(iconUrl)
                        .thumbnail(Glide.with(itemView.context).load(iconUrl))
                        .apply(RequestOptions()
                                .diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(agendaImg)
                agendaTitleText.text = title
                agendaTimelineText.text = time
                agendaLinear.setBackgroundColor(Color.parseColor(background_color))
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.agenda_details, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindAgendas(agendaModelList[position])
    }

    override fun getItemCount(): Int {
        return agendaModelList.size
    }


}
