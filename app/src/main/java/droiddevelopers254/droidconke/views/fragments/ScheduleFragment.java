package droiddevelopers254.droidconke.views.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import droiddevelopers254.droidconke.HomeActivity;
import droiddevelopers254.droidconke.R;

public class ScheduleFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        getActivity().setTitle("Schedule");

        viewPager = view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout =view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        int currentItem = viewPager.getCurrentItem();
        if (currentItem ==2){
            HomeActivity.fabVisible=false;
            Toast.makeText(getActivity(),"true",Toast.LENGTH_SHORT).show();
        }
        return view;
    }
     private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new DayOneFragment(),getString(R.string.day_one_label));
        adapter.addFragment(new DayTwoFragment(),getString(R.string.day_two_label));
        adapter.addFragment(new AgendaFragment(),"Agenda");
        viewPager.setAdapter(adapter);
    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}
