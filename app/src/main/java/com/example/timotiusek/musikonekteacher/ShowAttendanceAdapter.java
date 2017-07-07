package com.example.timotiusek.musikonekteacher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.timotiusek.musikonekteacher.CustomClass.Attendance;

import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by TimotiusEk on 5/6/2017.
 */

public class ShowAttendanceAdapter extends BaseAdapter {
    private ArrayList<Attendance> attendances;
    private Context mContext;
    private LayoutInflater inflater;
    @BindView(R.id.student_image_show_attendance_page)
    CircleImageView studentImg;
    @BindView(R.id.student_name_show_attendance_page)
    TextView studentName;
    @BindView(R.id.course_name_show_attendance_page)
    TextView courseName;
    @BindView(R.id.which_attendance_show_atttendance_page)
    TextView whichAttendance;

    public ShowAttendanceAdapter(ArrayList<Attendance> attendances, Context c) {
        this.attendances = attendances;
        mContext = c;
    }

    @Override
    public int getCount() {
        return attendances.size();
    }

    @Override
    public Object getItem(int position) {
        return attendances.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Attendance attendance = (Attendance) getItem(position);
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            convertView = inflater.inflate(R.layout.row_layout_show_attendance, parent, false);
            ButterKnife.bind(this, convertView);

            studentImg.setImageResource(attendance.getStudentImage());
            studentName.setText(attendance.getStudentName());
            courseName.setText(attendance.getCourseName());
            whichAttendance.setText(attendance.getWhichAttendance());
        }
        return convertView;
    }
}
