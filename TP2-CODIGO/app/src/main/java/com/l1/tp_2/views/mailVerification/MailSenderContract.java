package com.l1.tp_2.views.mailVerification;

public interface MailSenderContract {

    interface View {
        void onError();

        void onSuccess(String code);
    }

    interface Model {
        void sendEmail(String code, String emailTo);
    }

    interface Presenter {
        void sendEmail(String emailTo);
    }
}
