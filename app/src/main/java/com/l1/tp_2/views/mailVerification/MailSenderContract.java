package com.l1.tp_2.views.mailVerification;

import com.l1.tp_2.entities.LoginHistoric;
import com.l1.tp_2.entities.UserResponse;

import java.util.List;

public interface MailSenderContract {

    interface View {
        void onError();
        void onSuccess();
        void EnableButton();
        void DisableButton();
    }

    interface Model {

    }

    interface Presenter {
        String GeneratePassword();
        String SendEmail(String emailTo);
    }
}
