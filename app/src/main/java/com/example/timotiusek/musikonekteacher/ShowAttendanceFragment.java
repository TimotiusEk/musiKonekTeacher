package com.example.timotiusek.musikonekteacher;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.timotiusek.musikonekteacher.CustomClass.Attendance;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowAttendanceFragment extends Fragment {
    @BindView(R.id.show_attendance_list_view)
    ListView showAttendanceListView;
    ArrayList<Attendance> attendances;
    ShowAttendanceAdapter showAttendanceAdapter;

    public ShowAttendanceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_attendance, container, false);
        ButterKnife.bind(this,view);

        attendances = new ArrayList<>();
        attendances.add(new Attendance(R.drawable.avatar, "Kursus Piano Pemula", "Pertemuan 1", "Hendra Sulaeman"));
        attendances.add(new Attendance(R.drawable.avatar, "Kursus Piano Pemula", "Pertemuan 2", "Hendra Sulaeman"));
        attendances.add(new Attendance(R.drawable.avatar, "Kursus Piano Pemula", "Pertemuan 3", "Hendra Sulaeman"));
        attendances.add(new Attendance(R.drawable.avatar, "Kursus Piano Pemula", "Pertemuan 4", "Hendra Sulaeman"));

        showAttendanceAdapter = new ShowAttendanceAdapter(attendances, getActivity());
        showAttendanceListView.setAdapter(showAttendanceAdapter);
        // Inflate the layout for this fragment
        return view;
    }

}
