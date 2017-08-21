package com.example.timotiusek.musikonekteacher;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.timotiusek.musikonekteacher.CustomClass.Student;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class StudentDetailFragment extends Fragment {
    @BindView(R.id.tab_layout__student_detail_fra)
    TabLayout tabLayout;
    @BindView(R.id.view_pager__student_detail_fra)
    ViewPager viewPager;
    StudentListActivity ma;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    private Student student;

    public StudentDetailFragment() {
        // Required empty public constructor
    }

    public static StudentDetailFragment newInstance(Student student){

        StudentDetailFragment sdf = new StudentDetailFragment();
        sdf.setStudent(student);

        return sdf;


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_detail, container, false);
        ButterKnife.bind(this, view);

        ma = (StudentListActivity) getActivity();
        ma.getSupportActionBar().setTitle(student.getStudentName());
//        ma.clearCheckedItems();
        /**
         * todo : tentuin set checknya
         */


        viewPager.setAdapter(new StudentDetailFragment.MyAdapter(getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

        viewPager.setCurrentItem(0);
        // Inflate the layout for this fragment
        return view;
    }

    class MyAdapter extends android.support.v4.app.FragmentStatePagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            switch (position){
                case 0 : return ShowAttendanceFragment.newInstance(student);
                case 1 : return new ShortTestimonialFragment();
                case 2 :  return StudentDetailScheduleFragment.newInstance(student);
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
