package com.example.timotiusek.musikonekteacher.CustomClass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.timotiusek.musikonekteacher.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by TimotiusEk on 7/7/2017.
 */

public class PaymentAdapter extends BaseAdapter{
    private ArrayList<Payment> payments;
    private Context mContext;
    private LayoutInflater inflater;
    @BindView(R.id.student_image__payment_page)
    CircleImageView studentImg;
    @BindView(R.id.student_name__payment_page)
    TextView studentName;
    @BindView(R.id.course_name__payment_page)
    TextView courseName;
    @BindView(R.id.course_package__payment_page)
    TextView coursePackage;

    public PaymentAdapter(ArrayList<Payment> payments, Context c) {
        this.payments = payments;
        mContext = c;
    }

    @Override
    public int getCount() {
        return payments.size();
    }

    @Override
    public Object getItem(int position) {
        return payments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Payment payment = (Payment) getItem(position);
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            convertView = inflater.inflate(R.layout.row_layout_payment, parent, false);
            ButterKnife.bind(this, convertView);

            studentImg.setImageResource(payment.getStudentImage());
            studentName.setText(payment.getStudentName());
            courseName.setText(payment.getCourseName());
            coursePackage.setText(payment.getCoursePackage());
        }
        return convertView;
    }
}
