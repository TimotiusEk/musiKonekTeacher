package com.example.timotiusek.musikonekteacher;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.timotiusek.musikonekteacher.CustomClass.Order;
import com.example.timotiusek.musikonekteacher.CustomClass.OrderAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderRejectedFragment extends Fragment {
    ArrayList<Order> notFilteredOrders;
    ArrayList<Order> filteredOrders;
    @BindView(R.id.rejected_orders_lv__order_rejected_fra)
    ListView listView;

    public OrderRejectedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_order_rejected, container, false);
        ButterKnife.bind(this, v);
        notFilteredOrders = new ArrayList<>();
        notFilteredOrders.add(new Order(R.drawable.avatar, "Kursus Piano Pemula", "Paket 5 kali pertemuan", "Joe Biden", "PENDING"));
        notFilteredOrders.add(new Order(R.drawable.avatar, "Kursus Gitar Pemula", "Paket 2 kali pertemuan", "Joe Allen", "REJECTED"));
        notFilteredOrders.add(new Order(R.drawable.avatar, "Kursus Biola Pemula", "Paket 1 kali pertemuan", "Joe Cole", "PENDING"));
        notFilteredOrders.add(new Order(R.drawable.avatar, "Kursus Vokal Pemula", "Paket 4 kali pertemuan", "Joe shua Suherman", "ACCEPTED"));
        notFilteredOrders.add(new Order(R.drawable.avatar, "Kursus Piano Intermediate", "Paket 12 kali pertemuan", "Joe unochi", "REJECTED"));
        notFilteredOrders.add(new Order(R.drawable.avatar, "Kursus Angkung Pemula", "Paket 3 kali pertemuan", "Joealan Kacang", "ACCEPTED"));
        filteredOrders = new ArrayList<>();


        for (Order notFilteredOrder : notFilteredOrders) {
            if (notFilteredOrder.getStatus().equals("REJECTED")) {
                filteredOrders.add(notFilteredOrder);
            }
        }


        OrderAdapter orderAdapter = new OrderAdapter(filteredOrders, getActivity());
        listView.setAdapter(orderAdapter);
        // Inflate the layout for this fragment
        return v;
    }

}
