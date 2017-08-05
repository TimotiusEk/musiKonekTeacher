package com.example.timotiusek.musikonekteacher;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.timotiusek.musikonekteacher.CustomClass.Schedule;
import com.example.timotiusek.musikonekteacher.CustomClass.ScheduleAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class StudentDetailScheduleFragment extends Fragment {
    @BindView(R.id.schedule_lv__student_detail_schedule_page)
    ListView scheduleLv;
    ArrayList<Schedule> schedules;
    ScheduleAdapter scheduleAdapter;

    public StudentDetailScheduleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_student_detail_schedule, container, false);
        ButterKnife.bind(this, v);

        schedules = new ArrayList<>();
        schedules.add(new Schedule("Pertemuan 1", "12 Desember 1931, 12:12 WIB"));
        schedules.add(new Schedule("Pertemuan 2", "12 Desember 1911, 12:12 WIB"));
        schedules.add(new Schedule("Pertemuan 3", "12 Desember 1968, 12:12 WIB"));
        schedules.add(new Schedule("Pertemuan 4", "12 Desember 1980, 12:12 WIB"));
        schedules.add(new Schedule("Pertemuan 5", "12 Desember 1994, 12:12 WIB"));
        schedules.add(new Schedule("Pertemuan 6", "12 Desember 2010, 12:12 WIB"));

        scheduleAdapter = new ScheduleAdapter(schedules, getActivity());
        scheduleLv.setAdapter(scheduleAdapter);
        // Inflate the layout for this fragment
        return v;
    }

}
