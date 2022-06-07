package com.l1.tp_2.entities;

public class EventResponse {
    private boolean success;
    private Env env;
    private Event event;

    public boolean getSuccess() {
        return success;
    }

    public Env getEnv() {
        return env;
    }

    public Event getEvent() {
        return event;
    }
}
