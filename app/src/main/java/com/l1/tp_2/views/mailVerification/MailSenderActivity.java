package com.l1.tp_2.views.mailVerification;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.l1.tp_2.R;
import com.l1.tp_2.utils.GMailSender;
import com.l1.tp_2.views.BasicActivity;
import com.l1.tp_2.views.password_login.PasswordLoginActivity;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class MailSenderActivity extends BasicActivity {
    private TextView emailTo;
    private TextView codigoVerif;
    private String codigo;
    private Button send;
    private Button verifBtn;

    public void openNewActivity() {
        Intent intent = new Intent(this, PasswordLoginActivity.class);
        intent.putExtra(EMAIL_KEY, emailTo.getText().toString());
        startActivity(intent);
    }

    public void disableButton() {
        send.setEnabled(false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail_sender);
        codigo = GeneratePassword.randomString(20);
        send = (Button) this.findViewById(R.id.send);
        verifBtn = (Button) this.findViewById(R.id.verifBtn);

        emailTo = this.findViewById(R.id.emailTo);
        codigoVerif = this.findViewById(R.id.codigoText);
        verifBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (codigo.equals(codigoVerif.getText().toString())) {
                    openNewActivity();
                } else {
                    //send.setEnabled(true);
                }
            }
        });
        send.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onClick(View v) {
                try {
                    GMailSender sender = new GMailSender("soal1tp2@gmail.com", "cbookarmvhmkhhhv");
                    String password = GeneratePassword.randomString(10);

                    CompletableFuture.supplyAsync(() -> {
                        try {
                            //disableButton();
                            codigo = password;
                            sender.sendMail("Codigo de verificación - Iniciar Sesion",
                                    "Codigo de verificacion: " + password,
                                    "soal1tp2@gmail.com",
                                    emailTo.getText().toString());

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    });
                } catch (Exception e) {
                    Log.e("SendMail", e.getMessage(), e);
                }

            }
        });

    }

    private static class GeneratePassword {

        /**
         * Genera una password RANDOM
         */

        public static final String DATA = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz|!£$%&/=@#";
        public static Random RANDOM = new Random();

        public static String randomString(int len) {
            StringBuilder sb = new StringBuilder(len);

            for (int i = 0; i < len; i++) {
                sb.append(DATA.charAt(RANDOM.nextInt(DATA.length())));
            }

            return sb.toString();
        }

    }
}