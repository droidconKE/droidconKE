package droiddevelopers254.droidconke.views.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import droiddevelopers254.droidconke.R
import droiddevelopers254.droidconke.adapters.SessionsAdapter
import droiddevelopers254.droidconke.models.SessionTimeModel
import droiddevelopers254.droidconke.models.SessionsModel
import droiddevelopers254.droidconke.utils.ItemClickListener
import droiddevelopers254.droidconke.viewmodels.DayOneViewModel
import droiddevelopers254.droidconke.views.activities.SessionViewActivity
import kotlinx.android.synthetic.main.fragment_day_one.*
import kotlinx.android.synthetic.main.fragment_day_one.view.*
import org.jetbrains.anko.toast
import org.koin.android.ext.android.inject
import java.util.*

class DayOneFragment : Fragment() {

    internal var sessionsModelList: List<SessionsModel> = ArrayList()
    internal var sessionTimeModelList: List<SessionTimeModel> = ArrayList()
    internal var sessionIds: List<String> = ArrayList()
    internal var isStarred: Boolean = false
    lateinit var sessionsAdapter: SessionsAdapter

    private val dayOneViewModel: DayOneViewModel by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_day_one, container, false)

        sessionsAdapter = SessionsAdapter(activity!!, sessionsModelList, "day_one")
        val sessionsRv = view.sessionsRv

        initView(sessionsRv)

        dayOneViewModel.getDayOneSessions()
        //observe live data emitted by view model
        dayOneViewModel.sessions.observe(this, Observer {
            when {
                it?.sessionsModelList != null -> {
                    sessionsModelList = it.sessionsModelList
                    sessionsAdapter.setSessionsAdapter(sessionsModelList)
                    loginProgressBar.visibility = View.GONE
                }
                else -> {
                    loginProgressBar.visibility = View.GONE
                    handleError(it?.databaseError)
                }
            }
        })

        return view
    }

    private fun handleError(databaseError: String?) {
        activity?.toast(databaseError.toString())
    }

    private fun initView(sessionsRv: RecyclerView) {
        val layoutManager = LinearLayoutManager(activity)
        sessionsRv.layoutManager = layoutManager
        sessionsRv.itemAnimator = DefaultItemAnimator()
        sessionsRv.adapter = sessionsAdapter
        sessionsRv.addOnItemTouchListener(ItemClickListener(context!!, sessionsRv, object : ItemClickListener.ClickListener {

            override fun onLongClick(view: View?, position: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onClick(view: View, position: Int) {
                val intent = Intent(context, SessionViewActivity::class.java)
                intent.putExtra("sessionId", sessionsModelList[position].id)
                intent.putExtra("dayNumber", "day_one")
                intent.putExtra("starred", sessionsModelList[position].starred)
                intent.putIntegerArrayListExtra("speakerId", sessionsModelList[position].speaker_id)
                intent.putExtra("roomId", sessionsModelList[position].room_id)
                startActivity(intent)
            }


        }))

    }

}
