package com.example.timotiusek.musikonekteacher;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;

import com.example.timotiusek.musikonekteacher.CustomClass.Schedule;
import com.example.timotiusek.musikonekteacher.CustomClass.ScheduleAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetRevisedScheduleActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    @BindView(R.id.schedule_lv__set_revised_schedule_page)
    ListView scheduleLv;

    @BindView(R.id.set_schedule_btn__set_revised_schedule_page)
    Button setScheduleBtn;

    private ArrayList<Schedule> schedules;
    private ScheduleAdapter scheduleAdapter;
    DatePickerDialog datePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_revised_schedule);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle("Lihat & Revisi Jadwal");

        schedules = new ArrayList<>();
        schedules.add(new Schedule("Pertemuan 1" , "1 April 2012, 17:30 WIB"));
        schedules.add(new Schedule("Pertemuan 2" , "21 Mei 1927, 17:30 WIB"));
        schedules.add(new Schedule("Pertemuan 3" , "1 Januari 1938, 17:30 WIB"));
        schedules.add(new Schedule("Pertemuan 4" , "21 Desember 1930, 17:30 WIB"));
        schedules.add(new Schedule("Pertemuan 5" , "19 Desember 1910, 17:30 WIB"));
        schedules.add(new Schedule("Pertemuan 6" , "2 Maret 1945, 17:30 WIB"));
        schedules.add(new Schedule("Pertemuan 7" , "7 Juli 1996, 17:30 WIB"));

        scheduleAdapter = new ScheduleAdapter(schedules, this);
        scheduleLv.setAdapter(scheduleAdapter);
    }

    @OnClick(R.id.set_schedule_btn__set_revised_schedule_page)
    void showCalendar(){
        LayoutInflater inflater = this.getLayoutInflater();
        View titleView = inflater.inflate(R.layout.custom_datepicker_title, null);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
            datePickerDialog = new DatePickerDialog(
                    this,SetRevisedScheduleActivity.this, new Date().getYear() + 1900, new Date().getMonth(), new Date().getDate());
            // Do something for lollipop and above versions
        } else{
            datePickerDialog = new DatePickerDialog(
                    this, R.style.datepicker,SetRevisedScheduleActivity.this, new Date().getYear() + 1900, new Date().getMonth(), new Date().getDate());
            // do something for phones running an SDK before lollipop
        }
        datePickerDialog.setCustomTitle(titleView);

        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);

        SimpleDateFormat format = new SimpleDateFormat("EE, dd-MM-yyyy");
        String strDate = format.format(calendar.getTime());

        /**
         * todo : process the DATE that have been picked
         */
    }
}
