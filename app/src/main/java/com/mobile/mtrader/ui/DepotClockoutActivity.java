package com.mobile.mtrader.ui;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.mobile.mtrader.adapter.ClockOutAdaper;
import com.mobile.mtrader.di.component.ApplicationComponent;
import com.mobile.mtrader.di.component.DaggerApplicationComponent;
import com.mobile.mtrader.di.module.ContextModule;
import com.mobile.mtrader.di.module.MvvMModule;
import com.mobile.mtrader.mobiletreaderv3.R;
import com.mobile.mtrader.viewmodels.BankViewModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DepotClockoutActivity extends AppCompatActivity {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    BankViewModel bankViewModel;

    ApplicationComponent component;

    ClockOutAdaper clockOutAdaper;

    @BindView(R.id.clock_out_depot)
    RecyclerView clock_out_depot;

    @BindView(R.id.progressbar)
    ProgressBar progressbar;

    @BindView(R.id.back_page)
    ImageView back_page;

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
        bankViewModel = ViewModelProviders.of(this,viewModelFactory).get(BankViewModel.class);
        bankViewModel.salesEntriesToday();

        back_page.setOnClickListener(v-> onBackPressed());

        clock_out_depot.setLayoutManager(new LinearLayoutManager(this));
        clock_out_depot.setHasFixedSize(true);
        clockOutAdaper = new ClockOutAdaper(this);
        clock_out_depot.setAdapter(clockOutAdaper);

        bankViewModel.emitSalesEntries().observe(this, sales ->{
            progressbar.setVisibility(View.GONE);
            clockOutAdaper.setModulesAdapter(sales);
        });

    }
}
