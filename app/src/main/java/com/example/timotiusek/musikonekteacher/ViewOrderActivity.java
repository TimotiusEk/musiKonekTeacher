package com.example.timotiusek.musikonekteacher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);

        Intent incomingIntent = getIntent();
        Bundle params = incomingIntent.getExtras();

        TextView courseNameText = (TextView) findViewById(R.id.course_name_view_order_page);
        courseNameText.setText(params.getString("instrument"));

        TextView courseTypeText = (TextView) findViewById(R.id.course_type_view_order_page);
        courseTypeText.setText(params.getString("package"));

        TextView studentNameText = (TextView) findViewById(R.id.student_name_view_order_page);
        studentNameText.setText(params.getString("student"));

        getSupportActionBar().setTitle("Lihat Order");
        ButterKnife.bind(this);
    }
    @OnClick(R.id.reject_btn_view_order_page)
    void goToRejectReasonPage(){
        startActivity(new Intent(this, ReasonRejectActivity.class));
    }

    @OnClick(R.id.accept_btn_view_order_page)
    void goToStudentInfoPage(){
        startActivity(new Intent(this, StudentInfoActivity.class));
    }

}
