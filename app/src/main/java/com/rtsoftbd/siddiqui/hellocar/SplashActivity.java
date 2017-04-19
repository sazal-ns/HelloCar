package com.rtsoftbd.siddiqui.hellocar;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v13.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity";

    private static final String SP = "ms";
    private static final String PREF_KEY_SHORTCUT_ADDED = "PREF_KEY_SHORTCUT_ADDED";

    private static final int CALL_PERMISSION_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    private boolean sentToSettings = false;
    private Intent callIntent;

    @BindView(R.id.hotlineTitleTextView) TextView ms_HotlineTitleTextView;

    private SharedPreferences.Editor editor;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

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

                        // TODO: Load all categories here
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        finish();

                        return true;
                    }
                })
                .positiveText(R.string.choose)
                .show();


    }

    @OnClick(R.id.hotlineTitleTextView)
    public void onViewClicked() {
        String number[] = ms_HotlineTitleTextView.getText().toString().trim().split(" ");

        callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+number[1]));

        if (ActivityCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(SplashActivity.this, Manifest.permission.CALL_PHONE)){
                AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
                builder.setTitle("Need Call Phone Permission");
                builder.setMessage("To directly dial number, this app needs call phone permission.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.CALL_PHONE},
                                CALL_PERMISSION_CONSTANT);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }else if (sp.getBoolean(Manifest.permission.CALL_PHONE,false)){
                AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
                builder.setTitle("Need Call Phone Permission");
                builder.setMessage("To directly dial number, this app needs call phone permission.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        sentToSettings = true;
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                        Toast.makeText(getBaseContext(), "Go to Permissions to Grant Storage", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }else {
                ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.CALL_PHONE},
                        CALL_PERMISSION_CONSTANT);
            }
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean(Manifest.permission.CALL_PHONE,true);
            editor.apply();
        }else call();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CALL_PERMISSION_CONSTANT){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                call();
            }else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(SplashActivity.this, Manifest.permission.CALL_PHONE)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
                    builder.setTitle("Need Call Phone Permission");
                    builder.setMessage("To directly dial number, this app needs call phone permission.");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();

                            ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    CALL_PERMISSION_CONSTANT);

                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                }else new MaterialDialog.Builder(SplashActivity.this)
                            .title("Sorry")
                            .content("We Can't Call Directly from app.")
                            .show();

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION_SETTING){
            if (ActivityCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
                call();
            }
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (sentToSettings){
            if (ActivityCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                call();
            }
        }
    }

    private void call(){
       new MaterialDialog.Builder(this)
                .content(R.string.call_hotline)
                .positiveText("Call")
                .positiveColor(Color.parseColor("#6dc390"))
                .negativeText("Cancel")
                .negativeColor(Color.RED)
                .cancelable(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                        startActivity(callIntent);
                    }
                })
                .show();
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
                Intent.ShortcutIconResource.fromContext(getApplicationContext(), R.mipmap.ic_launcher_icon));
        addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        addIntent.putExtra("duplicate", false);
        getApplicationContext().sendBroadcast(addIntent);

        Toast.makeText(getApplicationContext(), "Shortcut Added", Toast.LENGTH_SHORT).show();

        editor.putBoolean(PREF_KEY_SHORTCUT_ADDED, true);
        editor.apply();

    }
}
