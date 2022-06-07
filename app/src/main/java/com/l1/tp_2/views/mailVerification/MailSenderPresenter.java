package com.l1.tp_2.views.mailVerification;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.l1.tp_2.utils.GMailSender;
import com.l1.tp_2.utils.Run;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class MailSenderPresenter implements MailSenderContract.Presenter{
    private final MailSenderContract.Model model;
    private Run _disableButton;

    public MailSenderPresenter(MailSenderContract.View view){
        this.model = new MailSenderModel();
        Run _enableButton = view::EnableButton;
        _disableButton = view::DisableButton;
    }

    @Override
    public String GeneratePassword() {
        return GeneratePassword.randomString(30);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String SendEmail(String emailTo){
        final String codigo = GeneratePassword.randomString(30);
        try {
            GMailSender sender = new GMailSender("soal1tp2@gmail.com", "cbookarmvhmkhhhv");
            String password = GeneratePassword.randomString(10);
            CompletableFuture.supplyAsync(() -> {
                try {
                    sender.sendMail("Codigo de verificación - Iniciar Sesion",
                            "Codigo de verificacion: " + password,
                            "soal1tp2@gmail.com",
                            emailTo);
                    _disableButton.run();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return password;
            });
            return password;
        } catch (Exception e) {
            Log.e("SendMail", e.getMessage(), e);
        }
        return codigo;
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
