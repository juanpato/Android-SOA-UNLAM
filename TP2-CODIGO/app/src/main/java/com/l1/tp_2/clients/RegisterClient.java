package com.l1.tp_2.clients;

import android.annotation.SuppressLint;

import com.l1.tp_2.clients.interfaces.Register;
import com.l1.tp_2.entities.Env;
import com.l1.tp_2.entities.User;
import com.l1.tp_2.entities.UserResponse;
import com.l1.tp_2.utils.Run;

import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterClient extends RetrofitClient<Register> {

    private Consumer<UserResponse> onSuccess;
    private Run onFailure;

    public RegisterClient(Consumer<UserResponse> onSuccess, Run onFailure) {
        super(Register.class);
        this.onSuccess = onSuccess;
        this.onFailure = onFailure;
    }

    @Override
    protected String getBaseUrl() {
        return "http://so-unlam.net.ar/";
    }

    public void register(String name, String lastname, String email, Integer dni, String password) {
        Call<UserResponse> call = getClient().register(new User()
                .setEnv(Env.DEV)
                .setName(name)
                .setLastname(lastname)
                .setEmail(email)
                .setDni(dni)
                .setCommission(1900)
                .setGroup(1) // L1 ?
                .setPassword(password));

        call.enqueue(new Callback<UserResponse>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                if (response.isSuccessful() && response.body().getSuccess()) {
                    onSuccess.accept(response.body());
                } else {
                    onFailure.run();
                }

            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                onFailure.run();
            }
        });
    }

}
