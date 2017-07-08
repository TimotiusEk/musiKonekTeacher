package com.example.timotiusek.musikonekteacher;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class WeeklyScheduleFragment extends Fragment {
    @BindView(R.id.tab_layout_weekly_schedule_page)
    TabLayout tabLayout;
    @BindView(R.id.view_pager_weekly_schedule_page)
    ViewPager viewPager;
    MainActivity ma;

    public WeeklyScheduleFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weekly_schedule, container, false);
        ButterKnife.bind(this, view);

        ma = (MainActivity) getActivity();
        ma.getSupportActionBar().setTitle("Jadwal");
        ma.setChecked(R.id.menu_schedule);


        viewPager.setAdapter(new MyAdapter(getActivity().getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

        viewPager.setCurrentItem(0);

        // Inflate the layout for this fragment
        return view;
    }


    class MyAdapter extends FragmentStatePagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            switch (position){
                case 0 : return new ScheduleFragment("Monday");
                case 1 : return new ScheduleFragment("Tuesday");
                case 2 :  return new ScheduleFragment("Wednesday");
                case 3 :  return new ScheduleFragment("Thursday");
                case 4 :  return new ScheduleFragment("Friday");
                case 5 :  return new ScheduleFragment("Saturday");
            }
            return null;
        }

        @Override
        public int getCount() {
            return 6;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0 : return "Senin";
                case 1 : return "Selasa";
                case 2 : return "Rabu";
                case 3 : return "Kamis";
                case 4 : return "Jumat";
                case 5 : return "Sabtu";
            }
            return null;
        }

    }

}

