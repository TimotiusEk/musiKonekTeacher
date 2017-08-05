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

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderRequestFragment extends Fragment {
    private ArrayList<Order> notFilteredOrders;
    private ArrayList<Order> pendingOrders;
    private ArrayList<Order> acceptedOrders;

    @BindView(R.id.accepted_orders_lv__order_request_page)
    ListView acceptedOrdersLv;

    @BindView(R.id.pending_orders_lv__order_request_page)
    ListView pendingOrdersLv;

    private OrderAdapter pendingOrdersAdapter;
    private OrderAdapter acceptedOrdersAdapter;
    public OrderRequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_order_request, container, false);
        ButterKnife.bind(this, v);

        notFilteredOrders = new ArrayList<>();
        notFilteredOrders.add(new Order(R.drawable.avatar, "Kursus Piano Pemula", "Paket 5 kali pertemuan","Joe Biden", "PENDING"));
        notFilteredOrders.add(new Order(R.drawable.avatar, "Kursus Gitar Pemula", "Paket 2 kali pertemuan","Joe Allen", "REJECTED"));
        notFilteredOrders.add(new Order(R.drawable.avatar, "Kursus Biola Pemula", "Paket 1 kali pertemuan","Joe Cole", "PENDING"));
        notFilteredOrders.add(new Order(R.drawable.avatar, "Kursus Vokal Pemula", "Paket 4 kali pertemuan","Joe shua Suherman", "ACCEPTED"));
        notFilteredOrders.add(new Order(R.drawable.avatar, "Kursus Piano Intermediate", "Paket 12 kali pertemuan","Joe unochi", "REJECTED"));
        notFilteredOrders.add(new Order(R.drawable.avatar, "Kursus Angkung Pemula", "Paket 3 kali pertemuan","Joealan Kacang", "ACCEPTED"));
        pendingOrders = new ArrayList<>();
        acceptedOrders = new ArrayList<>();


        for(Order notFilteredOrder : notFilteredOrders){
            if(notFilteredOrder.getStatus().equals("PENDING")){
                pendingOrders.add(notFilteredOrder);
            }
        }

        pendingOrdersAdapter = new OrderAdapter(pendingOrders, getActivity());
        pendingOrdersLv.setAdapter(pendingOrdersAdapter);

        pendingOrdersLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    /**
                     * Todo : sent data
                     */
                    startActivity(new Intent(getActivity(), ViewOrderActivity.class));
                }
            });

        for(Order notFilteredOrder : notFilteredOrders){
            if(notFilteredOrder.getStatus().equals("ACCEPTED")){
                acceptedOrders.add(notFilteredOrder);
            }
        }

        acceptedOrdersAdapter = new OrderAdapter(acceptedOrders, getActivity());
        acceptedOrdersLv.setAdapter(acceptedOrdersAdapter);
        acceptedOrdersLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    /**
                     * Todo : sent data
                     */
                    startActivity(new Intent(getActivity(), StudentInfoActivity.class));
                }
            });

        // Inflate the layout for this fragment
        return v;
    }

}
