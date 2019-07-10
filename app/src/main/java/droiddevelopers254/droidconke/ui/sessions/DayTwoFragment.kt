package droiddevelopers254.droidconke.ui.sessions

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import droiddevelopers254.droidconke.R
import droiddevelopers254.droidconke.models.SessionsModel
import droiddevelopers254.droidconke.utils.ItemClickListener
import droiddevelopers254.droidconke.utils.nonNull
import droiddevelopers254.droidconke.utils.observe
import droiddevelopers254.droidconke.viewmodels.DayTwoViewModel
import droiddevelopers254.droidconke.ui.sessiondetails.SessionViewActivity
import kotlinx.android.synthetic.main.fragment_day_two.*
import org.jetbrains.anko.toast
import org.koin.android.ext.android.inject
import java.util.*

class DayTwoFragment : Fragment() {
    lateinit var sessionsAdapter: SessionsAdapter
    private var sessionsModelList: List<SessionsModel> = ArrayList()
    private val dayTwoViewModel: DayTwoViewModel by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_day_two, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sessionsAdapter = SessionsAdapter(sessionsModelList){
            redirectToSessionDetails()
        }
        initView(sessionsRv)
        dayTwoViewModel.getDayTwoSessions()
        //observe live data emitted by view model
        observerLiveData()
    }

    private fun redirectToSessionDetails() {
    }

    private fun observerLiveData() {
        dayTwoViewModel.getSessionsResponse().nonNull().observe(this) {
            sessionsModelList = it
            sessionsAdapter.setSessionsAdapter(sessionsModelList)
        }
        dayTwoViewModel.getSessionsError().nonNull().observe(this) {
            handleError(it)
        }
    }

    private fun handleError(databaseError: String) {
        activity?.toast(databaseError)
    }

    private fun initView(sessionsRv: RecyclerView) {
        sessionsRv.adapter = sessionsAdapter
    }

}
