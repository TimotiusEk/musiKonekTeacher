package com.example.timotiusek.musikonekteacher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ViewAcceptedOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_accepted_order);
        getSupportActionBar().setTitle("Lihat Order");


        Intent incomingIntent = getIntent();
        Bundle params = incomingIntent.getExtras();

//        TextView textView = (TextView) findViewById(R.id.course_type__view_accepted_order_act);
//        Log.d("ASDF", String.valueOf(textView.getText()));

        TextView courseNameText = (TextView) findViewById(R.id.course_name__view_accepted_order_act);
        courseNameText.setText(params.getString("instrument"));

        Log.d("ASDF", String.valueOf(courseNameText.getText()));

        TextView courseTypeText = (TextView) findViewById(R.id.course_type__view_accepted_order_act);
        courseTypeText.setText(params.getString("package"));

        TextView studentNameText = (TextView) findViewById(R.id.student_name__view_accepted_order_act);
        studentNameText.setText(params.getString("student"));

        Log.d("ASDF","LIHAT ORDER BROU");


    }
}
