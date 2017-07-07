package com.example.timotiusek.musikonekteacher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class StudentInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_info_activity);
        getSupportActionBar().setTitle("Info Murid");
        ButterKnife.bind(this);
    }

    @OnClick(R.id.set_schedule_btn_student_info_page)
    void goToSetSchedulePage(){
        startActivity(new Intent(StudentInfoActivity.this, SetScheduleActivity.class));
    }
}
