package com.l1.tp_2.views.mailVerification;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.l1.tp_2.R;
import com.l1.tp_2.views.BasicActivity;
import com.l1.tp_2.views.password_login.PasswordLoginActivity;

public class MailSenderActivity extends BasicActivity implements MailSenderContract.View {
    private TextView emailTo;
    private TextView codigoVerif;
    private String codigo;
    private Button send;
    private Button verifBtn;
    private TextView error;
    private MailSenderContract.Presenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail_sender);
        error = findViewById(R.id.errorFA);
        error.setTextColor(Color.WHITE);
        codigo = "";
        presenter = new MailSenderPresenter(this);
        send = (Button) this.findViewById(R.id.send);
        verifBtn = (Button) this.findViewById(R.id.verifBtn);
        emailTo = this.findViewById(R.id.emailTo);
        codigoVerif = this.findViewById(R.id.codigoText);


        verifBtn.setOnClickListener(v -> {
            if (!codigo.isEmpty() && codigo.equals(codigoVerif.getText().toString())) {
                openNewActivity();
            } else {
                onError();
            }
        });
        send.setOnClickListener(v -> {
            DisableButton();
            presenter.sendEmail(emailTo.getText().toString());
        });

    }

    @Override
    public void onError() {
        setErrorMessage(error, "Los codigos no coinciden");
        EnableButton();
    }

    @Override
    public void onSuccess(String code) {
        this.codigo = code;
        setErrorMessage(error, "");
    }

    public void openNewActivity() {
        Intent intent = new Intent(this, PasswordLoginActivity.class);
        intent.putExtra(EMAIL_KEY, emailTo.getText().toString());
        startActivity(intent);
    }

    public void EnableButton() {
        send.setEnabled(true);
    }

    public void DisableButton() {
        send.setEnabled(false);
    }


}