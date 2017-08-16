package com.example.timotiusek.musikonekteacher;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.example.timotiusek.musikonekteacher.CustomClass.Order;
import com.example.timotiusek.musikonekteacher.CustomClass.OrderAdapter;
import com.example.timotiusek.musikonekteacher.CustomClass.Payment;
import com.example.timotiusek.musikonekteacher.CustomClass.PaymentAdapter;
import com.example.timotiusek.musikonekteacher.Helper.Connector;
import com.example.timotiusek.musikonekteacher.Helper.TextFormater;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderPaymentFragment extends Fragment {
    private ArrayList<Order> notFilteredPayments;
    private ArrayList<Order> scheduledPayments;
    private ArrayList<Order> paidPayments;

    MainActivity ma;

    @BindView(R.id.paid_payment_lv__order_payment_fra)
    ListView paidPaymentsLv;

    @BindView(R.id.scheduled_payment_lv__order_payment_fra)
    ListView scheduledPaymentsLv;

    private OrderAdapter scheduledPaymentAdapter;
    private OrderAdapter paidPaymentAdapter;
    public OrderPaymentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_order_payment, container, false);
        ButterKnife.bind(this, v);

        ma = (MainActivity) getActivity();

        notFilteredPayments = new ArrayList<>();
//        notFilteredPayments.add(new Payment(R.drawable.avatar, "Kursus Piano Pemula", "Paket 5 kali pertemuan","Joe Biden", "SCHEDULED"));
//        notFilteredPayments.add(new Payment(R.drawable.avatar, "Kursus Gitar Pemula", "Paket 2 kali pertemuan","Joe Allen", "SCHEDULED"));
//        notFilteredPayments.add(new Payment(R.drawable.avatar, "Kursus Biola Pemula", "Paket 1 kali pertemuan","Joe Cole", "SCHEDULED"));
//        notFilteredPayments.add(new Payment(R.drawable.avatar, "Kursus Vokal Pemula", "Paket 4 kali pertemuan","Joe shua Suherman", "PAID"));
//        notFilteredPayments.add(new Payment(R.drawable.avatar, "Kursus Piano Intermediate", "Paket 12 kali pertemuan","Joe unochi", "PAID"));
//        notFilteredPayments.add(new Payment(R.drawable.avatar, "Kursus Angkung Pemula", "Paket 3 kali pertemuan","Joealan Kacang", "PAID"));
        scheduledPayments = new ArrayList<>();
        paidPayments = new ArrayList<>();


        for(Order notFilteredPayment : notFilteredPayments){
            if(notFilteredPayment.getStatus().equals("WAITING FOR PAYMENT")){
                scheduledPayments.add(notFilteredPayment);
            }
        }

        scheduledPaymentAdapter = new OrderAdapter(scheduledPayments, getActivity());
        scheduledPaymentsLv.setAdapter(scheduledPaymentAdapter);


        for(Order notFilteredPayment : notFilteredPayments){
            if(notFilteredPayment.getStatus().equals("ACCEPTED")){
                paidPayments.add(notFilteredPayment);
            }
        }

        paidPaymentAdapter = new OrderAdapter(paidPayments, getActivity());
        paidPaymentsLv.setAdapter(paidPaymentAdapter);

        // Inflate the layout for this fragment
        return v;
    }

    private void populateOrders() {

        notFilteredPayments = new ArrayList<>();
//
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("profile", Context.MODE_PRIVATE);

        String token = "";

        if (!sharedPreferences.getString("token", "").equals("")) {
            token = sharedPreferences.getString("token", "");
        }


        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getContext().getCacheDir(), 1024 * 1024); // 1MB cap
        final Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        String url = Connector.getURL() + "/api/v1/course/getCourseByTeacherId?token=" + token;

        Log.d("ASDF",url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject res = new JSONObject(response);
                            Log.d("ASDF", res.toString());
                            JSONArray arr = res.getJSONArray("data");

                            for (int i = 0; i < arr.length(); i++) {
                                JSONObject jo = arr.getJSONObject(i);

                                String instrument = jo.getString("instrument");
                                String student_name = jo.getString("student_name");
                                String appointment = jo.getString("appointments");
                                String status = jo.getString("status_name");

                                notFilteredPayments.add(new Order(R.drawable.avatar, TextFormater.formatCourse(instrument), TextFormater.format(Integer.valueOf(appointment)), student_name, status));
//

                            }

//                            Log.d("ASDF","ELEH" + res.toString());


                        } catch (JSONException e) {
                            Log.d("ASDF", "Fail");
                            e.printStackTrace();
                        }

                        filterOrder();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        NetworkResponse networkResponse = error.networkResponse;

                        if (networkResponse == null) {

                            Toast.makeText(ma, "Connection Error", Toast.LENGTH_SHORT).show();

                        } else {
                            int a = networkResponse.statusCode;
                            if (networkResponse.statusCode == 403) {
                                Toast.makeText(ma, "TOKEN INVALID, PLEASE RE LOG", Toast.LENGTH_SHORT).show();

                            }

                            if (networkResponse.statusCode == 500) {
                                Toast.makeText(ma, "INVALID CREDENTIALS", Toast.LENGTH_SHORT).show();
                            }

                            if (networkResponse.statusCode != 401) {

                                Log.d("ASDF", "SHIT");

                            }

                        }


                    }
                }) {

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                int mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }

        };

        requestQueue.add(stringRequest);

    }

    public void filterOrder() {
        //if(status.equals("PENDING")){
        for (Order notFilteredOrder : notFilteredPayments) {
            if (notFilteredOrder.getStatus().equalsIgnoreCase("WAITING FOR PAYMENT")) {
                scheduledPayments.add(notFilteredOrder);
            }
        }
        scheduledPaymentsLv.setAdapter(new OrderAdapter(scheduledPayments, getActivity()));
        scheduledPaymentsLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /**
                 * Todo : sent data
                 */
                Order selected = scheduledPayments.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("instrument", selected.getCourseName());
                bundle.putString("package", selected.getCoursePackage());
                bundle.putString("student", selected.getStudentName());

//                    Toast.makeText(getContext(),selected.getStudentName(),Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), ViewOrderActivity.class);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });
        // } //else if(status.equals("ACCEPTED")){
        for (Order notFilteredOrder : notFilteredPayments) {
            if (notFilteredOrder.getStatus().equalsIgnoreCase("RUNNING")) {
                paidPayments.add(notFilteredOrder);
            }
        }

        paidPaymentsLv.setAdapter(new OrderAdapter(paidPayments, getActivity()));
        paidPaymentsLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /**
                 * Todo : sent data
                 */
                Order selected = paidPayments.get(position);

                Bundle bundle = new Bundle();
                bundle.putString("instrument", selected.getCourseName());
                bundle.putString("package", selected.getCoursePackage());
                bundle.putString("student", selected.getStudentName());

//                    Toast.makeText(getContext(),selected.getStudentName(),Toast.LENGTH_SHORT).show();

//                    Intent intent= new Intent(getActivity(), ViewAcceptedOrderActivity.class);
                Intent intent = new Intent(getActivity(), AcceptedStudentInfoActivity.class);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        populateOrders();
    }
}
