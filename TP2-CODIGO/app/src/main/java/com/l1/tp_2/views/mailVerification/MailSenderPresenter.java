package com.l1.tp_2.views.mailVerification;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.Random;

public class MailSenderPresenter implements MailSenderContract.Presenter {
    private final MailSenderContract.Model model;

    public MailSenderPresenter(MailSenderContract.View view) {
        this.model = new MailSenderModel(view::onSuccess);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void sendEmail(String emailTo) {
        final String password = GeneratePassword.randomString(10);
        model.sendEmail(password, emailTo);
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
