package com.example.timotiusek.musikonekteacher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.example.timotiusek.musikonekteacher.CustomClass.Course;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReasonRejectActivity extends AppCompatActivity {

    @BindView(R.id.reason_input__reason_reject_act) EditText reason;

    @OnClick(R.id.send_btn__reason_reject_act)
    public void confirm() {
        Log.d("DEBUG", getIntent().getIntExtra("course_id", 0) + "");
        Course.rejectThisCourse(this, getIntent().getIntExtra("course_id", 0), reason.getText().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reason_reject);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle("Alasan Menolak");
    }
}
