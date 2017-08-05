package com.example.timotiusek.musikonekteacher;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.timotiusek.musikonekteacher.CustomClass.Payment;
import com.example.timotiusek.musikonekteacher.CustomClass.PaymentAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderPaymentFragment extends Fragment {
    private ArrayList<Payment> notFilteredPayments;
    private ArrayList<Payment> scheduledPayments;
    private ArrayList<Payment> paidPayments;

    @BindView(R.id.paid_payment_lv__order_payment_fra)
    ListView paidPaymentsLv;

    @BindView(R.id.scheduled_payment_lv__order_payment_fra)
    ListView scheduledPaymentsLv;

    private PaymentAdapter scheduledPaymentAdapter;
    private PaymentAdapter paidPaymentAdapter;
    public OrderPaymentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_order_payment, container, false);
        ButterKnife.bind(this, v);

        notFilteredPayments = new ArrayList<>();
        notFilteredPayments.add(new Payment(R.drawable.avatar, "Kursus Piano Pemula", "Paket 5 kali pertemuan","Joe Biden", "SCHEDULED"));
        notFilteredPayments.add(new Payment(R.drawable.avatar, "Kursus Gitar Pemula", "Paket 2 kali pertemuan","Joe Allen", "SCHEDULED"));
        notFilteredPayments.add(new Payment(R.drawable.avatar, "Kursus Biola Pemula", "Paket 1 kali pertemuan","Joe Cole", "SCHEDULED"));
        notFilteredPayments.add(new Payment(R.drawable.avatar, "Kursus Vokal Pemula", "Paket 4 kali pertemuan","Joe shua Suherman", "PAID"));
        notFilteredPayments.add(new Payment(R.drawable.avatar, "Kursus Piano Intermediate", "Paket 12 kali pertemuan","Joe unochi", "PAID"));
        notFilteredPayments.add(new Payment(R.drawable.avatar, "Kursus Angkung Pemula", "Paket 3 kali pertemuan","Joealan Kacang", "PAID"));
        scheduledPayments = new ArrayList<>();
        paidPayments = new ArrayList<>();


        for(Payment notFilteredPayment : notFilteredPayments){
            if(notFilteredPayment.getStatus().equals("SCHEDULED")){
                scheduledPayments.add(notFilteredPayment);
            }
        }

        scheduledPaymentAdapter = new PaymentAdapter(scheduledPayments, getActivity());
        scheduledPaymentsLv.setAdapter(scheduledPaymentAdapter);


        for(Payment notFilteredPayment : notFilteredPayments){
            if(notFilteredPayment.getStatus().equals("PAID")){
                paidPayments.add(notFilteredPayment);
            }
        }

        paidPaymentAdapter = new PaymentAdapter(paidPayments, getActivity());
        paidPaymentsLv.setAdapter(paidPaymentAdapter);

        // Inflate the layout for this fragment
        return v;
    }

}
