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

public class ScheduleAdapter extends BaseAdapter{
    private ArrayList<Schedule> schedules;
    private Context mContext;
    private LayoutInflater inflater;
    @BindView(R.id.which_meeting__schedule_rl)
    TextView whichMeeting;
    @BindView(R.id.meeting_date__schedule_rl)
    TextView dateMeeting;

    public ScheduleAdapter(ArrayList<Schedule> schedules, Context c) {
        this.schedules = schedules;
        mContext = c;
    }

    @Override
    public int getCount() {
        return schedules.size();
    }

    @Override
    public Object getItem(int position) {
        return schedules.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Schedule schedule = (Schedule) getItem(position);
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            convertView = inflater.inflate(R.layout.row_layout_schedule, parent, false);
            ButterKnife.bind(this, convertView);

            whichMeeting.setText(schedule.getWhichMeeting());
            dateMeeting.setText(schedule.getDate());
        }
        return convertView;
    }
}
