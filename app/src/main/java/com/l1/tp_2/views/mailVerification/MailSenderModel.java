package com.l1.tp_2.views.mailVerification;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.l1.tp_2.utils.GMailSender;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class MailSenderModel implements MailSenderContract.Model {

    private final Consumer<String> onSuccess;
    private final GMailSender emailSender;

    public MailSenderModel(Consumer<String> onSuccess) {
        this.onSuccess = onSuccess;
        this.emailSender = new GMailSender("soal1tp2@gmail.com", "cbookarmvhmkhhhv");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void sendEmail(String code, String emailTo) {
        try {
            CompletableFuture.runAsync(() -> {
                try {
                    emailSender.sendMail("Codigo de verificación - Iniciar Sesion",
                            "Codigo de verificacion: " + code,
                            "soal1tp2@gmail.com",
                            emailTo);

                    onSuccess.accept(code);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            Log.e("SendMail", e.getMessage(), e);
        }
    }
}
