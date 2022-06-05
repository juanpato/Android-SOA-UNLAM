package com.l1.tp_2.views;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public abstract class BasicActivity extends AppCompatActivity {

    public static final String TOKEN_KEY = "token";

    protected boolean checkInternetConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        return isConnected(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)) ||
                isConnected(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI));
    }

    private boolean isConnected(NetworkInfo networkInfo) {
        return NetworkInfo.State.CONNECTED.equals(networkInfo.getState());
    }

    protected void setErrorMessage(TextView errorField, String message) {
        errorField.setText(message);
        errorField.setTextColor(Color.RED);

    }

}
