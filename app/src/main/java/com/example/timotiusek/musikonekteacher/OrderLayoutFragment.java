package com.example.timotiusek.musikonekteacher;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderLayoutFragment extends Fragment {

    @BindView(R.id.tab_layout__order_layout_fra) TabLayout tabLayout;
    @BindView(R.id.view_pager__order_layout_fra) ViewPager viewPager;

    private class MyAdapter extends android.support.v4.app.FragmentStatePagerAdapter {

        private MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0 : return new OrderRequestFragment();
                case 1 : return new OrderPaymentFragment();
                case 2 : return new OrderRejectedFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0 : return "REQUEST";
                case 1 : return "PAYMENT";
                case 2 : return "REJECTED";
            }
            return null;
        }
    }

    public OrderLayoutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_layout, container, false);
        ButterKnife.bind(this, view);

        MainActivity ma = (MainActivity) getActivity();
        ma.getSupportActionBar().setTitle("Order");
        ma.setChecked(R.id.menu_order);
        /**
         * todo : tentuin set checknya
         */

        HashMap<String, Object> x = new HashMap<>();

        viewPager.setAdapter(new OrderLayoutFragment.MyAdapter(getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(0);
        return view;
    }
}
