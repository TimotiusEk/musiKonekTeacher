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
    @OnClick(R.id.reject_btn_view_order_page)
    void goToRejectReasonPage(){
        startActivity(new Intent(this, ReasonRejectActivity.class));
    }

    @OnClick(R.id.accept_btn_view_order_page)
    void goToStudentInfoPage(){
        startActivity(new Intent(this, StudentInfoActivity.class));
    }

}
