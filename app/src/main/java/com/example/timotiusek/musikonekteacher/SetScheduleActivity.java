package com.example.timotiusek.musikonekteacher;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetScheduleActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    private Context context;
    @BindView(R.id.show_scheduled_date)
    EditText showScheduledDate;
    DatePickerDialog datePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_schedule);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle("Pasang Jadwal");
        context = this.getApplicationContext();
        datePickerDialog = new DatePickerDialog(
                this, SetScheduleActivity.this, new Date().getYear() + 1900, new Date().getMonth(), new Date().getDate());
        datePickerDialog.show();
    }

    @OnClick(R.id.show_scheduled_date)
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

