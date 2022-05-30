package com.l1.tp_2.views.password_login;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;

import com.l1.tp_2.clients.LoginClient;
import com.l1.tp_2.clients.SendEventClient;
import com.l1.tp_2.entities.Event;
import com.l1.tp_2.entities.LoginHistoric;
import com.l1.tp_2.entities.TypeEvent;
import com.l1.tp_2.entities.UserResponse;
import com.l1.tp_2.utils.JsonUtils;
import com.l1.tp_2.utils.Run;

import java.util.List;
import java.util.function.Consumer;

public class PasswordLoginModel implements PasswordLoginContract.Model {

    public static final String LOGIN_HISTORY_KEY = "login_history";

    private final LoginClient loginClient;
    private final SendEventClient sendEventClient;
    private final SharedPreferences sharedPreferences;

    public PasswordLoginModel(Consumer<UserResponse> onSuccess, Run onFailure, SharedPreferences sharedPreferences) {
        loginClient = new LoginClient(onSuccess, onFailure);
        sendEventClient = new SendEventClient(r -> System.out.print("Event sent"), () -> System.out.print("Fail sending event"));
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void login(String email, String password) {
        loginClient.login(email, password);

    }

    @Override
    public void registerLoginEvent(UserResponse response) {
        Event event = new Event()
                .setTypeEvents(TypeEvent.LOGIN)
                .setDescription("Login successful");
        sendEventClient.sendEvent(response.getToken(), event);
    }


    @Override
    public List<LoginHistoric> getLoginHistoric() {
        return JsonUtils.fromJsonList(sharedPreferences.getString(LOGIN_HISTORY_KEY, "[]"), LoginHistoric.class);
    }

    @SuppressLint("CommitPrefEdits")
    @Override
    public void saveLoginHistoric(List<LoginHistoric> historic) {
        sharedPreferences.edit().putString(LOGIN_HISTORY_KEY, JsonUtils.toJson(historic)).apply();
    }
}
