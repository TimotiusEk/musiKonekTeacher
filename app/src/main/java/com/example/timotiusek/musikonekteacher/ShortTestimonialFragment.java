package com.example.timotiusek.musikonekteacher;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShortTestimonialFragment extends Fragment {
MainActivity ma;

    public ShortTestimonialFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ma = (MainActivity) getActivity();
        //ma.clearCheckedItems();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_short_testimonial, container, false);
    }

}
