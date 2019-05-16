package droiddevelopers254.droidconke.views.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import butterknife.ButterKnife
import droiddevelopers254.droidconke.R
import droiddevelopers254.droidconke.models.SessionsModel
import droiddevelopers254.droidconke.models.SessionsUserFeedback
import droiddevelopers254.droidconke.viewmodels.SessionDataViewModel
import kotlinx.android.synthetic.main.activity_session_feed_back.*
import kotlinx.android.synthetic.main.content_session_feed_back.*
import org.jetbrains.anko.toast
import org.koin.android.ext.android.inject


class SessionFeedBackActivity : AppCompatActivity() {
    var sessionId: Int = 0
    private var dayNumber :String = ""
    private lateinit var sessionsModel1 : SessionsModel
    private lateinit var userFeedback : SessionsUserFeedback
    private var sessionFeedback : String = ""

    private val sessionDataViewModel: SessionDataViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_session_feed_back)
        ButterKnife.bind(this)
        setSupportActionBar(toolbar)

       supportActionBar?.let {
           with(it) {
               setDisplayHomeAsUpEnabled(true)
               title = "Sessions Feedback"
           }
       }

        //get extras
        val extraIntent = intent
        sessionId = extraIntent.getIntExtra("sessionId", 0)
        dayNumber = extraIntent.getStringExtra("dayNumber")

        getSessionData(dayNumber, sessionId)

        //observe live data emitted by view model
        sessionDataViewModel.sessionData.observe(this, Observer{
            assert(it != null)
            when {
                it.databaseError != null -> handleDatabaseError(it.databaseError)
                else -> this.handleFetchSessionData(it.sessionsModel)
            }
        })
        sessionDataViewModel.getSessionFeedBackResponse().observe(this, Observer{
            handleFeedbackResponse(it.responseString)
        })

        fab.setOnClickListener {
            //get data from user and post them
            when {
                isFeedbackValid() -> postUserFeedback(userFeedback)
            }
        }

    }

    private fun handleFeedbackResponse(@Suppress("UNUSED_PARAMETER") feedback: String) {
        loginProgressBarFeedBack.visibility = View.GONE

        txtSessionUserFeedback.setText("")

        toast("Thank you for your feedback")
    }

    private fun getSessionData(dayNumber: String, sessionId: Int) {
        sessionDataViewModel.getSessionDetails(dayNumber, sessionId)
    }

    private fun handleFetchSessionData(sessionsModel: SessionsModel?) {
        if (sessionsModel != null) {
            sessionsModel1 = sessionsModel
            //set the data on the view
            txtSessionFeedbackTitle.text = sessionsModel.title

        }
    }

    private fun handleDatabaseError(databaseError: String) {
        Toast.makeText(applicationContext, databaseError, Toast.LENGTH_SHORT).show()
    }

    private fun isFeedbackValid(): Boolean {

        sessionFeedback = txtSessionUserFeedback.text.toString().trim()
        val isValid: Boolean

        when {
            sessionFeedback.isEmpty() -> {
                txtSessionUserFeedback.error = "Session feedback cannot be empty"
                isValid = false
            }
            else -> {

                isValid = true
                txtSessionUserFeedback.error = null

                userFeedback = SessionsUserFeedback(
                        user_id = "",
                        session_id = sessionId.toString(),
                        day_number = dayNumber,
                        session_title = sessionsModel1.title,
                        session_feedback = sessionFeedback
                )
            }
        }
        return isValid
    }

    private fun postUserFeedback(userFeedback: SessionsUserFeedback) {

        loginProgressBarFeedBack.visibility = View.VISIBLE
        sessionDataViewModel.sendSessionFeedBack(userFeedback)

    }
}