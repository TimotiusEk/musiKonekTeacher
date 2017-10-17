package com.example.timotiusek.musikonekteacher;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.timotiusek.musikonekteacher.CustomClass.Appointment;
import com.example.timotiusek.musikonekteacher.CustomClass.Course;
import com.example.timotiusek.musikonekteacher.Helper.TextFormater;

import org.json.JSONArray;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AcceptedStudentInfoActivity extends AppCompatActivity {

    @BindView(R.id.student_name__accepted_student_info_act)          TextView studentName;
    @BindView(R.id.student_email__accepted_student_info_act)         TextView studentEmail;
    @BindView(R.id.student_address__accepted_student_info_act)       TextView studentAddress;
    @BindView(R.id.student_phone__accepted_student_info_act)         TextView studentPhone;
    @BindView(R.id.appointment_list_view__accepted_student_info_act) ListView appointmentList;

    private ArrayList<Appointment> appointments;

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
            final AcceptedStudentInfoActivity.CustomArrayAdapter.ViewHolder viewHolder; // view lookup cache stored in tag

            if (convertView == null) {
                viewHolder = new AcceptedStudentInfoActivity.CustomArrayAdapter.ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.row_layout_appointment_list, parent, false);
                viewHolder.number = (TextView) convertView.findViewById(R.id.number__appointment_list_rl);
                viewHolder.date   = (TextView) convertView.findViewById(R.id.date__appointment_list_rl);

                viewHolder.number.setText("Tertemuan " + (position+1));
                viewHolder.date.setText(TextFormater.formatTime(this.data.get(position).getAppointmentTime()));

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (AcceptedStudentInfoActivity.CustomArrayAdapter.ViewHolder) convertView.getTag();
            }

            return convertView;
        }
    }

    @OnClick(R.id.open_schedule_btn__accepted_student_info_act)
    void goToSetSchedulePage() {
//        startActivity(new Intent(AcceptedStudentInfoActivity.this, SetRevisedScheduleActivity.class));
        JSONArray id = new JSONArray();
        JSONArray time = new JSONArray();

        for(Appointment a : appointments) {
            id.put(a.getAppointmentId());
            time.put(a.getAppointmentTime());
        }

        Course.acceptThisCourse(this, getIntent().getExtras().getInt("course_id"), id, time);
    }

    public void onCourseAccepted() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accepted_student_info_activity);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle("Info Murid");
        ButterKnife.bind(this);

        Appointment.getAppointmentByCourseId(AcceptedStudentInfoActivity.this, getIntent().getExtras().getInt("course_id"));

        Bundle params = getIntent().getExtras();
        studentName.setText(params.getString("student"));
        studentEmail.setText(params.getString("email"));
        studentAddress.setText(params.getString("address"));
        studentPhone.setText(params.getString("phone"));
    }

    public void onDataReady(ArrayList<Appointment> appointments) {
        this.appointments = appointments;
        appointmentList.setAdapter(new AcceptedStudentInfoActivity.CustomArrayAdapter(this, appointments));
    }
}
