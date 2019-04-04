package com.mobile.mtrader;
import android.app.Application;
import com.mobile.mtrader.di.component.ApplicationComponent;

import timber.log.Timber;


public class Apps extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }

}
