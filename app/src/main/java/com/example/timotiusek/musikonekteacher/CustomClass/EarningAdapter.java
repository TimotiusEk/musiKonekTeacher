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
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by TimotiusEk on 7/7/2017.
 */

public class EarningAdapter extends BaseAdapter {
    private ArrayList<Earning> earnings;
    private Context mContext;
    private LayoutInflater inflater;
    @BindView(R.id.student_image__earning_rl)
    CircleImageView studentImg;
    @BindView(R.id.student_name__earning_rl)
    TextView studentName;
    @BindView(R.id.course_name__earning_rl)
    TextView courseName;
    @BindView(R.id.course_package__earning_rl)
    TextView coursePackage;

    public EarningAdapter(ArrayList<Earning> earnings, Context c) {
        this.earnings = earnings;
        mContext = c;
    }

    @Override
    public int getCount() {
        return earnings.size();
    }

    @Override
    public Object getItem(int position) {
        return earnings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Earning earning = (Earning) getItem(position);
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            convertView = inflater.inflate(R.layout.row_layout_earning, parent, false);
            ButterKnife.bind(this, convertView);

            studentImg.setImageResource(earning.getStudentImage());
            studentName.setText(earning.getStudentName());
            courseName.setText(earning.getCourseName());
            coursePackage.setText(earning.getCoursePackage());
        }
        return convertView;
    }
}
