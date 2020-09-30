package droiddevelopers254.droidconke.ui.about

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import droiddevelopers254.droidconke.R
import droiddevelopers254.droidconke.models.AboutDetailsModel
import droiddevelopers254.droidconke.utils.nonNull
import droiddevelopers254.droidconke.utils.observe
import droiddevelopers254.droidconke.utils.toast
import droiddevelopers254.droidconke.viewmodels.AboutDetailsViewModel
import kotlinx.android.synthetic.main.activity_about_details.*
import kotlinx.android.synthetic.main.content_about_details.*
import org.koin.android.ext.android.inject
import java.util.*

class AboutDetailsActivity : AppCompatActivity() {
  private var aboutDetailsList: List<AboutDetailsModel> = ArrayList()
  private var aboutType: String? = null

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
      "organizers" -> supportActionBar?.title = "Organizers"
      "sponsors" -> supportActionBar?.title = "Sponsors"
    }

    //fetch about details
    fetchAboutDetails(aboutType)

    //observe live data emitted by view model
    observeLiveData()
  }

  private fun observeLiveData() {
    aboutDetailsViewModel.getAboutDetailsResponse().nonNull().observe(this) {
      handleFetchAboutDetails(it)
    }
    aboutDetailsViewModel.getAboutDetailsError().nonNull().observe(this) {
      handleDatabaseError(it)
    }

  }

  private fun fetchAboutDetails(aboutType: String?) {
    aboutType?.let { aboutDetailsViewModel.fetchAboutDetails(it) }
  }

  private fun handleFetchAboutDetails(aboutDetailsModelList: List<AboutDetailsModel>?) {
    when {
      aboutDetailsModelList != null -> {
        aboutDetailsList = aboutDetailsModelList
        initView()
      }
    }
  }

  private fun handleDatabaseError(databaseError: String) {
    toast(databaseError)
  }

  private fun initView() {
    aboutDetailsRv.itemAnimator = DefaultItemAnimator()
    val aboutDetailsAdapter = AboutDetailsAdapter(aboutDetailsList, this) {
      //handle on click
    }
    aboutDetailsRv.adapter = aboutDetailsAdapter
  }


}
