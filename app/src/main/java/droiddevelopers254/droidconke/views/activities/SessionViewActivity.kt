package droiddevelopers254.droidconke.views.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import droiddevelopers254.droidconke.R
import droiddevelopers254.droidconke.adapters.SpeakersAdapter
import droiddevelopers254.droidconke.models.RoomModel
import droiddevelopers254.droidconke.models.SessionsModel
import droiddevelopers254.droidconke.models.SpeakersModel
import droiddevelopers254.droidconke.models.StarredSessionModel
import droiddevelopers254.droidconke.utils.SharedPref.PREF_NAME
import droiddevelopers254.droidconke.viewmodels.SessionDataViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_session_view.*
import kotlinx.android.synthetic.main.content_session_view.*
import kotlinx.android.synthetic.main.room_bottom_sheet.*
import java.util.*

class SessionViewActivity : AppCompatActivity() {
    internal var sessionId: Int = 0
    internal var roomId: Int = 0


    lateinit var sessionDataViewModel: SessionDataViewModel
    private var bottomSheetBehavior: BottomSheetBehavior<*>? = null
    lateinit var starStatus: String
    lateinit var dayNumber: String
    internal var documentId: String? = null
    lateinit var sessionsModel1: SessionsModel
    private var databaseReference: DatabaseReference? = null
    internal var speakersList: List<SpeakersModel> = ArrayList()
    internal var speakerId: List<Int> = ArrayList()
    internal var starred = false
    private val compositeDisposable = CompositeDisposable()
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

        sessionDataViewModel = ViewModelProviders.of(this).get(SessionDataViewModel::class.java)

