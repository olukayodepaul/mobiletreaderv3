package com.mobile.mtrader.di.component;

import android.content.Context;
import com.mobile.mtrader.data.Api;
import com.mobile.mtrader.di.module.ContextModule;
import com.mobile.mtrader.di.module.MvvMModule;
import com.mobile.mtrader.di.module.PicassoModule;
import com.mobile.mtrader.di.module.RetrofitModule;
import com.mobile.mtrader.di.qualifier.ApplicationContext;
import com.mobile.mtrader.di.scopes.ApplicationScope;
import com.mobile.mtrader.ui.BankActivity;
import com.mobile.mtrader.ui.ConfirmSales;
import com.mobile.mtrader.ui.CustomerFragment;
import com.mobile.mtrader.ui.Customer_Sales_History;
import com.mobile.mtrader.ui.DailySalesActivity;
import com.mobile.mtrader.ui.DeliveryMapActivity;
import com.mobile.mtrader.ui.DepotClockoutActivity;
import com.mobile.mtrader.ui.DepotClokingActivity;
import com.mobile.mtrader.ui.MainActivity;
import com.mobile.mtrader.ui.ModuleActivity;
import com.mobile.mtrader.ui.SalesHistoryFragment;
import com.squareup.picasso.Picasso;

import dagger.Component;

@ApplicationScope
@Component(modules = {ContextModule.class, RetrofitModule.class, PicassoModule.class, MvvMModule.class})
public interface ApplicationComponent {

    Picasso getPicasso();
    Api getApi();
    @ApplicationContext
    Context getContext();

    void inject(MainActivity mainActivity);
    void inject(ModuleActivity moduleActivity);
    void inject(CustomerFragment customerFragment);
    void inject(DepotClokingActivity depotClokingActivity);
    void inject(DailySalesActivity dailySalesActivity);
    void inject(SalesHistoryFragment salesHistoryFragment);
    void inject(ConfirmSales confirmSales);
    void inject(BankActivity bankActivity);
    void inject(DepotClockoutActivity depotClockoutActivity);
    void inject(Customer_Sales_History customer_sales_history);
    void inject(DeliveryMapActivity deliveryMapActivity);

}
