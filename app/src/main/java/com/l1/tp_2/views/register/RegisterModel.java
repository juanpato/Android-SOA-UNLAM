package com.l1.tp_2.views.register;

import com.l1.tp_2.clients.RegisterClient;
import com.l1.tp_2.clients.SendEventClient;
import com.l1.tp_2.entities.Event;
import com.l1.tp_2.entities.TypeEvent;
import com.l1.tp_2.entities.UserResponse;
import com.l1.tp_2.utils.Run;

import java.util.function.Consumer;

public class RegisterModel implements RegisterContract.Model {

    private final RegisterClient registerClient;
    private final SendEventClient sendEventClient;

    public RegisterModel(Consumer<UserResponse> onSuccess, Run onFailure) {
        registerClient = new RegisterClient(onSuccess, onFailure);
        sendEventClient = new SendEventClient(r -> System.out.print("Event sent"), () -> System.out.print("Fail sending event"));
    }

    @Override
    public void register(String name, String lastname, String email, Integer dni, String password) {
        registerClient.register(name, lastname, email, dni, password);
    }

    @Override
    public void registerRegisterEvent(UserResponse response) {
        Event event = new Event()
                .setTypeEvents(TypeEvent.REGISTER)
                .setDescription("Register successful");
        sendEventClient.sendEvent(response.getToken(), event);
    }
}
