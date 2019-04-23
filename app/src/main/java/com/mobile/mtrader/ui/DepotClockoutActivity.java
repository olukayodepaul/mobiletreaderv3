package com.mobile.mtrader.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.mobile.mtrader.BaseActivity;
import com.mobile.mtrader.adapter.ClockOutAdaper;
import com.mobile.mtrader.di.component.ApplicationComponent;
import com.mobile.mtrader.di.component.DaggerApplicationComponent;
import com.mobile.mtrader.di.module.ContextModule;
import com.mobile.mtrader.di.module.MvvMModule;
import com.mobile.mtrader.mobiletreaderv3.R;
import com.mobile.mtrader.util.AppUtil;
import com.mobile.mtrader.viewmodels.ClockOutViewModel;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DepotClockoutActivity extends BaseActivity {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    ClockOutViewModel clockOutViewModel;

    ApplicationComponent component;

    @BindView(R.id.user_baskets)
    RecyclerView user_baskets;

    @BindView(R.id.back_page)
    ImageView back_page;

    @BindView(R.id.payments_btn)
    Button payments_btn;

    ClockOutAdaper clockOutAdaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depot_clockout);
        ButterKnife.bind(this);
        component = DaggerApplicationComponent.builder()
                .contextModule(new ContextModule(this))
                .mvvMModule(new MvvMModule(this))
                .build();
        component.inject(this);
        clockOutViewModel = ViewModelProviders.of(this, viewModelFactory).get(ClockOutViewModel.class);
        showProgressBar(true);

        user_baskets.setLayoutManager(new LinearLayoutManager(this));
        user_baskets.setHasFixedSize(true);
        clockOutAdaper = new ClockOutAdaper(this, clockOutViewModel);
        user_baskets.setAdapter(clockOutAdaper);

        clockOutViewModel.setBasket("1").observe(this, products -> {
            showProgressBar(false);
            clockOutAdaper.setModulesAdapter(products);
            clockOutAdaper.notifyDataSetChanged();
        });

        payments_btn.setOnClickListener(v -> {
            showProgressBar(true);
            if (!AppUtil.checkConnection(this)) {
                showProgressBar(false);
                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            } else {
                clockOutViewModel.dailyRoster();
            }
        });



        back_page.setOnClickListener(v -> onBackPressed());
    }
}
