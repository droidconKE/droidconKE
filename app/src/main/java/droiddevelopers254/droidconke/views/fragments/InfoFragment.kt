package droiddevelopers254.droidconke.views.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import droiddevelopers254.droidconke.R
import kotlinx.android.synthetic.main.fragment_schedule.view.*
import java.util.*

class InfoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_info, container, false)
        activity?.title = "Info"

        val viewPager = view.viewpager
        setupViewPager(viewPager)

        val tabLayout = view.tabs
        tabLayout.setupWithViewPager(viewPager)

        return view

    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(EventFragment(), "Event")
        adapter.addFragment(TravelFragment(), "Travel")
        adapter.addFragment(AboutFragment(), "About")
        adapter.addFragment(SettingsFragment(), "Settings")
        viewPager.adapter = adapter

    }

     inner class ViewPagerAdapter(manager: FragmentManager) : android.support.v4.app.FragmentPagerAdapter(manager) {
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
