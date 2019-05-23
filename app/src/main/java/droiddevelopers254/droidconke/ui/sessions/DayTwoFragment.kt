package droiddevelopers254.droidconke.ui.sessions

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
import kotlinx.android.synthetic.main.fragment_day_two.view.*
import org.jetbrains.anko.toast
import org.koin.android.ext.android.inject
import java.util.*

class DayTwoFragment : Fragment() {
    lateinit var sessionsAdapter: SessionsAdapter
    internal var sessionsModelList: List<SessionsModel> = ArrayList()
    private val dayTwoViewModel: DayTwoViewModel by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_day_two, container, false)

        sessionsAdapter= SessionsAdapter(activity!!, sessionsModelList, "day_two")
        val sessionsRv = view.sessionsRv
        initView(sessionsRv)


        dayTwoViewModel.getDayTwoSessions()
        //observe live data emitted by view model
        observerLiveData()
        return view
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
        val layoutManager = LinearLayoutManager(activity)
        sessionsRv.layoutManager = layoutManager
        sessionsRv.itemAnimator = DefaultItemAnimator()
        sessionsRv.adapter = sessionsAdapter
        sessionsRv.addOnItemTouchListener(ItemClickListener(context!!, sessionsRv, object : ItemClickListener.ClickListener {
            override fun onClick(view: View, position: Int) {
                val intent = Intent(context, SessionViewActivity::class.java)
                intent.putExtra("sessionId", sessionsModelList[position].id)
                intent.putExtra("dayNumber", "day_two")
                intent.putExtra("starred", sessionsModelList[position].starred)
                intent.putIntegerArrayListExtra("speakerId", sessionsModelList[position].speaker_id)
                intent.putExtra("roomId", sessionsModelList[position].room_id)
                startActivity(intent)
            }

            override fun onLongClick(view: View?, position: Int) {
            }
        }))
    }

}
