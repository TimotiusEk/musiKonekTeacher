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
public class OrderRequestFragment extends Fragment {

    @BindView(R.id.accepted_orders_lv__order_request_fra) ListView acceptedOrdersLv;
    @BindView(R.id.pending_orders_lv__order_request_fra) ListView pendingOrdersLv;

    private String status;
    private ArrayList<Order> notFilteredOrders;
    private ArrayList<Order> filteredAcceptedOrders;
    private ArrayList<Order> filteredPendingOrders;
    private OrderAdapter acceptedOrdersAdapter;
    private OrderAdapter pendingOrdersAdapter;

    public OrderRequestFragment() {
        // Required empty public constructor
    }

    private void populateOrders() {
        notFilteredOrders = new ArrayList<>();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("profile", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getContext().getCacheDir(), 1024 * 1024); // 1MB cap
        final Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        String url = Connector.getURL() + "/api/v1/course/getCourseByTeacherId?token=" + token;

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
                                int id = jo.getInt("course_id");
                                String name = jo.getString("name");
                                String student_name = jo.getString("student_name");
                                String appointment = jo.getString("appointments");
                                String status = jo.getString("status_name");
                                String desc = jo.getString("description");
                                String email = jo.getString("email");
                                String address = jo.getString("address");
                                String phone = jo.getString("phone_number");
                                notFilteredOrders.add(new Order(id, R.drawable.avatar, name, TextFormater.format(Integer.valueOf(appointment)),
                                        student_name, status, desc, email, address, phone));
                            }
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
                            Toast.makeText(getActivity(), "Connection Error", Toast.LENGTH_SHORT).show();
                        } else {
                            if (networkResponse.statusCode == 403) {
                                Toast.makeText(getActivity(), "TOKEN INVALID, PLEASE RE LOG", Toast.LENGTH_SHORT).show();
                            } else if (networkResponse.statusCode == 500) {
                                Toast.makeText(getActivity(), "INVALID CREDENTIALS", Toast.LENGTH_SHORT).show();
                            } else if (networkResponse.statusCode != 401) {
                                Log.d("ASDF", "SHIT");
                            }
                        }
                    }
                }) {

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                return super.parseNetworkResponse(response);
            }
        };
        requestQueue.add(stringRequest);
    }

    public void filterOrder() {
        for (Order notFilteredOrder : notFilteredOrders) {
            Log.d("ASDF", notFilteredOrder.getStatus() + " tab "+ notFilteredOrder.getCourseName());
            if (notFilteredOrder.getStatus().equalsIgnoreCase("REQUESTED")) {
                filteredPendingOrders.add(notFilteredOrder);
            }
        }
        pendingOrdersLv.setAdapter(new OrderAdapter(filteredPendingOrders, getActivity()));
        pendingOrdersLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /**
                 * Todo : sent data
                 */
                Order selected = filteredPendingOrders.get(position);
                Bundle bundle = new Bundle();
                bundle.putInt   ("course_id",  selected.getCourseId());
                bundle.putString("instrument", selected.getCourseName());
                bundle.putString("package",    selected.getCoursePackage());
                bundle.putString("desc",       selected.getDesc());
                bundle.putString("student",    selected.getStudentName());
                bundle.putString("email",      selected.getEmail());
                bundle.putString("address",    selected.getAddress());
                bundle.putString("phone",      selected.getPhone());

                Intent intent = new Intent(getActivity(), ViewOrderActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        for (Order notFilteredOrder : notFilteredOrders) {
            if (notFilteredOrder.getStatus().equalsIgnoreCase("ACKNOWLEDGED")) {
                filteredAcceptedOrders.add(notFilteredOrder);
            }
        }
        acceptedOrdersLv.setAdapter(new OrderAdapter(filteredAcceptedOrders, getActivity()));
        acceptedOrdersLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /**
                 * Todo : sent data
                 */
                Order selected = filteredAcceptedOrders.get(position);

                Bundle bundle = new Bundle();
                bundle.putInt   ("course_id", selected.getCourseId());
                bundle.putString("student",   selected.getStudentName());
                bundle.putString("email",     selected.getEmail());
                bundle.putString("address",   selected.getAddress());
                bundle.putString("phone",     selected.getPhone());

                Intent intent = new Intent(getActivity(), AcceptedStudentInfoActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_order_request, container, false);
        ButterKnife.bind(this, v);
        filteredAcceptedOrders = new ArrayList<>();

        populateOrders();

        // ???
        filteredPendingOrders = new ArrayList<>();
        filteredAcceptedOrders = new ArrayList<>();
        filteredAcceptedOrders = new ArrayList<>();

        filterOrder();
        return v;
    }
}