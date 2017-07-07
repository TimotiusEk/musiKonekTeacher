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

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScheduleFragment extends Fragment {

    private Context mContext;
    private int hour[][] = new int[31][2];
    private CardView card;
    private RelativeLayout.LayoutParams params;
    RelativeLayout scheduleRL;
    Resources r;
    String day = "";
    @BindView(R.id.current_date)
    TextView currentDate;

    public ScheduleFragment() {
        // Required empty public constructor
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

        mContext = getActivity().getApplicationContext();

        initializeVariableHour();

        scheduleRL = (RelativeLayout) v.findViewById(R.id.schedule_rl);
        removeAllCardView(scheduleRL);

        if(day.equals("Monday")) {
            setAvailability(7, 10, true);
            setAvailability(1030, 12, false);
            setCourseSchedule(13, 1730);
        } else if(day.equals("Tuesday")){
            setAvailability(10, 11, true);
            setAvailability(1130, 13, false);
            setCourseSchedule(20, 22);
        } else if(day.equals("Wednesday")){
            setAvailability(10, 11, true);
            setAvailability(1130, 13, false);
            setCourseSchedule(20, 22);
        } else if(day.equals("Thursday")){
            setAvailability(10, 11, true);
            setAvailability(1130, 13, false);
            setCourseSchedule(20, 22);
        } else if(day.equals("Friday")){
            setAvailability(10, 11, true);
            setAvailability(1130, 13, false);
            setCourseSchedule(20, 22);
        } else if(day.equals("Saturday")){
            setAvailability(10, 11, true);
            setAvailability(1130, 13, false);
            setCourseSchedule(20, 22);
        } else{
            /**
             * Not for weekly schedule fragment
             */
            setAvailability(7, 9, false);
            setCourseSchedule(9, 11);
            setAvailability(11, 22, false);
            currentDate.setVisibility(View.GONE);
        }

        // Inflate the layout for this fragment
        return v;
    }

    private void initializeVariableHour() {
        hour[0][0] = R.id.line_am_07_00;
        hour[0][1] = 7;

        hour[1][0] = R.id.line_am_07_30;
        hour[1][1] = 730;

        hour[2][0] = R.id.line_am_08_00;
        hour[2][1] = 8;

        hour[3][0] = R.id.line_am_08_30;
        hour[3][1] = 830;

        hour[4][0] = R.id.line_am_09_00;
        hour[4][1] = 9;

        hour[5][0] = R.id.line_am_09_30;
        hour[5][1] = 930;

        hour[6][0] = R.id.line_am_10_00;
        hour[6][1] = 10;

        hour[7][0] = R.id.line_am_10_30;
        hour[7][1] = 1030;

        hour[8][0] = R.id.line_am_11_00;
        hour[8][1] = 11;

        hour[9][0] = R.id.line_am_11_30;
        hour[9][1] = 1130;

        hour[10][0] = R.id.line_pm_12_00;
        hour[10][1] = 12;

        hour[11][0] = R.id.line_pm_12_30;
        hour[11][1] = 1230;

        hour[12][0] = R.id.line_pm_13_00;
        hour[12][1] = 13;

        hour[13][0] = R.id.line_pm_13_30;
        hour[13][1] = 1330;

        hour[14][0] = R.id.line_pm_14_00;
        hour[14][1] = 14;

        hour[15][0] = R.id.line_pm_14_30;
        hour[15][1] = 1430;

        hour[16][0] = R.id.line_pm_15_00;
        hour[16][1] = 15;

        hour[17][0] = R.id.line_pm_15_30;
        hour[17][1] = 1530;

        hour[18][0] = R.id.line_pm_16_00;
        hour[18][1] = 16;

        hour[19][0] = R.id.line_pm_16_30;
        hour[19][1] = 1630;

        hour[20][0] = R.id.line_pm_17_00;
        hour[20][1] = 17;

        hour[21][0] = R.id.line_pm_17_30;
        hour[21][1] = 1730;

        hour[22][0] = R.id.line_pm_18_00;
        hour[22][1] = 18;

        hour[23][0] = R.id.line_pm_18_30;
        hour[23][1] = 1830;

        hour[24][0] = R.id.line_pm_19_00;
        hour[24][1] = 19;

        hour[25][0] = R.id.line_pm_19_30;
        hour[25][1] = 1930;

        hour[26][0] = R.id.line_pm_20_00;
        hour[26][1] = 20;

        hour[27][0] = R.id.line_pm_20_30;
        hour[27][1] = 2030;

        hour[28][0] = R.id.line_pm_21_00;
        hour[28][1] = 21;

        hour[29][0] = R.id.line_pm_21_30;
        hour[29][1] = 2130;

        hour[30][0] = R.id.line_pm_22_00;
        hour[30][1] = 22;
    }

    private void initializeCard(){
        card = new CardView(mContext);

        params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        r = mContext.getResources();
        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                -9,
                r.getDisplayMetrics()
        ); //to convert to -9 dp

        params.setMargins(0, px, 0, px); //left, top, right, bottom
        params.addRule(RelativeLayout.END_OF, R.id.am_07_00);
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
        for(int a = 0 ; a < 31 ; a++){
            if(hour[a][1] == start){
                startId = hour[a][0];
            } else if(hour[a][1] == end){
                endId = hour[a][0];
            }
        }

        params.addRule(RelativeLayout.BELOW, startId);
        params.addRule(RelativeLayout.ABOVE, endId);

        card.setLayoutParams(params);

        TextView tv = new TextView(mContext);
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
        for(int a = 0 ; a < 31 ; a++){
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

        RelativeLayout cardViewRL = new RelativeLayout(mContext);
        cardViewRL.setLayoutParams(paramsRelativeLayout);
        card.addView(cardViewRL);

        paramsStudentImg.addRule(RelativeLayout.CENTER_VERTICAL);
        paramsStudentImg.setMargins(leftMargin, 0, 0, 0); //left, top, right, bottom
        CircleImageView studentImg = new CircleImageView(mContext);
        studentImg.setImageResource(R.drawable.avatar);
        studentImg.setLayoutParams(paramsStudentImg);
        studentImg.setId(View.generateViewId());

        View topImageView = new View(mContext);
        topImageView.setBackgroundColor(getResources().getColor(android.R.color.black));
        paramsTopImageView.addRule(RelativeLayout.ABOVE, studentImg.getId());
        topImageView.setLayoutParams(paramsTopImageView);
        topImageView.setId(View.generateViewId());

        TextView courseName = new TextView(mContext);
        paramsCourseName.addRule(RelativeLayout.RIGHT_OF, studentImg.getId());
        paramsCourseName.setMargins(leftMargin, dp1*5, 0, 0); //left, top, right, bottom
        paramsCourseName.addRule(RelativeLayout.BELOW, topImageView.getId());
        courseName.setLayoutParams(paramsCourseName);
        courseName.setId(View.generateViewId());

        TextView courseDescription = new TextView(mContext);
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

}
