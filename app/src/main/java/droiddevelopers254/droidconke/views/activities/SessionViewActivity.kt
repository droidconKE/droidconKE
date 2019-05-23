package droiddevelopers254.droidconke.views.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import droiddevelopers254.droidconke.R
import droiddevelopers254.droidconke.adapters.SpeakersAdapter
import droiddevelopers254.droidconke.models.RoomModel
import droiddevelopers254.droidconke.models.SessionsModel
import droiddevelopers254.droidconke.models.SpeakersModel
import droiddevelopers254.droidconke.utils.SharedPref.PREF_NAME
import droiddevelopers254.droidconke.utils.nonNull
import droiddevelopers254.droidconke.utils.observe
import droiddevelopers254.droidconke.viewmodels.SessionDataViewModel
import kotlinx.android.synthetic.main.activity_session_view.*
import kotlinx.android.synthetic.main.content_session_view.*
import kotlinx.android.synthetic.main.room_bottom_sheet.*
import org.jetbrains.anko.toast
import org.koin.android.ext.android.inject
import java.util.*

class SessionViewActivity : AppCompatActivity() {
    private var sessionId: Int = 0
    private var roomId: Int = 0


    private val sessionDataViewModel: SessionDataViewModel by inject()

    private var bottomSheetBehavior: BottomSheetBehavior<*>? = null
    lateinit var starStatus: String
    lateinit var dayNumber: String
    internal var documentId: String? = null
    lateinit var sessionsModel1: SessionsModel
    private var databaseReference: DatabaseReference? = null
    private var speakersList: List<SpeakersModel> = ArrayList()
    private var speakerId: List<Int> = ArrayList()
    internal var starred = false
    lateinit var sharedPreferences: SharedPreferences
    lateinit var isStarred: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_session_view)

        databaseReference = FirebaseDatabase.getInstance().reference
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        //get extras
        val extraIntent = intent
        sessionId = extraIntent.getIntExtra("sessionId", 0)
        dayNumber = extraIntent.getStringExtra("dayNumber")
        starStatus = extraIntent.getStringExtra("starred")
        speakerId = extraIntent.getIntegerArrayListExtra("speakerId")
        roomId = extraIntent.getIntExtra("roomId", 0)

        getSessionData(dayNumber, sessionId)

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView)
        bottomSheetBehavior!!.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                // this part hides the button immediately and waits bottom sheet
                // to collapse to show
                if (newState == BottomSheetBehavior.STATE_EXPANDED) fab.animate().scaleX(0f).scaleY(0f).setDuration(200).start()
                else if (newState == BottomSheetBehavior.STATE_COLLAPSED) fab.animate().scaleX(1f).scaleY(1f).setDuration(200).start()
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }
        })

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        speakerId.forEach { i ->
            getSpeakerDetails(i)
        }

        getRoomDetails(roomId)
        //observe live data emitted by view model
        observeLiveData()

        bottomAppBar.replaceMenu(R.menu.menu_bottom_appbar)

        //handle menu items on material bottom bar
        bottomAppBar.setOnMenuItemClickListener { item ->
            val id = item.itemId
            when (id) {
                R.id.action_feedback -> {
                    val sessionFeedbackIntent = Intent(this, SessionFeedBackActivity::class.java)
                    sessionFeedbackIntent.putExtra("sessionId", sessionId)
                    sessionFeedbackIntent.putExtra("dayNumber", dayNumber)
                    startActivity(sessionFeedbackIntent)
                }
            }
            when (id) {
                R.id.action_map -> when {
                    bottomSheetBehavior!!.state != BottomSheetBehavior.STATE_EXPANDED -> bottomSheetBehavior!!.setState(BottomSheetBehavior.STATE_EXPANDED)
                    else -> bottomSheetBehavior!!.setState(BottomSheetBehavior.STATE_COLLAPSED)
                }
            }
            false
        }
        //share a session
        fab.setOnClickListener {
            val shareSession = Intent()
            shareSession.action = Intent.ACTION_SEND
            shareSession.putExtra(Intent.EXTRA_TEXT, "Check out " + "'" + sessionId + "' at " + getString(R.string.droidcoke_hashtag) + "\n" + getString(R.string.droidconke_site))
            shareSession.type = "text/plain"
            startActivity(shareSession)
        }
        //collapse bottom bar
        collapseBottomImg?.setOnClickListener {
            when {
                bottomSheetBehavior?.state == BottomSheetBehavior.STATE_EXPANDED -> bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }

    }

    private fun observeLiveData() {
        sessionDataViewModel.getSessionDataResponse().nonNull().observe(this) {
            handleFetchSessionData(it)
        }
        sessionDataViewModel.getSessionDataError().nonNull().observe(this) {
            handleDatabaseError(it)
        }
        sessionDataViewModel.getSpeakerInfoResponse().nonNull().observe(this) {
            handleFetchSpeakerDetails(it)
        }
        sessionDataViewModel.getSpeakerError().nonNull().observe(this) {
            handleDatabaseError(it)
        }
        sessionDataViewModel.getRoomInfoResponse().nonNull().observe(this) {
            handleFetchRoomDetails(it)
        }
        sessionDataViewModel.getRoomInfoError().nonNull().observe(this) {
            handleDatabaseError(it)
        }

    }

    private fun getSessionData(dayNumber: String, sessionId: Int) {
        sessionDataViewModel.getSessionDetails(dayNumber, sessionId)
    }


    private fun getRoomDetails(roomId: Int) {
        sessionDataViewModel.fetchRoomDetails(roomId)
    }

    private fun handleFetchRoomDetails(roomModel: RoomModel?) {
        roomDetailsText.text = roomModel?.name + "Room capacity is :  ${roomModel?.capacity.toString()}"
    }

    private fun getSpeakerDetails(speakerId: Int) {
        sessionDataViewModel.fetchSpeakerDetails(speakerId)
    }

    private fun handleFetchSpeakerDetails(speakersModel: List<SpeakersModel>?) {
        when {
            speakersModel != null -> {
                speakersList = speakersModel
                initView()
            }
            else -> //if there are no speakers for this session hide views
                speakersLinear.visibility = View.GONE
        }
    }

    private fun initView() {
        val speakersAdapter = SpeakersAdapter(speakersList, applicationContext)
        val layoutManager = LinearLayoutManager(this)
        speakersRV.layoutManager = layoutManager
        speakersRV.itemAnimator = DefaultItemAnimator()
        speakersRV.adapter = speakersAdapter
    }

    private fun handleFetchSessionData(sessionsModel: SessionsModel) {

        sessionsModel1 = sessionsModel
        //set the data on the view
        txtSessionTime.text = sessionsModel.time
        txtSessionRoom.text = sessionsModel.room
        txtSessionDesc.text = sessionsModel.description
        txtSessionCategory.text = sessionsModel.topic
        sessionViewTitleText.text = sessionsModel.title

    }

    private fun handleDatabaseError(databaseError: String?) {
        toast(databaseError.toString())
    }

}
