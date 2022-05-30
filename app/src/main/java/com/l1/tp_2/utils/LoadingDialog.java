package com.l1.tp_2.utils;

import android.app.Activity;
import android.app.AlertDialog;

import com.l1.tp_2.R;

public class LoadingDialog {

    private final Activity activity;
    private AlertDialog alertDialog;

    public LoadingDialog(Activity activity) {
        this.activity = activity;
    }

    public void showLoading() {
        alertDialog = new AlertDialog.Builder(activity)
                .setView(activity.getLayoutInflater().inflate(R.layout.loading_dialog, null))
                .setCancelable(false)
                .create();

        alertDialog.show();
    }

    public void dismissLoading() {
        alertDialog.dismiss();
    }
}
