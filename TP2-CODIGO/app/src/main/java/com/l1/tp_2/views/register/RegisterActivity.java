package com.l1.tp_2.views.register;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.widget.Button;
import android.widget.TextView;

import com.l1.tp_2.R;
import com.l1.tp_2.entities.UserResponse;
import com.l1.tp_2.utils.LoadingDialog;
import com.l1.tp_2.views.BasicActivity;
import com.l1.tp_2.views.password_login.PasswordLoginActivity;

import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

public class RegisterActivity extends BasicActivity implements RegisterContract.View {

    private RegisterContract.Presenter presenter;

    private TextView name;
    private TextView lastnameText;
    private TextView email;
    private TextView dniText;
    private TextView password;
    private TextView errorMessage;
    private Button button;

    private LoadingDialog loadingDialog;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        presenter = new RegisterPresenter(this);

        loadingDialog = new LoadingDialog(this);

        name = findViewById(R.id.name_text);
        lastnameText = findViewById(R.id.lastname_text);
        email = findViewById(R.id.email_text);
        dniText = findViewById(R.id.dni_text);
        password = findViewById(R.id.password);
        errorMessage = findViewById(R.id.error_message);

        button = findViewById(R.id.confirm_button);

        String emailText = (String) Optional.ofNullable(this.getIntent().getExtras())
                .map(extras -> extras.get(EMAIL_KEY))
                .orElse("");

        if (!"".equals(emailText)) {
            email.setText(emailText);
            email.setFocusable(false);
            email.setBackgroundResource(R.color.grey);
        }

        button.setOnClickListener(view -> {
            if (!checkInternetConnection()) {
                setErrorMessage(errorMessage, "No hay conexi??n a internet");
                return;
            }

            errorMessage.setTextColor(Color.WHITE);
            password.setBackgroundResource(R.color.white);
            loadingDialog.showLoading();

            String dni = dniText.getText().toString();
            presenter.onButtonClick(
                    name.getText().toString(),
                    lastnameText.getText().toString(),
                    email.getText().toString(),
                    StringUtils.isNotEmpty(dni) ? Integer.valueOf(dni) : null,
                    password.getText().toString()
            );
        });
    }

    @Override
    public void onSuccess(UserResponse response) {
        loadingDialog.dismissLoading();

        presenter.registerRegisterEvent(response);

        Intent intent = new Intent(this, PasswordLoginActivity.class);
        intent.putExtra(EMAIL_KEY, email.getText().toString());
        startActivity(intent);
    }

    @Override
    public void onError() {
        loadingDialog.dismissLoading();
        setErrorMessage(errorMessage, "Error en los datos, reviselos e intente nuevamente");
        dniText.setBackgroundResource(R.color.red);
        password.setBackgroundResource(R.color.red);
    }
}