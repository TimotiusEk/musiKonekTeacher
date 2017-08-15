package com.example.timotiusek.musikonekteacher;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
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
import com.example.timotiusek.musikonekteacher.CustomClass.Student;
import com.example.timotiusek.musikonekteacher.Helper.Connector;
import com.example.timotiusek.musikonekteacher.Helper.TextFormater;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowSkillFragment extends Fragment implements SkillPickerDialogFragment.OnSkillPickerListener {
    @BindView(R.id.skills_lv__show_skill_fra)
    ListView showSkillListView;
    ArrayList<Skill> skills;
    ShowSkillAdapter showSkillAdapter;
    MainActivity ma;

    public ShowSkillFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        loadSkill();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_skill, container, false);
        ButterKnife.bind(this,view);
        ma = (MainActivity) getActivity();
        ma.getSupportActionBar().setTitle("Keahlian");
        ma.setChecked(R.id.menu_skill);

        skills = new ArrayList<>();
//        skills.add(new Skill("Gitar", "Gitar Akustik"));
//        skills.add(new Skill("Piano", "Piano Pop"));

        showSkillAdapter = new ShowSkillAdapter(skills, getActivity());
        showSkillListView.setAdapter(showSkillAdapter);

        showSkillListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Skill skill = (Skill) showSkillAdapter.getItem(position);



                /**
                 * todo : receive and send data
                 */

                Intent intent = new Intent(getActivity(), SkillCourseDetailActivity.class);
                Bundle extras = new Bundle();
                extras.putString("id",skills.get(position).getId());
                extras.putString("name",skills.get(position).getInstrument());
//                Log.d("ASDF","sending "+skills.get(position).getId());
                intent.putExtras(extras);

                startActivity(intent);

            }
        });
        // Inflate the layout for this fragment

//        loadSkill();

        return view;
    }
    @OnClick(R.id.add_skill_btn__show_skill_fra)
    void addSkill(){
//        startActivity(new Intent(getActivity(), AddSkillActivity.class));

        SkillPickerDialogFragment spdf = new SkillPickerDialogFragment();
        spdf.show(getActivity().getFragmentManager(),"ASDF");
        spdf.attachSkillPickerListener(this);

        /**
         * Todo : add add_skill_fab behavior
         */
    }

    public void onSkillPicked(){
        loadSkill();
    };

    private void loadSkill(){

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("profile", Context.MODE_PRIVATE);

        String token ="";

        if(!sharedPreferences.getString("token","").equals("")) {
            token = sharedPreferences.getString("token","");
        }


        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getContext().getCacheDir(), 1024 * 1024); // 1MB cap
        final Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        String url = Connector.getURL() +"/api/v1/skill/myskill?token="+token;


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        skills.clear();
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

                                showSkillAdapter.notifyDataSetChanged();
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

                            Toast.makeText(getContext(), "Connection Error",Toast.LENGTH_SHORT).show();

                        }else{
                            int a = networkResponse.statusCode;
                            if(networkResponse.statusCode == 403){
                                Toast.makeText(getContext(), "TOKEN INVALID, PLEASE RE LOG",Toast.LENGTH_SHORT).show();

                            }

                            if(networkResponse.statusCode == 500){
                                Toast.makeText(getContext(), "INVALID CREDENTIALS",Toast.LENGTH_SHORT).show();
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

}
