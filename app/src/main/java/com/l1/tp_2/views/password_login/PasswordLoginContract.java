package com.l1.tp_2.views.password_login;

import com.l1.tp_2.entities.LoginHistoric;
import com.l1.tp_2.entities.UserResponse;

import java.util.List;

public interface PasswordLoginContract {

    interface View {
        void onError();

        void onSuccess(UserResponse userResponse);
    }

    interface Model {
        void login(String email, String password);

        void registerLoginEvent(UserResponse response);

        List<LoginHistoric> getLoginHistoric();

        void saveLoginHistoric(List<LoginHistoric> historic);
    }

    interface Presenter {
        void onButtonClick(String email, String password);

        void registerLoginEvent(UserResponse response);

        void saveLoginHistoric(String email, UserResponse response);
    }

}
