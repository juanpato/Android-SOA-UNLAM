package com.l1.tp_2.views.password_login;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;

import com.l1.tp_2.entities.LoginHistoric;
import com.l1.tp_2.entities.UserResponse;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PasswordLoginPresenter implements PasswordLoginContract.Presenter {

    private final PasswordLoginContract.Model model;
    @SuppressLint("SimpleDateFormat")
    private final DateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");

    public PasswordLoginPresenter(PasswordLoginContract.View view, SharedPreferences sharedPreferences) {
        this.model = new PasswordLoginModel(view::onSuccess, view::onError, sharedPreferences);
    }


    @Override
    public void onButtonClick(String email, String password) {
        model.login(email, password);
    }

    @Override
    public void registerLoginEvent(UserResponse response) {
        model.registerLoginEvent(response);
    }

    @Override
    public void saveLoginHistoric(String email, UserResponse response) {
        List<LoginHistoric> loginHistoric = model.getLoginHistoric();

        for (int i = loginHistoric.size() - 1; i >= 0; i--) {
            if (email.equals(loginHistoric.get(i).getFullName())) {
                loginHistoric.remove(i);
            }
        }

        loginHistoric.add(new LoginHistoric(email, simpleDateFormat.format(new Date())));

        model.saveLoginHistoric(loginHistoric);
    }
}
