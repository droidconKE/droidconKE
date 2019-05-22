package droiddevelopers254.droidconke.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import droiddevelopers254.droidconke.HomeActivity
import droiddevelopers254.droidconke.R
import kotlinx.android.synthetic.main.fragment_schedule.view.*
import java.util.*

class ScheduleFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_schedule, container, false)
        activity?.title = "Schedule"

        val viewPager = view.viewpager
        val tabLayout = view.tabs

        setupViewPager(viewPager)
        tabLayout.setupWithViewPager(viewPager)

        val currentItem = viewPager.currentItem
        when (currentItem) {
            2 -> {
                HomeActivity.fabVisible = false
            }
        }
        return view
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(DayOneFragment(), getString(R.string.day_one_label))
        adapter.addFragment(DayTwoFragment(), getString(R.string.day_two_label))
        adapter.addFragment(AgendaFragment(), "Agenda")
        viewPager.adapter = adapter
    }

    inner class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
        private val mFragmentList = ArrayList<Fragment>()
        private val mFragmentTitleList = ArrayList<String>()

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitleList[position]
        }
    }


}
