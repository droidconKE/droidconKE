package droiddevelopers254.droidconke.views.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import droiddevelopers254.droidconke.R
import droiddevelopers254.droidconke.adapters.AboutDetailsAdapter
import droiddevelopers254.droidconke.models.AboutDetailsModel
import droiddevelopers254.droidconke.viewmodels.AboutDetailsViewModel
import kotlinx.android.synthetic.main.activity_about_details.*
import kotlinx.android.synthetic.main.content_about_details.*
import org.jetbrains.anko.toast
import java.util.ArrayList

class AboutDetailsActivity : AppCompatActivity() {
    private var aboutDetailsList: List<AboutDetailsModel> = ArrayList()
    lateinit var aboutDetailsViewModel: AboutDetailsViewModel
    lateinit var aboutType: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_details)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        aboutDetailsViewModel = ViewModelProviders.of(this).get(AboutDetailsViewModel::class.java)

        //get intent extras
        val extraIntent = intent
        aboutType = extraIntent.getStringExtra("aboutType")

        when (aboutType) {
            "about_droidconKE" -> supportActionBar?.title = "About droidconKE"
            "organizers" ->supportActionBar?.title = "Organizers"
            "sponsors" ->  supportActionBar?.title = "Sponsors"
        }

        //fetch about details
        fetchAboutDetails(aboutType)

        //observe live data emitted by view model
        aboutDetailsViewModel.aboutDetails.observe(this, Observer{
            when {
                it?.databaseError != null -> handleDatabaseError(it.databaseError)
                else -> handleFetchAboutDetails(it?.aboutDetailsModelList)
            }
        })
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
        toast(databaseError.toString())
    }

    private fun initView() {
        val aboutDetailsAdapter = AboutDetailsAdapter(aboutDetailsList, applicationContext){
           //handle on click
        }
        val layoutManager = LinearLayoutManager(this)
        aboutDetailsRv.layoutManager = layoutManager
        aboutDetailsRv.itemAnimator = DefaultItemAnimator()
        aboutDetailsRv.adapter = aboutDetailsAdapter
    }



}
