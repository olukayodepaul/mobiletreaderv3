package com.mobile.mtrader.di.component;

import android.content.Context;

import com.mobile.mtrader.adapter.SkuAdapter;
import com.mobile.mtrader.data.Api;
import com.mobile.mtrader.di.module.ContextModule;
import com.mobile.mtrader.di.module.MvvMModule;
import com.mobile.mtrader.di.module.PicassoModule;
import com.mobile.mtrader.di.module.RealmModule;
import com.mobile.mtrader.di.module.RetrofitModule;
import com.mobile.mtrader.di.qualifier.ApplicationContext;
import com.mobile.mtrader.di.scopes.ApplicationScope;
import com.mobile.mtrader.repo.RealmService;
import com.mobile.mtrader.repo.RepoService;
import com.mobile.mtrader.ui.CustomerFragment;
import com.mobile.mtrader.ui.DailySalesActivity;
import com.mobile.mtrader.ui.DepotClokingActivity;
import com.mobile.mtrader.ui.MainActivity;
import com.mobile.mtrader.ui.ModuleActivity;
import com.mobile.mtrader.viewmodels.CustomViewModelFactory;
import com.squareup.picasso.Picasso;

import dagger.Component;

@ApplicationScope
@Component(modules = {ContextModule.class, RetrofitModule.class, RealmModule.class, PicassoModule.class, MvvMModule.class})
public interface ApplicationComponent {

    RealmService getRealmService();
    Picasso getPicasso();
    Api getApi();
    @ApplicationContext
    Context getContext();

    void inject(MainActivity mainActivity);
    void inject(ModuleActivity moduleActivity);
    void inject(CustomerFragment customerFragment);
    void inject(DepotClokingActivity depotClokingActivity);
    void inject(DailySalesActivity dailySalesActivity);
    void inject(SkuAdapter dailySalesActivity);


}
