package com.example.timotiusek.musikonekteacher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);
        getSupportActionBar().setTitle("Lihat Order");
        ButterKnife.bind(this);
    }
    @OnClick(R.id.set_schedule_btn_view_order_page)
    void goToSetSchedulePage(){
        startActivity(new Intent(ViewOrderActivity.this, SetScheduleActivity.class));
    }
}