        getSessionData(dayNumber, sessionId)

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView)
        bottomSheetBehavior!!.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                // this part hides the button immediately and waits bottom sheet
                // to collapse to show
                if (BottomSheetBehavior.STATE_EXPANDED == newState) {
                    fab.animate().scaleX(0f).scaleY(0f).setDuration(200).start()
                } else if (BottomSheetBehavior.STATE_COLLAPSED == newState) {
                    fab.animate().scaleX(1f).scaleY(1f).setDuration(200).start()
                }
            }
            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }
        })

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        for (i in speakerId) {
            getSpeakerDetails(i)
        }

        getRoomDetails(roomId)
        //observe live data emitted by view model
        sessionDataViewModel.sessionData.observe(this, Observer{
            if (it?.databaseError != null) {
                handleDatabaseError(it.databaseError)
            } else {
                this.handleFetchSessionData(it?.sessionsModel)
            }
        })
        sessionDataViewModel.speakerInfo.observe(this, Observer{
            if (it?.databaseError != null) {
                handleDatabaseError(it.databaseError)
            } else {
                handleFetchSpeakerDetails(it?.speakerModelList)
            }
        })

        sessionDataViewModel.roomInfo.observe(this, Observer{
            if (it?.databaseError != null) {
                handleDatabaseError(it.databaseError)
            } else {
                handleFetchRoomDetails(it?.roomModel)
            }
        })
        sessionDataViewModel.starSessionResponse().observe(this, Observer{
            if (it?.databaseError != null) {
                handleStarResponse(it.databaseError)
            }
        })
        sessionDataViewModel.unstarSessionResponse().observe(this, Observer{
            if (it?.databaseError != null) {
                handleStarResponse(it.databaseError)
            }
        })
        sessionDataViewModel.dbStarStatus.observe(this, Observer{
            if (it != null) {
                if (it > 0) {
                    isStarred = "1"
                    fab!!.setImageResource(R.drawable.ic_star_blue_24dp)
                } else {
                    isStarred = "0"
                    fab!!.setImageResource(R.drawable.ic_star_border_black_24dp)
                }
            }

        })
        bottomAppBar.replaceMenu(R.menu.menu_bottom_appbar)

        //handle menu items on material bottom bar
        bottomAppBar.setOnMenuItemClickListener { item ->
            val id = item.itemId
            if (id == R.id.action_share) {
                val shareSession = Intent()
                shareSession.action = Intent.ACTION_SEND
                shareSession.putExtra(Intent.EXTRA_TEXT, "Check out " + "'" + sessionId + "' at " + getString(R.string.droidcoke_hashtag) + "\n" + getString(R.string.droidconke_site))
                shareSession.type = "text/plain"
                startActivity(shareSession)
            }
            if (id == R.id.action_map) {

                if (bottomSheetBehavior!!.state != BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior!!.setState(BottomSheetBehavior.STATE_EXPANDED)
                } else {
                    bottomSheetBehavior!!.setState(BottomSheetBehavior.STATE_COLLAPSED)

                }
            }
            false
        }
        //star a session
        fab!!.setOnClickListener {
            if (isStarred == "0") {
                isStarred = "1"
                sessionDataViewModel.starrSessionInDb(sessionId, isStarred, dayNumber)
                Log.d("test", sessionId.toString() + isStarred + dayNumber)

                Toast.makeText(applicationContext, getString(R.string.starred_desc), Toast.LENGTH_SHORT).show()

                fab!!.setImageResource(R.drawable.ic_star_blue_24dp)
                val starredSessionModel= StarredSessionModel(
                        sessionsModel1.documentId,
                        dayNumber,
                        sessionsModel1.id.toString(),
                        FirebaseAuth.getInstance().currentUser!!.uid,
                        true
                )
                //star session in starred_sessions collection
                //this will aid in tracking every starred session and then send a push notification
                sessionDataViewModel.starSession(starredSessionModel, FirebaseAuth.getInstance().currentUser!!.uid)

            } else if (isStarred == "1") {
                isStarred = "0"
                Toast.makeText(applicationContext, getString(R.string.unstarred_desc), Toast.LENGTH_SHORT).show()

                //star a session
                fab!!.setImageResource(R.drawable.ic_star_border_black_24dp)
                sessionDataViewModel.unstarrSessionInDb(sessionId, isStarred, dayNumber)
                Log.d("test", sessionId.toString() + isStarred + dayNumber)
                //unstar session in starred_sessions collection
                sessionDataViewModel.unStarSession(sessionsModel1.documentId, FirebaseAuth.getInstance().currentUser!!.uid, false)

            }
        }
        //collapse bottom bar
        collapseBottomImg?.setOnClickListener {
            if (bottomSheetBehavior?.state == BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }

    }

    private fun getSessionData(dayNumber: String, sessionId: Int) {
        sessionDataViewModel.getSessionDetails(dayNumber, sessionId)
    }

    private fun handleStarUserSession(starMessage: Int) {
        Toast.makeText(applicationContext, getString(starMessage), Toast.LENGTH_SHORT).show()
    }

    private fun handleStarResponse(error: String) {
        Toast.makeText(applicationContext, error, Toast.LENGTH_SHORT).show()
    }

    private fun getRoomDetails(roomId: Int) {
        sessionDataViewModel.fetchRoomDetails(roomId)
    }

    private fun handleFetchRoomDetails(roomModel: RoomModel?) {

        roomDetailsText.text = roomModel?.name + "Room capacity is:  ${roomModel?.capacity.toString()}"
    }

    private fun getSpeakerDetails(speakerId: Int) {
        sessionDataViewModel.fetchSpeakerDetails(speakerId)
    }

    private fun handleFetchSpeakerDetails(speakersModel: List<SpeakersModel>?) {
        if (speakersModel != null) {
            speakersList = speakersModel
            initView()
        } else {
            //if there are no speakers for this session hide views
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

    private fun handleFetchSessionData(sessionsModel: SessionsModel?) {
        if (sessionsModel != null) {
            sessionsModel1 = sessionsModel
            //check star status
            sessionDataViewModel.isSessionStarredInDb(sessionId, dayNumber)

            //set the data on the view
            txtSessionTime.text = sessionsModel.time
            txtSessionRoom.text = sessionsModel.room
            txtSessionDesc.text = sessionsModel.description
            txtSessionCategory.text = sessionsModel.topic
            sessionViewTitleText.text = sessionsModel.title

        }
    }

    private fun handleDatabaseError(databaseError: String?) {
        Toast.makeText(applicationContext, databaseError, Toast.LENGTH_SHORT).show()
    }

}
