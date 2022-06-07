package com.l1.tp_2.clients.interfaces;

import com.l1.tp_2.entities.User;
import com.l1.tp_2.entities.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Login {

    @POST("api/api/login")
    Call<UserResponse> login(@Body User user);

}
