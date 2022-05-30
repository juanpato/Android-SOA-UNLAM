package com.l1.tp_2.views.register;

import com.l1.tp_2.entities.UserResponse;

public class RegisterPresenter implements RegisterContract.Presenter {

    private RegisterContract.View view;
    private RegisterContract.Model model;

    public RegisterPresenter(RegisterContract.View view) {
        this.view = view;
        this.model = new RegisterModel(view::onSuccess, view::onError);
    }


    @Override
    public void onButtonClick(String name, String lastname, String email, Integer dni, String password) {
        model.register(name, lastname, email, dni, password);
    }

    @Override
    public void registerRegisterEvent(UserResponse response) {
        model.registerRegisterEvent(response);
    }

}
