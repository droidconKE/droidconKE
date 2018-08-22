package droiddevelopers254.droidconke.views.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import droiddevelopers254.droidconke.R
import droiddevelopers254.droidconke.adapters.AgendaAdapter
import droiddevelopers254.droidconke.models.AgendaModel
import droiddevelopers254.droidconke.viewmodels.AgendaViewModel
import kotlinx.android.synthetic.main.fragment_agenda.*
import java.util.*

class AgendaFragment : Fragment() {
    private var agendaModelList: List<AgendaModel> = ArrayList()
    lateinit var agendaViewModel: AgendaViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_agenda, container, false)

        agendaViewModel = ViewModelProviders.of(this).get(AgendaViewModel::class.java)

        //fetch agendas
        agendaViewModel.fetchAgendas()

        //observe live data emitted by view model
        agendaViewModel.agendas.observe(this, Observer{
            if (it?.databaseError != null) {
                handleDatabaseError(it.databaseError)
            } else {
                handleAgendaResponse(it?.agendaModelList)
            }
        })
        return view
    }

    private fun handleAgendaResponse(agendaList: List<AgendaModel>?) {
        if (agendaList != null) {
            agendaModelList = agendaList
            initView()
        }
    }
    private fun handleDatabaseError(databaseError: String) {
        Toast.makeText(activity, databaseError, Toast.LENGTH_SHORT).show()
    }

    private fun initView() {
        val agendaAdapter = AgendaAdapter(agendaModelList, context!!)
        val layoutManager = LinearLayoutManager(activity)
        agendaRv.layoutManager = layoutManager
        agendaRv.itemAnimator = DefaultItemAnimator()
        agendaRv.adapter = agendaAdapter
    }

}
