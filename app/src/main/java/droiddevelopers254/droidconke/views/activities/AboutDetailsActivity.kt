package droiddevelopers254.droidconke.views.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.Toast

import com.google.firebase.database.DatabaseError

import java.util.ArrayList

import droiddevelopers254.droidconke.R
import droiddevelopers254.droidconke.adapters.AboutDetailsAdapter
import droiddevelopers254.droidconke.models.AboutDetailsModel
import droiddevelopers254.droidconke.viewmodels.AboutDetailsViewModel
import kotlinx.android.synthetic.main.content_about_details.*

class AboutDetailsActivity : AppCompatActivity() {
    internal var aboutDetailsList: List<AboutDetailsModel> = ArrayList()
    lateinit var aboutDetailsViewModel: AboutDetailsViewModel
    lateinit var aboutType: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_details)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        aboutDetailsViewModel = ViewModelProviders.of(this).get(AboutDetailsViewModel::class.java)

        //get intent extras
        val extraIntent = intent
        aboutType = extraIntent.getStringExtra("aboutType")

        if (aboutType == "about_droidconKE") {
            supportActionBar?.title = "About droidconKE"
        }
        if (aboutType == "organizers") {
            supportActionBar?.title = "Organizers"
        }
        if (aboutType == "sponsors") {
            supportActionBar?.title = "Sponsors"
        }
        //fetch about details
        fetchAboutDetails(aboutType)

        //observe live data emitted by view model
        aboutDetailsViewModel.aboutDetails.observe(this, Observer{
            if (it?.databaseError != null) {
                handleDatabaseError(it.databaseError)
            } else {
                handleFetchAboutDetails(it?.aboutDetailsModelList)
            }
        })
    }
    private fun fetchAboutDetails(aboutType: String) {
        aboutDetailsViewModel.fetchAboutDetails(aboutType)
    }

    private fun handleFetchAboutDetails(aboutDetailsModelList: List<AboutDetailsModel>?) {
        if (aboutDetailsModelList != null) {
            aboutDetailsList = aboutDetailsModelList
            initView()
        }
    }

    private fun handleDatabaseError(databaseError: String?) {
        Toast.makeText(applicationContext, databaseError, Toast.LENGTH_SHORT).show()
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
