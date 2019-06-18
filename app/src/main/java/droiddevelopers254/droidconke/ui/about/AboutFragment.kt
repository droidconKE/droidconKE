package droiddevelopers254.droidconke.ui.about

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import droiddevelopers254.droidconke.R
import kotlinx.android.synthetic.main.fragment_about.*

class AboutFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //load about details
        //about type is used to fetch for the specific clicked one
        aboutDroidconText.setOnClickListener {
            val aboutDetailsIntent = Intent(activity, AboutDetailsActivity::class.java)
            aboutDetailsIntent.putExtra("aboutType", "about_droidconKE")
            startActivity(aboutDetailsIntent)
        }
        organizersText.setOnClickListener {
            val aboutDetailsIntent = Intent(activity, AboutDetailsActivity::class.java)
            aboutDetailsIntent.putExtra("aboutType", "organizers")
            startActivity(aboutDetailsIntent)
        }
        sponsorsText.setOnClickListener {
            val aboutDetailsIntent = Intent(activity, AboutDetailsActivity::class.java)
            aboutDetailsIntent.putExtra("aboutType", "sponsors")
            startActivity(aboutDetailsIntent)
        }
    }
}
