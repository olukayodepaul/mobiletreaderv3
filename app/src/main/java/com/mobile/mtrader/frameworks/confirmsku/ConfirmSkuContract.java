package com.mobile.mtrader.frameworks.confirmsku;


import com.mobile.mtrader.model.MoveDataToServer;

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
