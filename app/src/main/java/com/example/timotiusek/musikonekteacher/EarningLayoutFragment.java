package com.example.timotiusek.musikonekteacher;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class EarningLayoutFragment extends Fragment {
    @BindView(R.id.tab_layout_earning_layout_page)
    TabLayout tabLayout;
    @BindView(R.id.view_pager_earning_layout_page)
    ViewPager viewPager;
    MainActivity ma;

    public EarningLayoutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_earning_layout, container, false);
        ButterKnife.bind(this, view);

        ma = (MainActivity) getActivity();
        ma.setTitle("Earning Page");
        //ma.clearCheckedItems();

        /**
         * todo : tentuin set checknya
         */


        viewPager.setAdapter(new EarningLayoutFragment.MyAdapter(getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

        viewPager.setCurrentItem(0);
        // Inflate the layout for this fragment
        return view;
    }

    private class MyAdapter extends android.support.v4.app.FragmentStatePagerAdapter {

        MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            switch (position){
                case 0 : return new EarningFragment("EXISTING");
                case 1 : return new EarningFragment("FINISHED");
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0 : return "EXISTING";
                case 1 : return "FINISHED";
            }
            return null;
        }

    }

}
