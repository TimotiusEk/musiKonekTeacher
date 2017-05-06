package com.example.timotiusek.musikonekteacher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInActivity extends AppCompatActivity {

    TextView linkToRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.sign_in_btn)
    void signIn(){
        startActivity(new Intent(this, MainActivity.class));
    }
    @OnClick(R.id.link_to_register)
    void clickToRegisterPage(){
        startActivity(new Intent(this, SignUpActivity.class));
    }
}
