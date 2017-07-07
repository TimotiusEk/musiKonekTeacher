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

public class OrderAdapter extends BaseAdapter{
    private ArrayList<Order> orders;
    private Context mContext;
    private LayoutInflater inflater;
    @BindView(R.id.student_image_order_page)
    CircleImageView studentImg;
    @BindView(R.id.student_name_order_page)
    TextView studentName;
    @BindView(R.id.course_name_order_page)
    TextView courseName;
    @BindView(R.id.course_package_order_page)
    TextView coursePackage;

    public OrderAdapter(ArrayList<Order> orders, Context c) {
        this.orders = orders;
        mContext = c;
    }

    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public Object getItem(int position) {
        return orders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Order order = (Order) getItem(position);
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            convertView = inflater.inflate(R.layout.row_layout_order, parent, false);
            ButterKnife.bind(this, convertView);

            studentImg.setImageResource(order.getStudentImage());
            studentName.setText(order.getStudentName());
            courseName.setText(order.getCourseName());
            coursePackage.setText(order.getCoursePackage());
        }
        return convertView;
    }
}
