package com.mobile.mtrader.frameworks.login;


public interface LoginContract {

    interface LoginView {
        void showError(String message, String title, String buttons);
        void showProgressDialog();
        void showComplete();
    }

    interface LoginPresenter {
        void loadSigninData(String user, String password, String imie);
    }

}
