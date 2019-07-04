package droiddevelopers254.droidconke.ui.agenda

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import droiddevelopers254.droidconke.R
import droiddevelopers254.droidconke.models.AgendaModel
import droiddevelopers254.droidconke.utils.nonNull
import droiddevelopers254.droidconke.utils.observe
import droiddevelopers254.droidconke.viewmodels.AgendaViewModel
import kotlinx.android.synthetic.main.fragment_agenda.*
import org.jetbrains.anko.toast
import org.koin.android.ext.android.inject
import java.util.*

class AgendaFragment : Fragment() {
    private var agendaModelList: List<AgendaModel> = ArrayList()
    private val agendaViewModel: AgendaViewModel by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_agenda, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //fetch agendas
        agendaViewModel.fetchAgendas()
        //observe live data emitted by view model
        observeLiveData()
    }

    private fun observeLiveData() {
        agendaViewModel.getAgendasResponse().nonNull().observe(this) {
            handleAgendaResponse(it, agendaRv)
        }
        agendaViewModel.getAgendaError().nonNull().observe(this) {
            handleDatabaseError(it)
        }
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
        agendaRv.adapter = agendaAdapter
    }

}
