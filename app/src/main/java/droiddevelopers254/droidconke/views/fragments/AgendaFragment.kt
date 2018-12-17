package droiddevelopers254.droidconke.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import droiddevelopers254.droidconke.R
import droiddevelopers254.droidconke.adapters.AgendaAdapter
import droiddevelopers254.droidconke.models.AgendaModel
import droiddevelopers254.droidconke.viewmodels.AgendaViewModel
import kotlinx.android.synthetic.main.fragment_agenda.view.*
import org.jetbrains.anko.toast
import java.util.*

class AgendaFragment : Fragment() {
    private var agendaModelList: List<AgendaModel> = ArrayList()
    lateinit var agendaViewModel: AgendaViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_agenda, container, false)

        agendaViewModel = ViewModelProviders.of(this).get(AgendaViewModel::class.java)

        val agendaRv = view.agendaRv
        //fetch agendas
        agendaViewModel.fetchAgendas()

        //observe live data emitted by view model
        agendaViewModel.agendas.observe(this, Observer{
            when {
                it?.databaseError != null -> handleDatabaseError(it.databaseError)
                else -> handleAgendaResponse(it?.agendaModelList,agendaRv)
            }
        })
        return view
    }

    private fun handleAgendaResponse(agendaList: List<AgendaModel>?, agendaRv: RecyclerView) {
        when {
            agendaList != null -> {
                agendaModelList = agendaList
                initView(agendaRv)
            }
        }
    }
    private fun handleDatabaseError(databaseError: String) {
      activity?.toast(databaseError)
    }

    private fun initView(agendaRv: RecyclerView) {
        val agendaAdapter = AgendaAdapter(agendaModelList, context!!)
        val layoutManager = LinearLayoutManager(activity)
        agendaRv.layoutManager = layoutManager
        agendaRv.itemAnimator = DefaultItemAnimator()
        agendaRv.adapter = agendaAdapter
    }

}
