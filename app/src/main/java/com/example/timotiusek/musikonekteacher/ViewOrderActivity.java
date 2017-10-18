package com.example.timotiusek.musikonekteacher;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.timotiusek.musikonekteacher.CustomClass.Appointment;
import com.example.timotiusek.musikonekteacher.CustomClass.Course;
import com.example.timotiusek.musikonekteacher.Helper.TextFormater;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewOrderActivity extends AppCompatActivity {

    @BindView(R.id.course_name__view_order_act)           TextView courseNameText;
    @BindView(R.id.course_type__view_order_act)           TextView courseTypeText;
    @BindView(R.id.student_name__view_order_act)          TextView studentNameText;
    @BindView(R.id.note__view_order_act)                  TextView descriptionText;
    @BindView(R.id.appointment_list_view__view_order_act) ListView appointmentList;

    private class CustomArrayAdapter extends ArrayAdapter<String> {

        private ArrayList<Appointment> data;

        private class ViewHolder {
            public TextView number;
            public TextView date;
        }

        public CustomArrayAdapter(Context context, ArrayList< Appointment > data) {
            super(context, R.layout.row_layout_appointment_list, new String[data.size()]);
            this.data = data;
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            String data = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            final ViewHolder viewHolder; // view lookup cache stored in tag

            if (convertView == null) {
                viewHolder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.row_layout_appointment_list, parent, false);
                viewHolder.number = (TextView) convertView.findViewById(R.id.number__appointment_list_rl);
                viewHolder.date   = (TextView) convertView.findViewById(R.id.date__appointment_list_rl);

                viewHolder.number.setText("Tertemuan " + (position+1));
                viewHolder.date.setText(TextFormater.formatTime(this.data.get(position).getAppointmentTime()));

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            return convertView;
        }
    }

    @OnClick(R.id.reject_btn__view_order_act)
    void goToRejectReasonPage() {
        Intent intent = new Intent(this, ReasonRejectActivity.class);
        intent.putExtra("course_id", getIntent().getExtras().getInt("course_id"));
        startActivity(intent);
    }

    @OnClick(R.id.accept_btn__view_order_act)
    void goToStudentInfoPage() {
        Course.acknowledgeThisCourse(this, getIntent().getExtras().getInt("course_id"));
    }

    public void onCourseAccepted() {
        Intent intent = new Intent(this, AcceptedStudentInfoActivity.class);
        intent.putExtras(getIntent().getExtras());
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);
        ButterKnife.bind(this);

        Appointment.getAppointmentByCourseId(ViewOrderActivity.this, getIntent().getExtras().getInt("course_id"));

        Bundle params = getIntent().getExtras();
        courseNameText.setText(params.getString("instrument"));
        courseTypeText.setText(params.getString("package"));
        studentNameText.setText(params.getString("student"));
        descriptionText.setText(params.getString("desc", "-"));

        getSupportActionBar().setTitle("Lihat Order");
        ButterKnife.bind(this);
    }

    public void onDataReady(ArrayList<Appointment> appointments) {
        appointmentList.setAdapter(new CustomArrayAdapter(this, appointments));
    }
}
