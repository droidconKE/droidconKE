package droiddevelopers254.droidconke.views.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import droiddevelopers254.droidconke.R
import droiddevelopers254.droidconke.views.activities.AboutDetailsActivity
import kotlinx.android.synthetic.main.fragment_about.view.*

class AboutFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_about, container, false)

        //load about details
        //about type is used to fetch for the specific clicked one

        view.aboutDroidconText.setOnClickListener {
            val aboutDetailsIntent = Intent(activity, AboutDetailsActivity::class.java)
            aboutDetailsIntent.putExtra("aboutType",  "about_droidconKE")
            startActivity(aboutDetailsIntent)
        }
        view.organizersText.setOnClickListener {
            val aboutDetailsIntent = Intent(activity, AboutDetailsActivity::class.java)
            aboutDetailsIntent.putExtra("aboutType", "organizers")
            startActivity(aboutDetailsIntent)
        }
        view.sponsorsText.setOnClickListener {
            val aboutDetailsIntent = Intent(activity, AboutDetailsActivity::class.java)
            aboutDetailsIntent.putExtra("aboutType", "sponsors")
            startActivity(aboutDetailsIntent)
        }

        return view
    }

}
