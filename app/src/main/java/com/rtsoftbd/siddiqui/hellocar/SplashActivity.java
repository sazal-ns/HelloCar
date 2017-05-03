package com.rtsoftbd.siddiqui.hellocar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import com.rtsoftbd.siddiqui.hellocar.models.CarType;
import com.rtsoftbd.siddiqui.hellocar.models.DurationAndCost;
import com.rtsoftbd.siddiqui.hellocar.models.UsingType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity";

    private static final String SP = "ms";
    private static final String PREF_KEY_SHORTCUT_ADDED = "PREF_KEY_SHORTCUT_ADDED";

    @BindView(R.id.hotlineTitleTextView) TextView ms_HotlineTitleTextView;
    @BindView(R.id.progressBar) ProgressBar ms_ProgressBar;

    private SharedPreferences.Editor editor;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        ms_ProgressBar.setVisibility(View.INVISIBLE);

        sp = getSharedPreferences(SP, MODE_PRIVATE);
        editor = sp.edit();

        addShortCut();

        new MaterialDialog.Builder(this)
                .title(R.string.title)
                .items(R.array.language)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        String language;
                        if (which == 0) language = "en";
                        else language = "bn";

                        Locale locale = new Locale(language);
                        Locale.setDefault(locale);

                        Configuration configuration = new Configuration();
                        configuration.locale = locale;
                        getBaseContext().getResources().updateConfiguration(configuration,
                                getBaseContext().getResources().getDisplayMetrics());

                        editor.putString("language", language);
                        editor.apply();

                        ms_ProgressBar.setVisibility(View.VISIBLE);
                        loadDate();
                        /*startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        finish();*/

                        return true;
                    }
                })
                .positiveText(R.string.choose)
                .cancelable(false)
                .show();


    }

    private void loadDate() {
        final StringRequest requestCarType = new StringRequest(Request.Method.POST, Boo.MS_BAG, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonObject = array.getJSONObject(i);
                        CarType carType = new CarType(jsonObject.getString(Boo.REPLAY_CAR_TYPE_NAME),
                                jsonObject.getInt(Boo.REPLAY_CAR_TYPE_ID));
                        CarType.setCarTypes(carType);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.getMessage().contains("Unable to resolve host"))
                    new Messages(SplashActivity.this).noInternet();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(Boo.KEY_TABLE, Boo.CAR_TYPE);

                return params;
            }
        };
        ApplicationController.getInstance().addToRequestQueue(requestCarType, TAG);

        final StringRequest requestDurationAndCost = new StringRequest(Request.Method.POST, Boo.MS_BAG, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonObject = array.getJSONObject(i);
                        DurationAndCost durationAndCost = new DurationAndCost(jsonObject.getString(Boo.REPLAY_DURATION_NAME), jsonObject.getInt(Boo.REPLAY_DURATION_ID),
                                jsonObject.getInt(Boo.REPLAY_DURATION_TYPE_ID), jsonObject.getInt(Boo.REPLAY_COST));
                        DurationAndCost.setDurationAndCosts(durationAndCost);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.getMessage().contains("Unable to resolve host"))
                    new Messages(SplashActivity.this).noInternet();
                error.printStackTrace();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(Boo.KEY_TABLE, Boo.DURATION);

                return params;
            }
        };

        ApplicationController.getInstance().addToRequestQueue(requestDurationAndCost, TAG);

        final StringRequest requestUsingType = new StringRequest(Request.Method.POST, Boo.MS_BAG, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonObject = array.getJSONObject(i);
                        UsingType usingType = new UsingType(jsonObject.getInt(Boo.REPLAY_USING_TYPE_ID), jsonObject.getString(Boo.REPLAY_USING_TYPE_NAME));
                        UsingType.setUsingTypes(usingType);

                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.getMessage().contains("Unable to resolve host"))
                    new Messages(SplashActivity.this).noInternet();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(Boo.KEY_TABLE, Boo.USING_TYPE);

                return params;
            }
        };
        ApplicationController.getInstance().addToRequestQueue(requestUsingType, TAG);
    }

    @OnClick(R.id.hotlineTitleTextView)
    public void onViewClicked() {
        new CallHotLine(SplashActivity.this);
    }

    private void addShortCut() {
        boolean shortCutWasAlreadyAdded = sp.getBoolean(PREF_KEY_SHORTCUT_ADDED, false);
        if (shortCutWasAlreadyAdded) return;

        Intent shortCut = new Intent(getApplicationContext(), SplashActivity.class);
        shortCut.setAction(Intent.ACTION_MAIN);

        Intent addIntent = new Intent();
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortCut);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, getResources().getString(R.string.app_name));
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                Intent.ShortcutIconResource.fromContext(getApplicationContext(), R.mipmap.ic_launcher));
        addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        addIntent.putExtra("duplicate", false);
        getApplicationContext().sendBroadcast(addIntent);

        Toast.makeText(getApplicationContext(), "Shortcut Added", Toast.LENGTH_SHORT).show();

        editor.putBoolean(PREF_KEY_SHORTCUT_ADDED, true);
        editor.apply();

    }
}
