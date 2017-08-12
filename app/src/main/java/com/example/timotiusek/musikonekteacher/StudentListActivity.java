package com.example.timotiusek.musikonekteacher;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.timotiusek.musikonekteacher.CustomClass.Student;

public class StudentListActivity extends AppCompatActivity {

    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        Intent incoming = getIntent();
        Bundle params = incoming.getExtras();

        Student student = new Student(R.id.profile_image__profile_act, params.getString("courseName"), params.getString("coursePackage"),params.getString("studentName"),"",params.getString("courseID"));

        changeFragment(StudentDetailFragment.newInstance(student));
    }

    public void changeFragment(Fragment newFragment) {
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.fragment_student_list, newFragment);
        mFragmentTransaction.commit();
    }
}
