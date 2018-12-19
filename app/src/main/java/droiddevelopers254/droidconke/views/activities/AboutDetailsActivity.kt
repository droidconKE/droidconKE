package droiddevelopers254.droidconke.views.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import droiddevelopers254.droidconke.R
import droiddevelopers254.droidconke.adapters.AboutDetailsAdapter
import droiddevelopers254.droidconke.models.AboutDetailsModel
import droiddevelopers254.droidconke.utils.toast
import droiddevelopers254.droidconke.viewmodels.AboutDetailsViewModel
import kotlinx.android.synthetic.main.activity_about_details.*
import kotlinx.android.synthetic.main.content_about_details.*
import org.koin.android.ext.android.inject
import java.util.*

class AboutDetailsActivity : AppCompatActivity() {
    private var aboutDetailsList: List<AboutDetailsModel> = ArrayList()
    lateinit var aboutType: String

    private val aboutDetailsViewModel: AboutDetailsViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_details)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initView()

        //get intent extras
        val extraIntent = intent
        aboutType = extraIntent.getStringExtra("aboutType")

        when (aboutType) {
            "about_droidconKE" -> supportActionBar?.title = "About droidconKE"
            "organizers" ->supportActionBar?.title = "Organizers"
            "sponsors" ->  supportActionBar?.title = "Sponsors"
        }

        //observe live data emitted by view model
        aboutDetailsViewModel.aboutDetails.observe(this, Observer {
            println(it)
            when {
                it.databaseError != null -> handleDatabaseError(it.databaseError)
                else -> handleFetchAboutDetails(it?.aboutDetailsModelList)
            }
        })

        //fetch about details
        fetchAboutDetails(aboutType)
    }
    private fun fetchAboutDetails(aboutType: String) {
        aboutDetailsViewModel.fetchAboutDetails(aboutType)
    }

    private fun handleFetchAboutDetails(aboutDetailsModelList: List<AboutDetailsModel>?) {
        when {
            aboutDetailsModelList != null -> {
                aboutDetailsList = aboutDetailsModelList
                initView()
            }
        }
    }

    private fun handleDatabaseError(databaseError: String?) {
        this.toast(databaseError.toString())
    }

    private fun initView() {
        aboutDetailsRv.itemAnimator = DefaultItemAnimator()
        val aboutDetailsAdapter = AboutDetailsAdapter(aboutDetailsList, this) {
            //handle on click
        }
        aboutDetailsRv.adapter = aboutDetailsAdapter
    }



}
