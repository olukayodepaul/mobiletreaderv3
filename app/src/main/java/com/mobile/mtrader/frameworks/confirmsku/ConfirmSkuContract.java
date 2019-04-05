package com.mobile.mtrader.frameworks.confirmsku;


public interface ConfirmSkuContract {

    interface ConfirmView {
        void showError(String message, String title, String buttons);
        void showProgressDialog();
        void showComplete();
    }

    interface ConfirmPresenter {
        void pushDataToSever(String networkAccess) ;
    }




}
