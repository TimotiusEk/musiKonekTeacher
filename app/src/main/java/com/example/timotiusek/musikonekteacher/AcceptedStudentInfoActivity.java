package com.example.timotiusek.musikonekteacher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AcceptedStudentInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accepted_student_info_activity);
        getSupportActionBar().setTitle("Info Murid");
        ButterKnife.bind(this);

        Intent incomingIntent = getIntent();
        Bundle params = incomingIntent.getExtras();

    }
    @OnClick(R.id.open_schedule_btn__accepted_student_info_act)
    void goToSetSchedulePage(){
        startActivity(new Intent(AcceptedStudentInfoActivity.this, SetRevisedScheduleActivity.class));
    }

}
