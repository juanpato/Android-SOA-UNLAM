package com.l1.tp_2.views.login_historic;

import com.l1.tp_2.entities.LoginHistoric;

import java.util.List;

public interface LoginHistoricContract {

    interface View {
    }

    interface Model {
        List<LoginHistoric> getLoginHistoric();
    }

    interface Presenter {
        List<LoginHistoric> onLoadData();
    }

}
