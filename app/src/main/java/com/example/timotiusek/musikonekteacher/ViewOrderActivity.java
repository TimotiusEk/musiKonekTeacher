package com.example.timotiusek.musikonekteacher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);

//        Intent incomingIntent = getIntent();
//        Bundle params = incomingIntent.getExtras();
//
//        TextView courseNameText = (TextView) findViewById(R.id.course_name__view_order_act);
//        courseNameText.setText(params.getString("instrument"));
//
//        TextView courseTypeText = (TextView) findViewById(R.id.course_type__view_order_act);
//        courseTypeText.setText(params.getString("package"));
//
//        TextView studentNameText = (TextView) findViewById(R.id.student_name__view_order_act);
//        studentNameText.setText(params.getString("student"));

        getSupportActionBar().setTitle("Lihat Order");
        ButterKnife.bind(this);
    }
    @OnClick(R.id.reject_btn__view_order_act)
    void goToRejectReasonPage(){
        startActivity(new Intent(this, ReasonRejectActivity.class));
    }

    @OnClick(R.id.accept_btn__view_order_act)
    void goToStudentInfoPage(){
        startActivity(new Intent(this, AcceptedStudentInfoActivity.class));
    }

}
