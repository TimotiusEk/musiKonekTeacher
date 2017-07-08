package com.example.timotiusek.musikonekteacher;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.timotiusek.musikonekteacher.CustomClass.Order;
import com.example.timotiusek.musikonekteacher.CustomClass.OrderAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {
    String status;
    ArrayList<Order> notFilteredOrders;
    ArrayList<Order> filteredOrders;
    @BindView(R.id.order_fragment_list_view)
    ListView listView;
    public OrderFragment() {
        // Required empty public constructor
    }

    public OrderFragment(String status){
        this.status = status;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_order, container, false);
        ButterKnife.bind(this, v);
        notFilteredOrders = new ArrayList<>();
        notFilteredOrders.add(new Order(R.drawable.avatar, "Kursus Piano Pemula", "Paket 5 kali pertemuan","Joe Biden", "PENDING"));
        notFilteredOrders.add(new Order(R.drawable.avatar, "Kursus Gitar Pemula", "Paket 2 kali pertemuan","Joe Allen", "REJECTED"));
        notFilteredOrders.add(new Order(R.drawable.avatar, "Kursus Biola Pemula", "Paket 1 kali pertemuan","Joe Cole", "PENDING"));
        notFilteredOrders.add(new Order(R.drawable.avatar, "Kursus Vokal Pemula", "Paket 4 kali pertemuan","Joe shua Suherman", "ACCEPTED"));
        notFilteredOrders.add(new Order(R.drawable.avatar, "Kursus Piano Intermediate", "Paket 12 kali pertemuan","Joe unochi", "REJECTED"));
        notFilteredOrders.add(new Order(R.drawable.avatar, "Kursus Angkung Pemula", "Paket 3 kali pertemuan","Joealan Kacang", "ACCEPTED"));
        filteredOrders = new ArrayList<>();

        if(status.equals("PENDING")){
            for(Order notFilteredOrder : notFilteredOrders){
                if(notFilteredOrder.getStatus().equals("PENDING")){
                    filteredOrders.add(notFilteredOrder);
                }
            }
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    /**
                     * Todo : sent data
                     */
                    startActivity(new Intent(getActivity(), ViewOrderActivity.class));
                }
            });
        } else if(status.equals("ACCEPTED")){
            for(Order notFilteredOrder : notFilteredOrders){
                if(notFilteredOrder.getStatus().equals("ACCEPTED")){
                    filteredOrders.add(notFilteredOrder);
                }
            }
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    /**
                     * Todo : sent data
                     */
                    startActivity(new Intent(getActivity(), StudentInfoActivity.class));
                }
            });
        } else if(status.equals("REJECTED")){
            for(Order notFilteredOrder : notFilteredOrders){
                if(notFilteredOrder.getStatus().equals("REJECTED")){
                    filteredOrders.add(notFilteredOrder);
                }
            }
        }

        OrderAdapter orderAdapter = new OrderAdapter(filteredOrders, getActivity());
        listView.setAdapter(orderAdapter);
        // Inflate the layout for this fragment
        return v;
    }

}
