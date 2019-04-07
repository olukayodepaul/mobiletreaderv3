package com.mobile.mtrader.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.mobile.mtrader.adapter.BankAdapter;
import com.mobile.mtrader.di.component.ApplicationComponent;
import com.mobile.mtrader.di.component.DaggerApplicationComponent;
import com.mobile.mtrader.di.module.ContextModule;
import com.mobile.mtrader.di.module.MvvMModule;
import com.mobile.mtrader.mobiletreaderv3.R;
import com.mobile.mtrader.viewmodels.BankViewModel;
import com.mobile.mtrader.viewmodels.LoginViewModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BankActivity extends AppCompatActivity {

    BankViewModel bankViewModel;

    ApplicationComponent component;

    @BindView(R.id.progressbar)
    ProgressBar progressbar;

    @BindView(R.id.banks)
    RecyclerView banks;

    @BindView(R.id.back_page)
    ImageView back_page;

    BankAdapter bankAdapter;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank);
        ButterKnife.bind(this);
        component = DaggerApplicationComponent.builder()
                .contextModule(new ContextModule(this))
                .mvvMModule(new MvvMModule(this))
                .build();
        component.inject(this);
        bankViewModel = ViewModelProviders.of(this,viewModelFactory).get(BankViewModel.class);
        bankViewModel.salesEntriesToday();

        back_page.setOnClickListener(v-> onBackPressed());

        banks.setLayoutManager(new LinearLayoutManager(this));
        banks.setHasFixedSize(true);
        bankAdapter = new BankAdapter(this);
        banks.setAdapter(bankAdapter);
        bankViewModel.emitSalesEntries().observe(this, sales -> {
            progressbar.setVisibility(View.GONE);
            bankAdapter.setModulesAdapter(sales);
        });

    }
}
