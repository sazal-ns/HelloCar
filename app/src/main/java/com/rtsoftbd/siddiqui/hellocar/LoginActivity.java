package com.rtsoftbd.siddiqui.hellocar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.Html;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.rtsoftbd.siddiqui.hellocar.helpingHand.ApplicationController;
import com.rtsoftbd.siddiqui.hellocar.helpingHand.Boo;
import com.rtsoftbd.siddiqui.hellocar.helpingHand.CallHotLine;
import com.rtsoftbd.siddiqui.hellocar.helpingHand.Messages;
import com.rtsoftbd.siddiqui.hellocar.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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

    private String userName, password;

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
                userName = ms_UsernameEditText.getText().toString().trim();
                password = ms_PasswordEditText.getText().toString().trim();
                if (isAuthorized()){

                }else new Messages(this, getResources().getString(R.string.error),
                        getResources().getString(R.string.user_and_pass_not_match)
                        ,getResources().getDrawable(R.drawable.ic_error_red_a700_36dp));
                break;
            case R.id.forgetTextView:
                create(LoginActivity.this).show();
                break;
            case R.id.regTextView:
                startActivity(new Intent(this, RegistrationActivity.class));
                finish();
                break;
            case R.id.hotlineTitleTextView:
                new CallHotLine(this);
                break;
        }
    }

    public static AlertDialog create(Context context) {
        final TextView message = new TextView(context);
        final SpannableString s = new SpannableString(context.getText(R.string.forget_password_info));
        Linkify.addLinks(s, Linkify.WEB_URLS);
        message.setText(s);
        message.setMovementMethod(LinkMovementMethod.getInstance());

        return new AlertDialog.Builder(context)
                .setCancelable(true)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setView(message)
                .setTitle(context.getResources().getString(R.string.forget_pass))
                .create();
    }
    private boolean isAuthorized() {
        final boolean[] authorized = {true};

        if (!isValid()){
            return false;
        }

        final StringRequest request = new StringRequest(Request.Method.POST, Boo.MS_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    if (response.contains("Username Password Missmatch"))
                        new Messages(LoginActivity.this, getResources().getString(R.string.error),
                                getResources().getString(R.string.user_and_pass_not_match), true);
                    else  if (response.contains("Block User")) new Messages(LoginActivity.this, getResources().getString(R.string.error),
                            getResources().getString(R.string.block_user_info), true);
                    else {
                        User.setFullName(object.getString(Boo.REPLAY_FULL_NAME));
                        User.setAddress(object.getString(Boo.REPLAY_USER_ADDRESS));
                        User.setEmail(object.getString(Boo.REPLAY_USER_EMAIL));
                        User.setEngContact(object.getInt(Boo.REPLAY_USER_EMERGENCY_NUMBER));
                        User.setMobile(object.getInt(Boo.REPLAY_MOBILE_NUMBER));
                        User.setnID(object.getInt(Boo.REPLAY_NID));
                        User.setUserID(object.getInt(Boo.REPLAY_LOGIN_USER_ID));
                        User.setUserName(object.getString(Boo.REPLAY_USER_NAME));
                        User.setPassword(object.getString(Boo.REPLAY_USER_PASSWORD));
                        User.setImageName(object.getString(Boo.REPLAY_USER_IMAGE));
                        User.setPin(object.getInt(Boo.REPLAY_USER_PIN));
                        User.setState(object.getInt(Boo.REPLAY_STATE));

                        ApplicationController.getInstance().cancelPendingRequests("GET");
                        if (User.getState() == Boo.INVALID_USER){
                            new MaterialDialog.Builder(LoginActivity.this)
                                    .title(getResources().getString(R.string.setPin))
                                    .input(getResources().getString(R.string.pinHint), null, false, new MaterialDialog.InputCallback() {
                                        @Override
                                        public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                            if (input.toString().trim().contentEquals(String.valueOf(User.getPin()).trim())){
                                                StringRequest pin = new StringRequest(Request.Method.POST, Boo.MS_UPDATE_PIN, new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {
                                                        if (!response.contains("true")) {
                                                            authorized[0] = false;
                                                        }else {
                                                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                                        finish();
                                                        }
                                                    }
                                                }, new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                        error.printStackTrace();
                                                        if (error.getMessage().contains("Unable to resolve host"))
                                                            new Messages(LoginActivity.this).NoInternet();
                                                        else new Messages(LoginActivity.this, getResources().getString(R.string.error),
                                                                getResources().getString(R.string.server_error), true);
                                                        authorized[0] = false;
                                                    }
                                                }){
                                                    @Override
                                                    protected Map<String, String> getParams() throws AuthFailureError {
                                                        Map<String , String > params = new HashMap<String, String>();
                                                        params.put(Boo.KEY_ID, String.valueOf(User.getUserID()));
                                                        return params;
                                                    }
                                                };

                                                ApplicationController.getInstance().addToRequestQueue(pin);
                                            }else new Messages(LoginActivity.this,null, getResources().getString(R.string.invalid_pin), true);
                                        }
                                    })
                                    .show();
                        }
                        else {
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    new Messages(LoginActivity.this, getResources().getString(R.string.error),
                            getResources().getString(R.string.server_error), true);
                    authorized[0] = false;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                if (error.getMessage().contains("Unable to resolve host"))
                    new Messages(LoginActivity.this).NoInternet();
                else new Messages(LoginActivity.this, getResources().getString(R.string.error),
                        getResources().getString(R.string.server_error), true);
                authorized[0] = false;
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String > params = new HashMap<>();
                params.put(Boo.KEY_LOGIN_USER_NAME, userName);
                params.put(Boo.KEY_LOGIN_PASSWORD, password);
                return params;
            }
        };

        ApplicationController.getInstance().addToRequestQueue(request , "GET");

        return authorized[0];
    }

    private boolean isValid() {
        boolean valid = true;

        if (userName.isEmpty()){
            ms_UsernameEditText.setError(getResources().getString(R.string.required));
            valid = false;
        }else ms_UsernameEditText.setError(null);

        if (password.isEmpty()){
            ms_PasswordEditText.setError(getResources().getString(R.string.required));
            valid = false;
        }else ms_PasswordEditText.setError(null);

        return valid;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.hotline, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.navigation_hotline:
                new CallHotLine(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
