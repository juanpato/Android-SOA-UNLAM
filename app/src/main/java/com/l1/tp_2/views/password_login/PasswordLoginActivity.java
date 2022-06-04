package com.l1.tp_2.views.password_login;

import static com.l1.tp_2.views.register.RegisterActivity.TOKEN_KEY;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.l1.tp_2.R;
import com.l1.tp_2.entities.UserResponse;
import com.l1.tp_2.utils.LoadingDialog;
import com.l1.tp_2.views.login_historic.LoginHistoricActivity;

public class PasswordLoginActivity extends AppCompatActivity implements PasswordLoginContract.View {

    public static final String LOGIN_HISTORY_PREFERENCES = "login_history_preferences";

    private PasswordLoginContract.Presenter presenter;

    private TextView email;
    private TextView password;
    private TextView error;
    private Button button;

    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_login);
        presenter = new PasswordLoginPresenter(this, getSharedPreferences(LOGIN_HISTORY_PREFERENCES, Context.MODE_PRIVATE));

        loadingDialog = new LoadingDialog(this);

        button = findViewById(R.id.button);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        error = findViewById(R.id.error);
        error.setTextColor(Color.WHITE);
        button.setOnClickListener(view -> {
            error.setTextColor(Color.WHITE);
            email.setBackgroundResource(R.color.white);
            password.setBackgroundResource(R.color.white);
            loadingDialog.showLoading();
            presenter.onButtonClick(email.getText().toString(), password.getText().toString());
        });
    }

    @Override
    public void onSuccess(UserResponse response) {
        loadingDialog.dismissLoading();

        presenter.registerLoginEvent(response);

        presenter.saveLoginHistoric(email.getText().toString(), response);

        Intent intent = new Intent(this, LoginHistoricActivity.class);
        intent.putExtra(TOKEN_KEY, response.getToken());
        startActivity(intent);
    }

    @Override
    public void onError() {
        loadingDialog.dismissLoading();
        error.setTextColor(Color.RED);
        email.setBackgroundResource(R.color.red);
        password.setBackgroundResource(R.color.red);
    }
}