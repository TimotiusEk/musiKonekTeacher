package com.example.timotiusek.musikonekteacher;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetScheduleActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    private Context context;
    @BindView(R.id.show_scheduled_date__set_schedule_act)
    EditText showScheduledDate;
    DatePickerDialog datePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_schedule);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle("Pasang Jadwal");
        LayoutInflater inflater = this.getLayoutInflater();
        View titleView = inflater.inflate(R.layout.custom_datepicker_title, null);
        context = this.getApplicationContext();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
            datePickerDialog = new DatePickerDialog(
                    this,SetScheduleActivity.this, new Date().getYear() + 1900, new Date().getMonth(), new Date().getDate());
            // Do something for lollipop and above versions
        } else{
            datePickerDialog = new DatePickerDialog(
                    this, R.style.datepicker,SetScheduleActivity.this, new Date().getYear() + 1900, new Date().getMonth(), new Date().getDate());
            // do something for phones running an SDK before lollipop
        }

        datePickerDialog.setCustomTitle(titleView);

        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        datePickerDialog.show();
    }

    @OnClick(R.id.show_scheduled_date__set_schedule_act)
    void showDateDialog(){
        datePickerDialog.show();
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);

        SimpleDateFormat format = new SimpleDateFormat("EE, dd-MM-yyyy");
        String strDate = format.format(calendar.getTime());
        showScheduledDate.setText(strDate);
    }
}

