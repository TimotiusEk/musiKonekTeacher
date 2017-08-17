package com.example.timotiusek.musikonekteacher;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.timotiusek.musikonekteacher.CustomClass.Schedule;
import com.example.timotiusek.musikonekteacher.CustomClass.ScheduleController;

import org.json.JSONArray;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ScheduleFragment extends Fragment {

    private static final int hour[][] = {
            {R.id.line_am_07_00__schedule_fra, 7},
            {R.id.line_am_07_30__schedule_fra, 730},
            {R.id.line_am_08_00__schedule_fra, 8},
            {R.id.line_am_08_30__schedule_fra, 830},
            {R.id.line_am_09_00__schedule_fra, 9},
            {R.id.line_am_09_30__schedule_fra, 930},
            {R.id.line_am_10_00__schedule_fra, 10},
            {R.id.line_am_10_30__schedule_fra, 1030},
            {R.id.line_am_11_00__schedule_fra, 11},
            {R.id.line_am_11_30__schedule_fra, 1130},
            {R.id.line_pm_12_00__schedule_fra, 12},
            {R.id.line_pm_12_30__schedule_fra, 1230},
            {R.id.line_pm_13_00__schedule_fra, 13},
            {R.id.line_pm_13_30__schedule_fra, 1330},
            {R.id.line_pm_14_00__schedule_fra, 14},
            {R.id.line_pm_14_30__schedule_fra, 1430},
            {R.id.line_pm_15_00__schedule_fra, 15},
            {R.id.line_pm_15_30__schedule_fra, 1530},
            {R.id.line_pm_16_00__schedule_fra, 16},
            {R.id.line_pm_16_30__schedule_fra, 1630},
            {R.id.line_pm_17_00__schedule_fra, 17},
            {R.id.line_pm_17_30__schedule_fra, 1730},
            {R.id.line_pm_18_00__schedule_fra, 18},
            {R.id.line_pm_18_30__schedule_fra, 1830},
            {R.id.line_pm_19_00__schedule_fra, 19},
            {R.id.line_pm_19_30__schedule_fra, 1930},
            {R.id.line_pm_20_00__schedule_fra, 20},
            {R.id.line_pm_20_30__schedule_fra, 2030},
            {R.id.line_pm_21_00__schedule_fra, 21},
            {R.id.line_pm_21_30__schedule_fra, 2130},
            {R.id.line_pm_22_00__schedule_fra, 22},
            {R.id.line_pm_22_30__schedule_fra, 2230}
    };

    @BindView(R.id.current_date__schedule_fra) TextView currentDate;

    private CardView card;
    private RelativeLayout.LayoutParams params;
    private RelativeLayout scheduleRL;
    private Resources r;
    private String day = "";
    private JSONArray data = null;

    public ScheduleFragment() {
        // Required empty public constructor
    }

    public void setData(JSONArray data) {
        this.data = data;
//        updateView(data);
    }

    public ScheduleFragment(String day){
        this.day = day;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get the application context

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_schedule, container, false);
        ButterKnife.bind(this, v);

        scheduleRL = (RelativeLayout) v.findViewById(R.id.schedule_rl__schedule_fra);
        removeAllCardView(scheduleRL);

        try {
            updateView(data);
        } catch(NullPointerException e) {
//            e.printStackTrace();
        }

        return v;
    }

    private void initializeCard(){
        card = new CardView(getActivity().getApplicationContext());

        params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        r = getActivity().getApplicationContext().getResources();
        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                -9,
                r.getDisplayMetrics()
        ); //to convert to -9 dp

        params.setMargins(0, px, 0, px); //left, top, right, bottom
        params.addRule(RelativeLayout.END_OF, R.id.am_07_00__schedule_fra);
    }

    private void removeAllCardView(ViewGroup viewGroup) {

        int count = viewGroup.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = viewGroup.getChildAt(i);
            if (view instanceof ViewGroup) {
                CardView cardView = (CardView) view;
                Log.d("cardView", "true");
                viewGroup.removeView(view);
            }
        }

    }

    private void setAvailability(int start, int end, boolean isAvailable){
        initializeCard();
        int startId = 0;
        int endId = 0;
        for(int a = 0 ; a < hour.length ; a++){
            if(hour[a][1] == start){
                startId = hour[a][0];
            } else if(hour[a][1] == end){
                endId = hour[a][0];
            }
        }

        params.addRule(RelativeLayout.BELOW, startId);
        params.addRule(RelativeLayout.ABOVE, endId);

        card.setLayoutParams(params);

        TextView tv = new TextView(getActivity().getApplicationContext());
        if(isAvailable) {
            card.setCardBackgroundColor(Color.parseColor("#62f199")); //medium aquamarine
            tv.setText("AVAILABLE");
        } else{
            card.setCardBackgroundColor(Color.parseColor("#cbd8db"));
            tv.setText("NOT AVAILABLE");
        }
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        tv.setTextColor(Color.WHITE);
        tv.setGravity(Gravity.CENTER);
        tv.setTypeface(Typeface.DEFAULT_BOLD);
        // Put the TextView in CardView
        card.addView(tv);
        scheduleRL.addView(card);
    }

    private void setCourseSchedule(int start, int end /** todo: dibkin parameter 1 lagi (object) yg nampung semua informasi course**/){
        initializeCard();
        int startId = 0;
        int endId = 0;
        for(int a = 0 ; a < hour.length ; a++){
            if(hour[a][1] == start){
                startId = hour[a][0];
            } else if(hour[a][1] == end){
                endId = hour[a][0];
            }
        }

        params.addRule(RelativeLayout.BELOW, startId);
        params.addRule(RelativeLayout.ABOVE, endId);

        card.setLayoutParams(params);


        int studentImgSize = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                80,
                r.getDisplayMetrics()
        );//to convert to 80 dp

        int leftMargin = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                10,
                r.getDisplayMetrics()
        );

        int dp1 = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                1,
                r.getDisplayMetrics()
        );


        RelativeLayout.LayoutParams paramsRelativeLayout= new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );

        RelativeLayout.LayoutParams paramsStudentImg = new RelativeLayout.LayoutParams(
                studentImgSize,
                studentImgSize
        );

        RelativeLayout.LayoutParams paramsCourseName = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        RelativeLayout.LayoutParams paramsCourseDescription = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        RelativeLayout.LayoutParams paramsTopImageView = new RelativeLayout.LayoutParams(
                dp1,
                dp1
        );

        RelativeLayout cardViewRL = new RelativeLayout(getActivity().getApplicationContext());
        cardViewRL.setLayoutParams(paramsRelativeLayout);
        card.addView(cardViewRL);

        paramsStudentImg.addRule(RelativeLayout.CENTER_VERTICAL);
        paramsStudentImg.setMargins(leftMargin, 0, 0, 0); //left, top, right, bottom
        CircleImageView studentImg = new CircleImageView(getActivity().getApplicationContext());
        studentImg.setImageResource(R.drawable.avatar);
        studentImg.setLayoutParams(paramsStudentImg);
        studentImg.setId(View.generateViewId());

        View topImageView = new View(getActivity().getApplicationContext());
        topImageView.setBackgroundColor(getResources().getColor(android.R.color.black));
        paramsTopImageView.addRule(RelativeLayout.ABOVE, studentImg.getId());
        topImageView.setLayoutParams(paramsTopImageView);
        topImageView.setId(View.generateViewId());

        TextView courseName = new TextView(getActivity().getApplicationContext());
        paramsCourseName.addRule(RelativeLayout.RIGHT_OF, studentImg.getId());
        paramsCourseName.setMargins(leftMargin, dp1*5, 0, 0); //left, top, right, bottom
        paramsCourseName.addRule(RelativeLayout.BELOW, topImageView.getId());
        courseName.setLayoutParams(paramsCourseName);
        courseName.setId(View.generateViewId());

        TextView courseDescription = new TextView(getActivity().getApplicationContext());
        paramsCourseDescription.addRule(RelativeLayout.BELOW, courseName.getId());
        paramsCourseDescription.addRule(RelativeLayout.RIGHT_OF, studentImg.getId());
        paramsCourseDescription.setMargins(leftMargin, 0, 0, 0); //left, top, right, bottom
        courseDescription.setLayoutParams(paramsCourseDescription);

        card.setCardBackgroundColor(Color.parseColor("#E49b48")); //Fire Bush (Orange)
        courseName.setText("Kursus Piano Pemula");
        courseDescription.setText("Paket 6 kali pertemuan" + "\n" +"Hendra Sulaeman");

        courseName.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        courseName.setTextColor(Color.WHITE);
        courseName.setTypeface(Typeface.DEFAULT_BOLD);
        // Put the TextView in CardView
        cardViewRL.addView(studentImg);
        cardViewRL.addView(topImageView);
        cardViewRL.addView(courseName);
        cardViewRL.addView(courseDescription);

        scheduleRL.addView(card);
    }

    private void setCard(int code, int start, int end) {
        if(code == ScheduleController.AVAILABLE) {
            setAvailability(hour[start][1], hour[end][1], true);
        } else if(code == ScheduleController.UNAVAILABLE) {
            setAvailability(hour[start][1], hour[end][1], false);
        } else if(code == ScheduleController.OCCUPIED) {
            setCourseSchedule(hour[start][1], hour[end][1]);
        }
    }

    private void updateView(JSONArray data) {
        int start = 0, end = 0;
        for(int i = 1; i < data.length(); i++) {
            if(i + 1 == data.length()) {
                if(data.optInt(i - 1) == data.optInt(i)) {
                    end = i;
                } else {
                    setCard(data.optInt(i), i, i);
                }
                setCard(data.optInt(i - 1), start, end);
            }
            if(data.optInt(i - 1) != data.optInt(i)) {
                setCard(data.optInt(i - 1), start, end);
                start = i;
            }
            end = i;
        }
        Log.d("DEBUG", day + ": " + data.toString());
    }
}
