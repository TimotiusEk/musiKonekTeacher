package com.example.timotiusek.musikonekteacher;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.widget.ProgressBar;

import com.example.timotiusek.musikonekteacher.CustomClass.Schedule;
import com.example.timotiusek.musikonekteacher.CustomClass.ScheduleController;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeeklyScheduleFragment extends Fragment {

    private static final String TAG = "Jadwal";
    private final ScheduleFragment[] scheduleFragments = {
            new ScheduleFragment(ScheduleController.days[0]),
            new ScheduleFragment(ScheduleController.days[1]),
            new ScheduleFragment(ScheduleController.days[2]),
            new ScheduleFragment(ScheduleController.days[3]),
            new ScheduleFragment(ScheduleController.days[4]),
            new ScheduleFragment(ScheduleController.days[5]),
            new ScheduleFragment(ScheduleController.days[6])};

    @BindView(R.id.tab_layout__weekly_schedule_fra) TabLayout tabLayout;
    @BindView(R.id.view_pager__weekly_schedule_fra) ViewPager viewPager;
    @BindView(R.id.loading_weekly_schedule) ProgressBar loading;

    private class MyAdapter extends android.support.v4.app.FragmentStatePagerAdapter {

        MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return scheduleFragments[position];
        }

        @Override
        public int getCount() {
            return 7;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0 : return ScheduleController.days[0];
                case 1 : return ScheduleController.days[1];
                case 2 : return ScheduleController.days[2];
                case 3 : return ScheduleController.days[3];
                case 4 : return ScheduleController.days[4];
                case 5 : return ScheduleController.days[5];
                case 6 : return ScheduleController.days[6];
            }
            return null;
        }
    }

    public WeeklyScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weekly_schedule, container, false);
        ButterKnife.bind(this, view);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(TAG);

        viewPager.setVisibility(View.GONE);
        tabLayout.setVisibility(View.GONE);
//        JSONObject teacherSchedule = ScheduleController.getSchedule(getContext());
        new ScheduleController().getDataAsync(WeeklyScheduleFragment.this);



        return view;
    }

    public void onDataReady(JSONObject data, JSONObject additionalData) {
        Log.d("DEBUG", "Fetched: " + data.toString());
        for(int i = 0; i < ScheduleController.days.length; i++) {
            scheduleFragments[i].setData(data.optJSONArray(ScheduleController.days[i]));
            scheduleFragments[i].setAdditionalData(additionalData.optJSONArray(ScheduleController.days[i]));
        }

        loading.setVisibility(View.GONE);
        viewPager.setVisibility(View.VISIBLE);
        tabLayout.setVisibility(View.VISIBLE);
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(0);
    }
}

