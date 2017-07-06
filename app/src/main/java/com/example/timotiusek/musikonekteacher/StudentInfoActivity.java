package com.example.timotiusek.musikonekteacher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class StudentInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_info);
        getSupportActionBar().setTitle("Info Murid");
    }
}
