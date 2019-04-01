package com.mobile.mtrader.frameworks.clockin;


public interface ClockContract {

    interface ClockView {
        void showProgressDialog();
        void showMsg(String msg, String title, String buttons);
    }

    interface ClockPresenter {
        void loadClockOutData();
    }

}
