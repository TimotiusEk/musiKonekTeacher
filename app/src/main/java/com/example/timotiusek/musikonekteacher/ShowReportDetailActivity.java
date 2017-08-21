package com.example.timotiusek.musikonekteacher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShowReportDetailActivity extends AppCompatActivity {

    @BindView(R.id.show_date__show_report_detail_act)
    TextView showDate;

    @BindView(R.id.show_time__show_report_detail_act)
    TextView showTime;

    @BindView(R.id.show_homework__show_report_detail_act)
    TextView showHomework;

    @BindView(R.id.show_exercises__show_report_detail_act)
    TextView showExercises;

    @BindView(R.id.show_comment__show_report_detail_act)
    TextView showComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_report_detail);
        ButterKnife.bind(this);

        /**
         * todo : populate with data from the database
         */

//        showDate.setText();
//        showTime.setText("Jam " + );
//        showHomework.setText();
//        showExercises.setText();
//        showComment.setText();
    }

//    @OnClick(R.id.checkin_btn__show_report_detail_act)
//    void checkIn(){
//
//    }
//
//    @OnClick(R.id.save_btn__show_report_detail_act)
//    void save(){
//
//    }

}
