package com.example.timotiusek.musikonekteacher;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_short_testimonial, container, false);
        ButterKnife.bind(this, v);
//        ma = (MainActivity) getActivity();
        //ma.clearCheckedItems();
        // Inflate the layout for this fragment

        RatingBar ratingBar = (RatingBar) v.findViewById(R.id.rating_bar__short_testimonial_fra);
        ratingBar.setNumStars(2);

        return v;
    }

    @OnClick(R.id.open_btn__short_testimonial_fra)
    void openTestimonialInputPage(){
        Toast.makeText(getActivity(), "getPressed", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getActivity(), CreateTestimonialActivity.class));
    }

}
