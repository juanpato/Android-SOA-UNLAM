package com.l1.tp_2.views.login_historic;

import android.content.SharedPreferences;

import com.l1.tp_2.entities.LoginHistoric;

import java.util.List;

public class LoginHistoricPresenter implements LoginHistoricContract.Presenter {

    private LoginHistoricContract.Model model;

    public LoginHistoricPresenter(SharedPreferences sharedPreferences) {
        this.model = new LoginHistoricModel(sharedPreferences);
    }

    @Override
    public List<LoginHistoric> onLoadData() {
        return model.getLoginHistoric();
    }
}
