package com.example.timotiusek.musikonekteacher;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.timotiusek.musikonekteacher.CustomClass.Earning;
import com.example.timotiusek.musikonekteacher.CustomClass.EarningAdapter;
import com.example.timotiusek.musikonekteacher.CustomClass.Order;
import com.example.timotiusek.musikonekteacher.CustomClass.OrderAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class EarningFragment extends Fragment {
    String status;
    ArrayList<Earning> notFilteredEarnings;
    ArrayList<Earning> filteredEarnings;
    @BindView(R.id.earning_fragment_list_view)
    ListView listView;
    public EarningFragment() {
        // Required empty public constructor
    }

    public EarningFragment(String status){
        this.status = status;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_earning, container, false);
        ButterKnife.bind(this, v);

        notFilteredEarnings = new ArrayList<>();
        notFilteredEarnings.add(new Earning(R.drawable.avatar, "Kursus Piano Pemula", "Paket 5 kali pertemuan","Joe Biden", "EXISTING"));
        notFilteredEarnings.add(new Earning(R.drawable.avatar, "Kursus Gitar Pemula", "Paket 2 kali pertemuan","Joe Allen", "EXISTING"));
        notFilteredEarnings.add(new Earning(R.drawable.avatar, "Kursus Biola Pemula", "Paket 1 kali pertemuan","Joe Cole", "EXISTING"));
        notFilteredEarnings.add(new Earning(R.drawable.avatar, "Kursus Vokal Pemula", "Paket 4 kali pertemuan","Joe shua Suherman", "FINISHED"));
        notFilteredEarnings.add(new Earning(R.drawable.avatar, "Kursus Piano Intermediate", "Paket 12 kali pertemuan","Joe unochi", "FINISHED"));
        notFilteredEarnings.add(new Earning(R.drawable.avatar, "Kursus Angkung Pemula", "Paket 3 kali pertemuan","Joealan Kacang", "FINISHED"));
        filteredEarnings = new ArrayList<>();

        if(status.equals("EXISTING")){
            for(Earning notFilteredEarning : notFilteredEarnings){
                if(notFilteredEarning.getStatus().equals("EXISTING")){
                    filteredEarnings.add(notFilteredEarning);
                }
            }
        } else if(status.equals("FINISHED")){
            for(Earning notFilteredEarning : notFilteredEarnings){
                if(notFilteredEarning.getStatus().equals("FINISHED")){
                    filteredEarnings.add(notFilteredEarning);
                }
            }
        }

        EarningAdapter earningAdapter = new EarningAdapter(filteredEarnings, getActivity());
        listView.setAdapter(earningAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /**
                 * todo : make bundle to send data
                 */
                startActivity(new Intent(getActivity(), EarningDetailActivity.class));
            }
        });
        // Inflate the layout for this fragment
        return v;
    }

}
