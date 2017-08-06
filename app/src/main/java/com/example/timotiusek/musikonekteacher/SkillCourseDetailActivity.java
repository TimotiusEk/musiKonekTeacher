package com.example.timotiusek.musikonekteacher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.timotiusek.musikonekteacher.CustomClass.Course;
import com.example.timotiusek.musikonekteacher.CustomClass.CourseAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SkillCourseDetailActivity extends AppCompatActivity {
    @BindView(R.id.course_related_lv__skill_course_detail_act)
    ListView courseRelatedLv;
    ArrayList<Course> courses;
    CourseAdapter courseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill_course_detail);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle("Daftar Kursus Keahlian");

        courses = new ArrayList<>();
        courses.add(new Course("Kursus Violin Dasar","12 kali pertemuan"));
        courses.add(new Course("Kursus Violin Pop","3 kali pertemuan"));
        courses.add(new Course("Kursus Violin Menengah","4 kali pertemuan"));
        courses.add(new Course("Kursus Violin Dasar","12 kali pertemuan"));
        courses.add(new Course("Kursus Violin Pop","3 kali pertemuan"));
        courses.add(new Course("Kursus Violin Menengah","4 kali pertemuan"));

        courseAdapter = new CourseAdapter(courses, this);
        courseRelatedLv.setAdapter(courseAdapter);

        courseRelatedLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Course courseToSend = (Course) courseAdapter.getItem(position);

                /**
                 * todo : send data
                 */

                startActivity(new Intent(SkillCourseDetailActivity.this, CourseDetailActivity.class));
            }
        });
    }
}
