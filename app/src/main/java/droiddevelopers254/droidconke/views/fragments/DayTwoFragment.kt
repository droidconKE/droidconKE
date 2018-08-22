package droiddevelopers254.droidconke.views.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import droiddevelopers254.droidconke.R
import droiddevelopers254.droidconke.adapters.SessionsAdapter
import droiddevelopers254.droidconke.models.SessionsModel
import droiddevelopers254.droidconke.utils.ItemClickListener
import droiddevelopers254.droidconke.viewmodels.DayTwoViewModel
import droiddevelopers254.droidconke.views.activities.SessionViewActivity
import kotlinx.android.synthetic.main.fragment_day_two.*

class DayTwoFragment : Fragment() {
    lateinit var dayTwoViewModel: DayTwoViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_day_two, container, false)

        dayTwoViewModel = ViewModelProviders.of(this).get(DayTwoViewModel::class.java)

        dayTwoViewModel.getDayTwoSessions()
        //observe live data emitted by view model
        dayTwoViewModel.sessions.observe(this, Observer{
            if (it?.sessionsModelList != null) {
                val sessionsModelList = it.sessionsModelList
                initView(sessionsModelList)
            } else {
                handleError(it?.databaseError)
            }
        })
        return view
    }

    private fun handleError(databaseError: String?) {
        Toast.makeText(activity, databaseError, Toast.LENGTH_SHORT).show()
    }

    private fun initView(sessionsModelList: List<SessionsModel>) {
        val sessionsAdapter = SessionsAdapter(activity!!, sessionsModelList, "day_two")
        val layoutManager = LinearLayoutManager(activity)
        sessionsRv.layoutManager = layoutManager
        sessionsRv.itemAnimator = DefaultItemAnimator()
        sessionsRv.adapter = sessionsAdapter
        sessionsRv.addOnItemTouchListener(ItemClickListener(activity, sessionsRv, object : ItemClickListener.ClickListener {
            override fun onClick(view: View, position: Int) {
                val intent = Intent(context, SessionViewActivity::class.java)
                intent.putExtra("sessionId", sessionsModelList[position].id)
                intent.putExtra("dayNumber", "day_two")
                intent.putExtra("starred", sessionsModelList[position].isStarred)
                intent.putIntegerArrayListExtra("speakerId", sessionsModelList[position].speaker_id)
                intent.putExtra("roomId", sessionsModelList[position].room_id)
                startActivity(intent)
            }
            override fun onLongClick(view: View, position: Int) {

            }
        }))
    }

}
