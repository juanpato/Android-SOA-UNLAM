package com.l1.tp_2.entities;

public class UserResponse {
    private boolean success;
    private Env env;
    private String token;
    private User user;

    public boolean getSuccess() {
        return success;
    }

    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }
}
