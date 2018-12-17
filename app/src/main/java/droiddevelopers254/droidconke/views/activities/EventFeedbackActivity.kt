package droiddevelopers254.droidconke.views.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import butterknife.ButterKnife
import droiddevelopers254.droidconke.R
import droiddevelopers254.droidconke.models.UserEventFeedback
import droiddevelopers254.droidconke.viewmodels.FeedBackViewModel
import kotlinx.android.synthetic.main.activity_event_feedback.*
import kotlinx.android.synthetic.main.content_event_feedback.*
import org.jetbrains.anko.toast


class EventFeedbackActivity : AppCompatActivity() {
    lateinit var feedBackViewModel : FeedBackViewModel
    private var eventFeedback : String = ""
    lateinit var userEventFeedback : UserEventFeedback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_feedback)
        ButterKnife.bind(this)
        setSupportActionBar(toolbar)

        feedBackViewModel = ViewModelProviders.of(this).get(FeedBackViewModel::class.java)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            supportActionBar?.setTitle("Event Feedback")
        }

        //observe live data emitted by view model
        feedBackViewModel.getEventFeedBackResponse().observe(this, Observer{
            if (it.responseString != null) {
                handleFeedbackResponse(it.responseString)
            }
        })

        fab.setOnClickListener {
            when {
                isFeedbackValid() -> postUserFeedback(userEventFeedback)
            }
        }

    }

    private fun handleFeedbackResponse(feedback: String) {
        progressBarEventFeedBack.setVisibility(View.GONE)
        txtEventFeedback.setText("")
        toast("Thank you for your feedback")

    }

    private fun isFeedbackValid(): Boolean {

        eventFeedback = txtEventFeedback.getText().toString().trim()
        val isValid: Boolean

        when {
            eventFeedback.isEmpty() -> {
                txtEventFeedback.setError("Event feedback cannot be empty")
                isValid = false
            }
            else -> {

                isValid = true
                txtEventFeedback.error = null

                userEventFeedback = UserEventFeedback(eventFeedback)

            }
        }
        return isValid
    }

    private fun postUserFeedback(userEventFeedback: UserEventFeedback) {

        progressBarEventFeedBack.setVisibility(View.VISIBLE)
        feedBackViewModel.sendEventFeedBack(userEventFeedback)

    }
}