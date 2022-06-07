package com.l1.tp_2.views.login_historic;

import static com.l1.tp_2.views.password_login.PasswordLoginModel.LOGIN_HISTORY_KEY;

import android.content.SharedPreferences;

import com.l1.tp_2.entities.LoginHistoric;
import com.l1.tp_2.utils.JsonUtils;

import java.util.List;

public class LoginHistoricModel implements LoginHistoricContract.Model {

    private final SharedPreferences sharedPreferences;

    public LoginHistoricModel(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public List<LoginHistoric> getLoginHistoric() {
        return JsonUtils.fromJsonList(sharedPreferences.getString(LOGIN_HISTORY_KEY, "[]"), LoginHistoric.class);
    }
}
