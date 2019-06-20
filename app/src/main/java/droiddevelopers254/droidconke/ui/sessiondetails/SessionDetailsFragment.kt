package droiddevelopers254.droidconke.ui.sessiondetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import droiddevelopers254.droidconke.R

class SessionDetailsFragment : Fragment() {


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_session_details, container, false)
    }

}