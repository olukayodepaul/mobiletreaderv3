package com.mobile.mtrader;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;


import com.mobile.mtrader.di.component.ApplicationComponent;
import com.mobile.mtrader.di.component.DaggerApplicationComponent;
import com.mobile.mtrader.di.module.ContextModule;
import com.mobile.mtrader.di.module.MvvMModule;
import com.mobile.mtrader.di.module.PicassoModule;
import com.mobile.mtrader.di.module.RealmModule;
import com.mobile.mtrader.di.module.RetrofitModule;


import io.realm.Realm;
import timber.log.Timber;


public class Apps extends Application {

    ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {

        super.onCreate();
        Realm.init(this);
        Timber.plant(new Timber.DebugTree());
        Realm.init(this);
        //dependencyInjection();
    }

    /*public static Apps get(Activity activity) {
        return (Apps) activity.getApplication();
    }*/

   /* public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }*/

    /*private void dependencyInjection(){
        applicationComponent = DaggerApplicationComponent.builder()
                .contextModule(new ContextModule(this))
                .picassoModule(new PicassoModule())
                .retrofitModule(new RetrofitModule())
                .realmModule(new RealmModule())
                .mvvMModule(new MvvMModule(this))
                .build();
        applicationComponent.injectApplication(this);

    }*/
}
