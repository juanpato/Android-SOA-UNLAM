package com.l1.tp_2.clients.interfaces;

import com.l1.tp_2.entities.User;
import com.l1.tp_2.entities.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Register {

    @POST("api/api/register")
    Call<UserResponse> register(@Body User user);

}
