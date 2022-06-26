package com.l1.tp_2.clients;

import android.annotation.SuppressLint;

import com.l1.tp_2.clients.interfaces.SendEvent;
import com.l1.tp_2.entities.Event;
import com.l1.tp_2.entities.EventResponse;
import com.l1.tp_2.utils.Run;

import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendEventClient extends RetrofitClient<SendEvent> {

    private Consumer<EventResponse> onSuccess;
    private Run onFailure;

    public SendEventClient(Consumer<EventResponse> onSuccess, Run onFailure) {
        super(SendEvent.class);
        this.onSuccess = onSuccess;
        this.onFailure = onFailure;
    }

    @Override
    protected String getBaseUrl() {
        return "http://so-unlam.net.ar/";
    }

    public void sendEvent(String token, Event event) {
        Call<EventResponse> call = getClient().sendEvent(String.format("Bearer %s", token), event);

        call.enqueue(new Callback<EventResponse>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {

                if (response.isSuccessful() && response.body().getSuccess()) {
                    onSuccess.accept(response.body());
                } else {
                    onFailure.run();
                }

            }

            @Override
            public void onFailure(Call<EventResponse> call, Throwable t) {
                onFailure.run();
            }
        });
    }

}
