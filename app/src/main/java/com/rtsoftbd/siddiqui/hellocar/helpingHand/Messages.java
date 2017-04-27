package com.rtsoftbd.siddiqui.hellocar.helpingHand;

import android.app.Activity;
import android.graphics.drawable.Drawable;

import com.afollestad.materialdialogs.MaterialDialog;
import com.rtsoftbd.siddiqui.hellocar.R;

/**
 * Created by RTsoftBD_Siddiqui on 2017-04-24.
 */

public class Messages {
    private Activity activity;
    private String title, message;
    private boolean cancel;
    private Drawable icon;

    public Messages(Activity activity) {
        this.activity = activity;
    }

    public Messages(Activity activity, String title, String message, boolean cancel) {
        this.activity = activity;
        this.title = title;
        this.message = message;
        this.cancel = cancel;
        showMessage();
    }

    public Messages(Activity activity, String title, String message, boolean cancel, Drawable icon) {
        this.activity = activity;
        this.title = title;
        this.message = message;
        this.cancel = cancel;
        this.icon = icon;
    }

    public Messages(Activity activity, String title, String message, Drawable icon) {
        this.activity = activity;
        this.title = title;
        this.message = message;
        this.icon = icon;
    }

    private void showMessage(){
        new MaterialDialog.Builder(activity)
                .cancelable(cancel)
                .title(title)
                .content(message)
                .icon(icon)
                .show();
    }

    public void NoInternet(){
        new MaterialDialog.Builder(activity)
                .title(activity.getResources().getString(R.string.error))
                .titleColor(activity.getResources().getColor(R.color.red))
                .icon(activity.getResources().getDrawable(R.drawable.ic_error_red_a700_36dp))
                .content(activity.getResources().getString(R.string.no_active_internet))
                .cancelable(true)
                .show();
    }
}
