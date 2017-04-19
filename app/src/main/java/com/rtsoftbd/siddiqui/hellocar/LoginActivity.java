package com.rtsoftbd.siddiqui.hellocar;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.usernameEditText) EditText ms_UsernameEditText;
    @BindView(R.id.passwordEditText) EditText ms_PasswordEditText;
    @BindView(R.id.loginAppCompatButton) AppCompatButton ms_LoginAppCompatButton;
    @BindView(R.id.forgetTextView) TextView ms_ForgetTextView;
    @BindView(R.id.hotlineTitleTextView) TextView ms_HotlineTitleTextView;
    @BindView(R.id.regTextView) TextView ms_RegTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.loginAppCompatButton, R.id.forgetTextView, R.id.regTextView, R.id.hotlineTitleTextView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.loginAppCompatButton:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.forgetTextView:
                break;
            case R.id.regTextView:
                startActivity(new Intent(this, RegistrationActivity.class));
                break;
            case R.id.hotlineTitleTextView:
                break;
        }
    }
}
