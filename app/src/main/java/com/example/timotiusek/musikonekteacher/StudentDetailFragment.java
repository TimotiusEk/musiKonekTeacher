package com.example.timotiusek.musikonekteacher;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class StudentDetailFragment extends Fragment {
    @BindView(R.id.tab_layout_student_detail_page)
    TabLayout tabLayout;
    @BindView(R.id.view_pager_student_detail_page)
    ViewPager viewPager;
    MainActivity ma;

    public StudentDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_detail, container, false);
        ButterKnife.bind(this, view);

        ma = (MainActivity) getActivity();
        ma.setTitle("Student Detail");
        ma.setChecked(R.id.menu_attendance);
        /**
         * todo : tentuin set checknya
         */


        viewPager.setAdapter(new StudentDetailFragment.MyAdapter(getActivity().getSupportFragmentManager()));
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
                case 0 : return new ShowAttendanceFragment();
                case 1 : return new ShortTestimonialFragment();
                case 2 :  return new ScheduleFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0 : return "Attendance";
                case 1 : return "Testimonial";
                case 2 : return "Schedule";
            }
            return null;
        }

    }

}
