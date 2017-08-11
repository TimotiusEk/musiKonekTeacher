package com.example.timotiusek.musikonekteacher.CustomClass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.timotiusek.musikonekteacher.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by TimotiusEk on 8/3/2017.
 */

public class ReportAdapter extends BaseAdapter{
    private ArrayList<Report> reports;
    private Context mContext;
    private LayoutInflater inflater;
    @BindView(R.id.report_title__show_report_rl)
    TextView reportTitle;
    @BindView(R.id.report_date__show_report_rl)
    TextView reportDate;

    public ReportAdapter(ArrayList<Report> reports, Context c) {
        this.reports = reports;
        mContext = c;
    }

    @Override
    public int getCount() {
        return reports.size();
    }

    @Override
    public Object getItem(int position) {
        return reports.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Report report = (Report) getItem(position);
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            convertView = inflater.inflate(R.layout.row_layout_report, parent, false);
            ButterKnife.bind(this, convertView);

            reportTitle.setText(report.getTitle());
            reportDate.setText(report.getDate());
        }
        return convertView;
    }
}
