package com.example.timotiusek.musikonekteacher;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class UnderDevelopmentFragment extends Fragment {

    MainActivity ma;
    public UnderDevelopmentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_under_development, container, false);
        ma = (MainActivity) getActivity();
        ma.setChecked(R.id.menu_home);
        ma.getSupportActionBar().setTitle("Beranda");
        // Inflate the layout for this fragment
        return v;
    }

}
