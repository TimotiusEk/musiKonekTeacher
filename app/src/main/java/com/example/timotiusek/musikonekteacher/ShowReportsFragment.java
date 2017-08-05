package com.example.timotiusek.musikonekteacher;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.timotiusek.musikonekteacher.CustomClass.Report;
import com.example.timotiusek.musikonekteacher.CustomClass.ReportAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowReportsFragment extends Fragment {
    @BindView(R.id.reports_lv__show_reports_page)
    ListView reportsLv;
    ArrayList<Report> reports;
    ReportAdapter reportAdapter;
    MainActivity ma;

    public ShowReportsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_show_reports, container, false);
        ButterKnife.bind(this, v);
        ma = (MainActivity) getActivity();
        ma.getSupportActionBar().setTitle("Laporan");
        reports = new ArrayList<>();
        reports.add(new Report("Laporan Minggu 1" , "28 April 1935, 12:00 WIB"));
        reports.add(new Report("Laporan Minggu 2" , "8 Mei 1951, 12:39 WIB"));
        reportAdapter = new ReportAdapter(reports, getActivity());
        reportsLv.setAdapter(reportAdapter);
        // Inflate the layout for this fragment
        return v;
    }

}
