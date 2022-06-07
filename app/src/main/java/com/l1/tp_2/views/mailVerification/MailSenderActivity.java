package com.l1.tp_2.views.mailVerification;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.l1.tp_2.R;
import com.l1.tp_2.views.BasicActivity;
import com.l1.tp_2.views.password_login.PasswordLoginActivity;

public class MailSenderActivity extends BasicActivity implements MailSenderContract.View{
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
        presenter = new MailSenderPresenter(this);
        codigo = presenter.GeneratePassword();
        send = (Button) this.findViewById(R.id.send);
        verifBtn = (Button) this.findViewById(R.id.verifBtn);
        emailTo = this.findViewById(R.id.emailTo);
        codigoVerif = this.findViewById(R.id.codigoText);


        verifBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (codigo.equals(codigoVerif.getText().toString())) {
                    openNewActivity();
                } else {
                    EnableButton();
                }
            }
        });
        send.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onClick(View v) {
                codigo = presenter.SendEmail(emailTo.getText().toString());
            }
        });

    }

    @Override
    public void onError() {
        setErrorMessage(error, "Los codigos no coinciden");
        codigoVerif.setBackgroundResource(R.color.red);
    }

    @Override
    public void onSuccess() {
        setErrorMessage(error, "");
        codigoVerif.setBackgroundResource(R.color.white);
    }

    public void openNewActivity() {
        Intent intent = new Intent(this, PasswordLoginActivity.class);
        intent.putExtra(EMAIL_KEY, emailTo.getText().toString());
        startActivity(intent);
    }
    @Override
    public void EnableButton(){
        send.setEnabled(true);
        setErrorMessage(error, "Los codigos no coinciden");
    }
    @Override
    public void DisableButton() {
        send.setEnabled(false);
        setErrorMessage(error, "");
    }


}