package com.example.timotiusek.musikonekteacher;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Rating;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.example.timotiusek.musikonekteacher.CustomClass.Student;
import com.example.timotiusek.musikonekteacher.Helper.Connector;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */

public class ShortTestimonialFragment extends Fragment {
//MainActivity ma;

    public ShortTestimonialFragment() {
        // Required empty public constructor
    }

    Student student;

    public static ShortTestimonialFragment newInstance(Student student){

        ShortTestimonialFragment stf = new ShortTestimonialFragment();
        stf.setStudent(student);

        return stf;

    }

    public void setStudent(Student student){
        this.student = student;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_short_testimonial, container, false);
        ButterKnife.bind(this, v);
//        ma = (MainActivity) getActivity();
        //ma.clearCheckedItems();
        // Inflate the layout for this fragment

        TextView studentView = (TextView) v.findViewById(R.id.course_name__short_testimonial_fra);
        studentView.setText(student.getCourseName());

        TextView teacherView = (TextView) v.findViewById(R.id.teacher_name__short_testimonial_fra);
        teacherView.setText(student.getStudentName());

        TextView courseNameView = (TextView) v.findViewById(R.id.course_name2__short_testimonial_fra);
        courseNameView.setText(student.getCourseName());

        loadScore();
        loadStudentScore();
        return v;
    }

    @OnClick(R.id.open_btn__short_testimonial_fra)
    void openTestimonialInputPage(){
        Toast.makeText(getActivity(), "getPressed", Toast.LENGTH_SHORT).show();
//        startActivity(new Intent(getActivity(), CreateTestimonialActivity.class));
    }

    @OnClick(R.id.open_btn_for_student_short_testimonial_fra)
    void submitForStudentForm(){
//        Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
        EditText commentEdit = (EditText) getView().findViewById(R.id.comment_for_student_edit_short_testimonial_fra);
        if(commentEdit.getVisibility() == View.INVISIBLE){
            Toast.makeText(getActivity(), "You've already rated this student", Toast.LENGTH_SHORT).show();
        }else{
//            Toast.makeText(getActivity(), "Aw yisss", Toast.LENGTH_SHORT).show();
            submitForm();
        }
    }

    void loadScore(){

        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap
        final Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("profile", Context.MODE_PRIVATE);

        String token ="";
        if(!sharedPreferences.getString("token","").equals("")) {
            token = sharedPreferences.getString("token","");
        }

        String url = Connector.getURL() +"/api/v1/course/rating?token="+token+"&course_id="+student.getCourseID();

        Log.d("ASDF",url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("ASDF",response);

                        try {
                            JSONObject all = new JSONObject(response);
                            JSONObject res  = all.getJSONObject("data");

                            RatingBar ratingBar = (RatingBar) getView().findViewById(R.id.rating_bar__short_testimonial_fra);
                            ratingBar.setRating((float) res.getDouble("rating"));

                            TextView commentView = (TextView) getView().findViewById(R.id.comment__short_testimonial_fra);

                            if(res.getString("comment").equals("") || res.getString("comment").equals("null")){
                                commentView.setVisibility(View.INVISIBLE);
                            }else {
                                commentView.setText(res.getString("comment"));
                                ratingBar.setIsIndicator(true);
                            }


//                            showDate.setText(TextFormater.formatDateSpacing(res.getString("date")));
//                            showTime.setText("Jam " +res.getString("time"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        NetworkResponse networkResponse = error.networkResponse;

                        if(networkResponse == null){

                            Toast.makeText(getActivity(), "Connection Error",Toast.LENGTH_SHORT).show();

                        }else{
                            int a = networkResponse.statusCode;
                            if(networkResponse.statusCode == 401){
                            }

                            if(networkResponse.statusCode == 500){
                                Toast.makeText(getActivity(), "ERROR",Toast.LENGTH_SHORT).show();
                            }

                            if(networkResponse.statusCode != 401){


                            }

                        }



                    }
                }){

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                int mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }

        };

