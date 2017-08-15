package com.example.timotiusek.musikonekteacher;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
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
import com.example.timotiusek.musikonekteacher.CustomClass.Skill;
import com.example.timotiusek.musikonekteacher.Helper.Connector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by wilbe on 15/08/2017.
 */

public class SkillPickerDialogFragment extends DialogFragment {

    View view;
    ListView skillsListView;
    ArrayList<Skill> skills;
    SkillPickerAdapter skillPickerAdapter;

    interface OnSkillPickerListener{
        void onSkillPicked();
    }

    OnSkillPickerListener onSkillPickerListener;

    @Nullable
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());



        view = getActivity().getLayoutInflater().inflate(R.layout.fragment_skill_picker_dialog, null);
        builder.setView(view);




        return builder.create();
    }

    public void attachSkillPickerListener(OnSkillPickerListener ospl){
        this.onSkillPickerListener = ospl;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){


        skills= new ArrayList<>();
        skillsListView = (ListView) view.findViewById(R.id.skill_list_view);

        skillPickerAdapter = new SkillPickerAdapter(skills, getActivity());
        skillsListView.setAdapter(skillPickerAdapter);

        loadSkill();

        skillsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                addSkill(skills.get(i).getId());
            }
        });

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    void loadSkill(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("profile", Context.MODE_PRIVATE);

        String token ="";

        if(!sharedPreferences.getString("token","").equals("")) {
            token = sharedPreferences.getString("token","");
        }


        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap
        final Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        String url = Connector.getURL() +"/api/v1/skill/notmyskill?token="+token;


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject res = new JSONObject(response);
                            Log.d("ASDF",res.toString());
                            JSONArray arr = res.getJSONArray("data");

                            for(int i=0;i<arr.length();i++){
                                JSONObject jo =  arr.getJSONObject(i);

                                String name = jo.getString("name");
                                String id = jo.getString("skill_id");

                                skills.add(new Skill(name, "yes", id));
//

                                skillPickerAdapter.notifyDataSetChanged();
                            }

                        } catch (JSONException e) {
                            Log.d("ASDF","Fail");
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
                            if(networkResponse.statusCode == 403){
                                Toast.makeText(getActivity(), "TOKEN INVALID, PLEASE RE LOG",Toast.LENGTH_SHORT).show();

                            }

                            if(networkResponse.statusCode == 500){
                                Toast.makeText(getActivity(), "INVALID CREDENTIALS",Toast.LENGTH_SHORT).show();
                            }

                            if(networkResponse.statusCode != 401){

                                Log.d("ASDF","SHIT");

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

        requestQueue.add(stringRequest);
    }

    void addSkill(final String skillId){

        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap
        final Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();


        String url = Connector.getURL() +"/api/v1/skill_teacher/attach";

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(getActivity(), "Skill Added",Toast.LENGTH_SHORT).show();
                        onSkillPickerListener.onSkillPicked();
                        dismiss();


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

                String token = "";
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("profile", Context.MODE_PRIVATE);

                if(!sharedPreferences.getString("token","").equals("")) {
                    token = sharedPreferences.getString("token","");
                }

                reqBody.put("token",token);
                reqBody.put("skill_id",skillId);

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

        requestQueue.add(stringRequest);

    }




}