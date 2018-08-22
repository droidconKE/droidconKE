package droiddevelopers254.droidconke.adapters

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import droiddevelopers254.droidconke.R
import droiddevelopers254.droidconke.models.AgendaModel
import kotlinx.android.synthetic.main.agenda_details.view.*

class AgendaAdapter(private val agendaModelList: List<AgendaModel>, private val context: Context) : RecyclerView.Adapter<AgendaAdapter.MyViewHolder>() {
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val agendaTitleText = itemView.agendaTitleText
        private val agendaTimelineText = itemView.agendaTimelineText
        private val agendaImg =itemView.agendaImg
        private val agendaLinear = itemView.agendaLinear

        fun bindAgendas(agendaModel: AgendaModel){
            with(agendaModel){
                //TODO add logic for changing agenda icon
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
