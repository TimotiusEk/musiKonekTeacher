package com.example.timotiusek.musikonekteacher;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(v, getActivity());
        // Inflate the layout for this fragment
        return v;
    }

    @OnClick(R.id.registered_students_list_btn__home_fra)
    public void openRegisteredStudentsList(){
        /**
         * todo : redirect to list of registered student
         */
    }

    @OnClick(R.id.requests_list_btn__home_fra)
    public void openRequestsList(){
        /**
         * todo : redirect to list of requests
         */
    }

    @OnClick(R.id.open_schedule_btn__home_fra)
    public void openTeacherSchedule(){
        /**
         * todo : redirect to teacher schedule
         */
    }

}