//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(stringRequest);

    }

    void loadStudentScore(){

        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap
        final Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("profile", Context.MODE_PRIVATE);

        String token ="";
        if(!sharedPreferences.getString("token","").equals("")) {
            token = sharedPreferences.getString("token","");
        }

        String url = Connector.getURL() +"/api/v1/course/studentRating?token="+token+"&course_id="+student.getCourseID();

        Log.d("ASDF",url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("ASDF",response);

                        try {
                            JSONObject all = new JSONObject(response);
                            JSONObject res  = all.getJSONObject("data");

                            RatingBar ratingBar = (RatingBar) getView().findViewById(R.id.rating_bar_for_student_short_testimonial_fra);
                            ratingBar.setRating((float) res.getDouble("student_rating"));

                            TextView commentView = (TextView) getView().findViewById(R.id.comment_for_student_short_testimonial_fra);

                            TextView studentName = (TextView) getView().findViewById(R.id.student_name__short_testimonial_fra);
                            studentName.setText(res.getString("fullname"));

                            EditText commentEdit = (EditText) getView().findViewById(R.id.comment_for_student_edit_short_testimonial_fra);

                            if(res.getString("student_comment").equals("") || res.getString("student_comment").equals("null")){
                                commentView.setVisibility(View.INVISIBLE);
                                ratingBar.setIsIndicator(false);
                            }else {
                                commentView.setText(res.getString("student_comment"));
                                ratingBar.setIsIndicator(true);
                                commentEdit.setVisibility(View.INVISIBLE);
                            }


//                            showDate.setText(TextFormater.formatDateSpacing(res.getString("date")));
//                            showTime.setText("Jam " +res.getString("time"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        NetworkResponse networkResponse = error.networkResponse;

                        if(networkResponse == null){

                            Toast.makeText(getActivity(), "Connection Error",Toast.LENGTH_SHORT).show();

                        }else{
                            int a = networkResponse.statusCode;
                            if(networkResponse.statusCode == 401){
                            }

                            if(networkResponse.statusCode == 500){
                                Toast.makeText(getActivity(), "ERROR",Toast.LENGTH_SHORT).show();
                            }

                            if(networkResponse.statusCode != 401){


                            }

                        }



                    }
                }){

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                int mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }

        };

//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(stringRequest);

    }

    void submitForm(){

        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap
        final Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        String url = Connector.getURL() +"/api/v1/course/rateStudent";
        Log.d("ASDF",url);



        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getActivity(),"Success", Toast.LENGTH_SHORT).show();
                        EditText commentEdit = (EditText) getView().findViewById(R.id.comment_for_student_edit_short_testimonial_fra);
                        TextView commentView = (TextView) getView().findViewById(R.id.comment_for_student_short_testimonial_fra);

                        commentEdit.setVisibility(View.INVISIBLE);
                        commentView.setVisibility(View.VISIBLE);

                        RatingBar ratingBar = (RatingBar) getView().findViewById(R.id.rating_bar_for_student_short_testimonial_fra);

                        ratingBar.setIsIndicator(true);

                        commentView.setText(commentEdit.getText());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        NetworkResponse networkResponse = error.networkResponse;

                        if(networkResponse == null){

                            Toast.makeText(getActivity(), "Connection Error",Toast.LENGTH_SHORT).show();

                        }else{
                            int a = networkResponse.statusCode;
                            if(networkResponse.statusCode == 401){
                            }

                            if(networkResponse.statusCode == 500){
                                Toast.makeText(getActivity(), "ERROR",Toast.LENGTH_SHORT).show();
                            }

                            if(networkResponse.statusCode != 401){


                            }

                        }



                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> reqBody = new HashMap<String, String>();

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("profile", Context.MODE_PRIVATE);

                String token ="";

                if(!sharedPreferences.getString("token","").equals("")) {
                    token = sharedPreferences.getString("token","");
                }

                EditText commentEdit = (EditText) getView().findViewById(R.id.comment_for_student_edit_short_testimonial_fra);

                RatingBar ratingBar = (RatingBar) getView().findViewById(R.id.rating_bar_for_student_short_testimonial_fra);

                reqBody.put("token",token);
                reqBody.put("course_id",student.getCourseID());
                reqBody.put("rating", String.valueOf(ratingBar.getRating()));
                reqBody.put("comment",commentEdit.getText().toString());

                return checkParams(reqBody);
            }
            private Map<String, String> checkParams(Map<String, String> map){
                Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, String> pairs = (Map.Entry<String, String>)it.next();
                    if(pairs.getValue()==null){
                        map.put(pairs.getKey(), "");
                    }
                }
                return map;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                int mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }

        };

//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(stringRequest);


    }



}
