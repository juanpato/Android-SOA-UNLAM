package com.l1.tp_2.clients.interfaces;

import com.l1.tp_2.entities.Event;
import com.l1.tp_2.entities.EventResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface SendEvent {

    @POST("api/api/event")
    Call<EventResponse> sendEvent(@Header("token") String token, @Body Event event);

}
