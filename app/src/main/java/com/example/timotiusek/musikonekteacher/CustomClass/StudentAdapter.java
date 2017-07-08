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
 * Created by TimotiusEk on 7/8/2017.
 */

public class StudentAdapter extends BaseAdapter{
    private ArrayList<Student> students;
    private Context mContext;
    private LayoutInflater inflater;
    @BindView(R.id.student_image_student_list_page)
    CircleImageView studentImg;
    @BindView(R.id.student_name_student_list_page)
    TextView studentName;
    @BindView(R.id.course_name_student_list_page)
    TextView courseName;
    @BindView(R.id.course_package_student_list_page)
    TextView coursePackage;

    public StudentAdapter(ArrayList<Student> students, Context c) {
        this.students = students;
        mContext = c;
    }

    @Override
    public int getCount() {
        return students.size();
    }

    @Override
    public Object getItem(int position) {
        return students.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Student student = (Student) getItem(position);
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            convertView = inflater.inflate(R.layout.row_layout_student_list, parent, false);
            ButterKnife.bind(this, convertView);

            studentImg.setImageResource(student.getStudentImage());
            studentName.setText(student.getStudentName());
            courseName.setText(student.getCourseName());
            coursePackage.setText(student.getCoursePackage());
        }
        return convertView;
    }
}
