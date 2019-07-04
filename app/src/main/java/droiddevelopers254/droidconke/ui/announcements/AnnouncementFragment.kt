package droiddevelopers254.droidconke.ui.announcements

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import droiddevelopers254.droidconke.R
import droiddevelopers254.droidconke.models.Announcement
import droiddevelopers254.droidconke.utils.nonNull
import droiddevelopers254.droidconke.utils.observe
import droiddevelopers254.droidconke.viewmodels.AnnouncementViewModel
import kotlinx.android.synthetic.main.fragment_announcements.*
import org.jetbrains.anko.toast
import org.koin.android.ext.android.inject

class AnnouncementFragment : Fragment() {
    private val announcementViewModel: AnnouncementViewModel by inject()
    private var announcementList: List<Announcement> = ArrayList()
    private lateinit var announcementAdapter: AnnouncementsAdapter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_announcements, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        announcementAdapter = AnnouncementsAdapter(announcementList)

        //get announcements
        getAnnouncements()
        //show announcement list
        initView()

        //observe live data
        observeLiveData()
    }

    private fun getAnnouncements() {
        announcementViewModel.getAnnouncements()
    }

    private fun initView() {
        announcementsRv.adapter = announcementAdapter
    }

    private fun observeLiveData() {
        announcementViewModel.getAnnouncementsResponse().nonNull().observe(this) {
            announcementAdapter.setAnnouncements(it)
        }
        announcementViewModel.getAnnouncementError().nonNull().observe(this) {
            handleError(it)

        }

    }

    private fun handleError(databaseError: String?) {
        activity?.toast(databaseError.toString())
    }
}