package com.example.timotiusek.musikonekteacher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditCourseActivity extends AppCompatActivity {
    @BindView(R.id.how_many_meetings__edit_course_act) Spinner howManyMeetings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle("Ubah Kursus");

        howManyMeetings.setSelection(getIndex(howManyMeetings, "4"));
    }


    //to get index of the spinner based on string value
    private int getIndex(Spinner spinner, String myString)
    {
        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                index = i;
                break;
            }
        }
        return index;
    }
}
