package com.l1.tp_2.views.register;

import com.l1.tp_2.entities.LoginHistoric;
import com.l1.tp_2.entities.UserResponse;

import java.util.List;

public interface RegisterContract {

    interface View {
        void onError();

        void onSuccess(UserResponse userResponse);
    }

    interface Model {
        void register(String name, String lastname, String email, Integer dni, String password);

        void registerRegisterEvent(UserResponse response);
    }

    interface Presenter {
        void onButtonClick(String name, String lastname, String email, Integer dni, String password);

        void registerRegisterEvent(UserResponse response);
    }

}
