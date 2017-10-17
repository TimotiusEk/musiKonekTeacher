package com.example.timotiusek.musikonekteacher;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timotiusek.musikonekteacher.CustomClass.Appointment;
import com.example.timotiusek.musikonekteacher.CustomClass.MagicBox;
import com.example.timotiusek.musikonekteacher.CustomClass.Schedule;
import com.example.timotiusek.musikonekteacher.CustomClass.ScheduleController;
import com.example.timotiusek.musikonekteacher.Helper.TextFormater;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetScheduleActivity extends AppCompatActivity {

    @BindView(R.id.monday__set_schedule_act) ListView mondayList;
    @BindView(R.id.monday_add__set_schedule_act) TextView mondayAdd;
    private ArrayList<String> mondayRow;
    private CustomArrayAdapter mondayAdapter;

    @BindView(R.id.tuesday__set_schedule_act) ListView tuesdayList;
    @BindView(R.id.tuesday_add__set_schedule_act) TextView tuesdayAdd;
    private ArrayList<String> tuesdayRow;
    private CustomArrayAdapter tuesdayAdapter;

    @BindView(R.id.wednessday__set_schedule_act) ListView wednessdayList;
    @BindView(R.id.wednessday_add__set_schedule_act) TextView wednessdayAdd;
    private ArrayList<String> wednessdayRow;
    private CustomArrayAdapter wednessdayAdapter;

    @BindView(R.id.thursday__set_schedule_act) ListView thursdayList;
    @BindView(R.id.thursday_add__set_schedule_act) TextView thursdayAdd;
    private ArrayList<String> thursdayRow;
    private CustomArrayAdapter thursdayAdapter;

    @BindView(R.id.friday__set_schedule_act) ListView fridayList;
    @BindView(R.id.friday_add__set_schedule_act) TextView fridayAdd;
    private ArrayList<String> fridayRow;
    private CustomArrayAdapter fridayAdapter;

    @BindView(R.id.saturday__set_schedule_act) ListView saturdayList;
    @BindView(R.id.saturday_add__set_schedule_act) TextView saturdayAdd;
    private ArrayList<String> saturdayRow;
    private CustomArrayAdapter saturdayAdapter;

    @BindView(R.id.sunday__set_schedule_act) ListView sundayList;
    @BindView(R.id.sunday_add__set_schedule_act) TextView sundayAdd;
    private ArrayList<String> sundayRow;
    private CustomArrayAdapter sundayAdapter;

    private boolean isDataReady = false;

    private class CustomArrayAdapter extends ArrayAdapter<String> {

        private String[] hours;
        public Date[] hourPoints;
        public ArrayList<Integer> lowerBound;
        public ArrayList<Integer> upperBound;

        private class ViewHolder {
            public Spinner start;
            public Spinner end;
        }

        public CustomArrayAdapter(Context context, ArrayList< String > data) {
            super(context, R.layout.row_layout_set_schedule, data);
            hourPoints = new Date[33];
            for(int i = 0; i < hourPoints.length; i++) {
                hourPoints[i] = new Date(0, 0, 0, 7, 30*i);
            }
            hours = new String[33];
            for(int i = 0; i < hourPoints.length; i++) {
                hours[i] = hourPoints[i].getHours() + ":" + (hourPoints[i].getMinutes() == 0 ? "00" : "30");
            }
            lowerBound = new ArrayList<>();
            upperBound = new ArrayList<>();
            Log.d("DEBUG", "hahaha: " + data.size() + "");
            for(int i = 0; i < data.size(); i++) {
                String[] code = data.get(i).split("\\s");
                lowerBound.add(i, Integer.parseInt(code[0]));
                upperBound.add(i, Integer.parseInt(code[1]));
            }
            this.notifyDataSetChanged();
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            String data = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            final SetScheduleActivity.CustomArrayAdapter.ViewHolder viewHolder; // view lookup cache stored in tag

            viewHolder = new SetScheduleActivity.CustomArrayAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_layout_set_schedule, parent, false);
            viewHolder.start = (Spinner) convertView.findViewById(R.id.start_spinner__set_schedule_rl);
            viewHolder.end   = (Spinner) convertView.findViewById(R.id.end_spinner__set_schedule_rl);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(SetScheduleActivity.this, android.R.layout.simple_spinner_item, hours);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            viewHolder.start.setAdapter(adapter);

            viewHolder.start.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, final int x, long id) {
                    lowerBound.add(position, x);

                    ArrayAdapter<String> adapter2 = new ArrayAdapter<>(SetScheduleActivity.this, android.R.layout.simple_spinner_item,
                            Arrays.copyOfRange(hours, x+1, hours.length));
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    viewHolder.end.setAdapter(adapter2);
                    viewHolder.end.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int y, long id) {
                            upperBound.add(position, x+1+y);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    try {
                        viewHolder.start.setSelection(lowerBound.get(position));
                    } catch (IndexOutOfBoundsException e) {
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            try {
                viewHolder.start.setSelection(lowerBound.get(position));
            } catch (IndexOutOfBoundsException e) {
            }

            convertView.setTag(viewHolder);
            return convertView;
        }
    }

    @OnClick(R.id.monday_add__set_schedule_act)
    public void mondayAdd() {
        if(!isDataReady) {
            return;
        }
        mondayRow.add("0 0");
        mondayAdapter.notifyDataSetChanged();
        setListViewHeightBasedOnChildren(mondayList);
    }

    @OnClick(R.id.tuesday_add__set_schedule_act)
    public void tuesdayAdd() {
        if(!isDataReady) {
            return;
        }
        tuesdayRow.add("0 0");
        tuesdayAdapter.notifyDataSetChanged();
        setListViewHeightBasedOnChildren(tuesdayList);
    }

    @OnClick(R.id.wednessday_add__set_schedule_act)
    public void wednessdayAdd() {
        if(!isDataReady) {
            return;
        }
        wednessdayRow.add("0 0");
        wednessdayAdapter.notifyDataSetChanged();
        setListViewHeightBasedOnChildren(wednessdayList);
    }

    @OnClick(R.id.thursday_add__set_schedule_act)
    public void thursdayAdd() {
        if(!isDataReady) {
            return;
        }
        thursdayRow.add("0 0");
        thursdayAdapter.notifyDataSetChanged();
        setListViewHeightBasedOnChildren(thursdayList);
    }

    @OnClick(R.id.friday_add__set_schedule_act)
    public void fridayAdd() {
        if(!isDataReady) {
            return;
        }
        fridayRow.add("0 0");
        fridayAdapter.notifyDataSetChanged();
        setListViewHeightBasedOnChildren(fridayList);
    }

    @OnClick(R.id.saturday_add__set_schedule_act)
    public void saturdayAdd() {
        if(!isDataReady) {
            return;
        }
        saturdayRow.add("0 0");
        saturdayAdapter.notifyDataSetChanged();
        setListViewHeightBasedOnChildren(saturdayList);
    }

    @OnClick(R.id.sunday_add__set_schedule_act)
    public void sundayAdd() {
        if(!isDataReady) {
            return;
        }
        sundayRow.add("0 0");
        sundayAdapter.notifyDataSetChanged();
        setListViewHeightBasedOnChildren(sundayList);
    }

    private JSONArray initJSONArray() {
        JSONArray output = new JSONArray();
        for(int i = 0; i < 32; i++) {
            output.put(0);
        }
        return output;
    }

    @OnClick(R.id.send_btn__set_schedule_act)
    public void submit() {
        JSONArray monday = initJSONArray();
        for(int i = 0; i < mondayRow.size(); i++) {
            int x = mondayAdapter.lowerBound.get(i);
            int y = mondayAdapter.upperBound.get(i);
            for(int j = x; j < y; j++) {
                try {
                    monday.put(j, 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        JSONArray tuesday = initJSONArray();
        for(int i = 0; i < tuesdayRow.size(); i++) {
            int x = tuesdayAdapter.lowerBound.get(i);
            int y = tuesdayAdapter.upperBound.get(i);
            for(int j = x; j < y; j++) {
                try {
                    tuesday.put(j, 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        JSONArray wednessday = initJSONArray();
        for(int i = 0; i < wednessdayRow.size(); i++) {
            int x = wednessdayAdapter.lowerBound.get(i);
            int y = wednessdayAdapter.upperBound.get(i);
            for(int j = x; j < y; j++) {
                try {
                    wednessday.put(j, 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        JSONArray thursday = initJSONArray();
        for(int i = 0; i < thursdayRow.size(); i++) {
            int x = thursdayAdapter.lowerBound.get(i);
            int y = thursdayAdapter.upperBound.get(i);
            for(int j = x; j < y; j++) {
                try {
                    thursday.put(j, 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        JSONArray friday = initJSONArray();
        for(int i = 0; i < fridayRow.size(); i++) {
            int x = fridayAdapter.lowerBound.get(i);
            int y = fridayAdapter.upperBound.get(i);
            for(int j = x; j < y; j++) {
                try {
                    friday.put(j, 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        JSONArray saturday = initJSONArray();
        for(int i = 0; i < saturdayRow.size(); i++) {
            int x = saturdayAdapter.lowerBound.get(i);
            int y = saturdayAdapter.upperBound.get(i);
            for(int j = x; j < y; j++) {
                try {
                    saturday.put(j, 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        JSONArray sunday = initJSONArray();
        for(int i = 0; i < sundayRow.size(); i++) {
            int x = sundayAdapter.lowerBound.get(i);
            int y = sundayAdapter.upperBound.get(i);
            for(int j = x; j < y; j++) {
                try {
                    sunday.put(j, 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        JSONObject bundle = new JSONObject();
        try {
            bundle.put(ScheduleController.days[0], monday);
            bundle.put(ScheduleController.days[1], tuesday);
            bundle.put(ScheduleController.days[2], wednessday);
            bundle.put(ScheduleController.days[3], thursday);
            bundle.put(ScheduleController.days[4], friday);
            bundle.put(ScheduleController.days[5], saturday);
            bundle.put(ScheduleController.days[6], sunday);
            new ScheduleController().saveDataAsync(SetScheduleActivity.this, MagicBox.encodeScheduleData(bundle));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("DEBUG", bundle.toString());
    }

    private void setInitalAdapters() {
        mondayAdapter = new CustomArrayAdapter(SetScheduleActivity.this, mondayRow);
        mondayList.setAdapter(mondayAdapter);
        mondayAdapter.notifyDataSetChanged();
        setListViewHeightBasedOnChildren(mondayList);

        tuesdayAdapter = new CustomArrayAdapter(SetScheduleActivity.this, tuesdayRow);
        tuesdayList.setAdapter(tuesdayAdapter);
        tuesdayAdapter.notifyDataSetChanged();
        setListViewHeightBasedOnChildren(tuesdayList);

        wednessdayAdapter = new CustomArrayAdapter(SetScheduleActivity.this, wednessdayRow);
        wednessdayList.setAdapter(wednessdayAdapter);
        wednessdayAdapter.notifyDataSetChanged();
        setListViewHeightBasedOnChildren(wednessdayList);

        thursdayAdapter = new CustomArrayAdapter(SetScheduleActivity.this, thursdayRow);
        thursdayList.setAdapter(thursdayAdapter);
        thursdayAdapter.notifyDataSetChanged();
        setListViewHeightBasedOnChildren(thursdayList);

        fridayAdapter = new CustomArrayAdapter(SetScheduleActivity.this, fridayRow);
        fridayList.setAdapter(fridayAdapter);
        fridayAdapter.notifyDataSetChanged();
        setListViewHeightBasedOnChildren(fridayList);

        saturdayAdapter = new CustomArrayAdapter(SetScheduleActivity.this, saturdayRow);
        saturdayList.setAdapter(saturdayAdapter);
        saturdayAdapter.notifyDataSetChanged();
        setListViewHeightBasedOnChildren(saturdayList);

        sundayAdapter = new CustomArrayAdapter(SetScheduleActivity.this, sundayRow);
        sundayList.setAdapter(sundayAdapter);
        sundayAdapter.notifyDataSetChanged();
        setListViewHeightBasedOnChildren(sundayList);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_schedule);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle("Atur Jadwal");
        Toast.makeText(this, "Mengambil jadwal aktif", Toast.LENGTH_SHORT).show();
        ScheduleController.getActiveSchedule(SetScheduleActivity.this);
    }

    public void onDataSaved() {
        finish();
    }

    private ArrayList<String> interpretSchedule(JSONArray data) {
        int start = 0, end = 0;
        ArrayList<String> output = new ArrayList<>();
        for(int i = 1; i < data.length(); i++) {
            end = i;
            if(i + 1 == data.length()) {
                Log.d("DEBUG", "This is the end");
                if(data.optInt(i - 1) != data.optInt(i)) {
                    if(data.optInt(i) == 1) {
                        output.add(i + " " + (i+1));
                        Log.d("DEBUG", output.get(output.size()-1));
                    }
                } else {
                    end = i + 1;
                }
                if(data.optInt(i - 1) == 1) {
                    output.add(start + " " + end);
                    Log.d("DEBUG", output.get(output.size()-1));
                }
            }
            if(data.optInt(i - 1) != data.optInt(i)) {
                if(data.optInt(i - 1) == 1) {
                    output.add(start + " " + end);
                    Log.d("DEBUG", output.get(output.size()-1));
                }
                start = i;
            }
        }
        return output;
    }

    public void onDataReady(JSONObject result) {
        Log.d("DEBUG", result.toString());
        mondayRow     = interpretSchedule(result.optJSONArray("Monday"));
        tuesdayRow    = interpretSchedule(result.optJSONArray("Tuesday"));
        wednessdayRow = interpretSchedule(result.optJSONArray("Wednesday"));
        thursdayRow   = interpretSchedule(result.optJSONArray("Thursday"));
        fridayRow     = interpretSchedule(result.optJSONArray("Friday"));
        saturdayRow   = interpretSchedule(result.optJSONArray("Saturday"));
        sundayRow     = interpretSchedule(result.optJSONArray("Sunday"));
        isDataReady = true;
        setInitalAdapters();
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}

