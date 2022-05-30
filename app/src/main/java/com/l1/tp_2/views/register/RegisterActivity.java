package com.l1.tp_2.views.register;

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

import org.apache.commons.lang3.StringUtils;

public class RegisterActivity extends AppCompatActivity implements RegisterContract.View {

    public static final String TOKEN_KEY = "token";
    private RegisterContract.Presenter presenter;

    private TextView name;
    private TextView lastnameText;
    private TextView emailText;
    private TextView dniText;
    private TextView password;
    private TextView errorMessage;
    private Button button;

    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        presenter = new RegisterPresenter(this);

        loadingDialog = new LoadingDialog(this);

        name = findViewById(R.id.name_text);
        lastnameText = findViewById(R.id.lastname_text);
        emailText = findViewById(R.id.email_text);
        dniText = findViewById(R.id.dni_text);
        password = findViewById(R.id.password);
        errorMessage = findViewById(R.id.error_message);

        button = findViewById(R.id.confirm_button);

        button.setOnClickListener(view -> {
            errorMessage.setTextColor(Color.WHITE);
            emailText.setBackgroundResource(R.color.white);
            password.setBackgroundResource(R.color.white);
            loadingDialog.showLoading();

            String dni = dniText.getText().toString();
            presenter.onButtonClick(
                    name.getText().toString(),
                    lastnameText.getText().toString(),
                    emailText.getText().toString(),
                    StringUtils.isNotEmpty(dni) ? Integer.valueOf(dni) : null,
                    password.getText().toString()
            );
        });
    }

    @Override
    public void onSuccess(UserResponse response) {
        loadingDialog.dismissLoading();

        presenter.registerRegisterEvent(response);

        Intent intent = new Intent(this, LoginHistoricActivity.class);
        intent.putExtra(TOKEN_KEY, response.getToken());
        startActivity(intent);
    }

    @Override
    public void onError() {
        loadingDialog.dismissLoading();
        errorMessage.setTextColor(Color.RED);
        emailText.setBackgroundResource(R.color.red);
        password.setBackgroundResource(R.color.red);
    }
}