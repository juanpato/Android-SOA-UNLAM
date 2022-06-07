package com.l1.tp_2.entities;

public class LoginHistoric {
    private String fullName;
    private String date;

    public LoginHistoric(String fullName, String date) {
        this.fullName = fullName;
        this.date = date;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDate() {
        return date;
    }
}
