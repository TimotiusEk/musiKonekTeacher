package com.example.timotiusek.musikonekteacher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateReportActivity extends AppCompatActivity {
    @BindView(R.id.homework__create_report_act)
    TextView homeworkInput;

    @BindView(R.id.exercise__create_report_act)
    TextView exerciseInput;

    @BindView(R.id.comment__create_report_act)
    TextView commentInput;

    @BindView(R.id.show_date__create_report_act)
    TextView showDate;

    @BindView(R.id.show_time__create_report_act)
    TextView showTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_report);
        getSupportActionBar().setTitle("Absen & Buat Laporan");
        ButterKnife.bind(this);

        /**
         * todo : set date --> showDate.setText("");
         */

        /**
         * todo : set time --> showTime.setText("");
         */

    }

    @OnClick(R.id.checkin_btn__create_report_act)
    void checkIn(){
        /**
         * Todo : Create CheckIn Method
         */
    }

    @OnClick(R.id.save_btn__create_report_act)
    void saveReport(){
        /**
         * Todo : Create Save Report Method
         */
    }
}
