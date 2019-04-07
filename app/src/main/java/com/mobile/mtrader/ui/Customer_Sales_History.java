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

import com.mobile.mtrader.adapter.SalesEntryHistoryAdapter;
import com.mobile.mtrader.di.component.ApplicationComponent;
import com.mobile.mtrader.di.component.DaggerApplicationComponent;
import com.mobile.mtrader.di.module.ContextModule;
import com.mobile.mtrader.di.module.MvvMModule;
import com.mobile.mtrader.mobiletreaderv3.R;
import com.mobile.mtrader.viewmodels.BankViewModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Customer_Sales_History extends AppCompatActivity {

    Bundle extras;

    String timeId;

    @BindView(R.id.sales_history)
    RecyclerView sales_history;

    @BindView(R.id.back_page)
    ImageView back_page;

    RecyclerView.LayoutManager layoutManager;

    BankViewModel bankViewModel;

    ApplicationComponent component;

    @BindView(R.id.progressbar)
    ProgressBar progressbar;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    SalesEntryHistoryAdapter salesEntryHistoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer__sales__history);
        ButterKnife.bind(this);

        component = DaggerApplicationComponent.builder()
                .contextModule(new ContextModule(this))
                .mvvMModule(new MvvMModule(this))
                .build();
        component.inject(this);

        bankViewModel = ViewModelProviders.of(this,viewModelFactory).get(BankViewModel.class);

        extras = getIntent().getExtras();

        if (extras != null) {
            timeId = extras.getString("CUSTOMER_ID");
        }

        back_page.setOnClickListener(v->{
            onBackPressed();
        });

        sales_history.setLayoutManager(new LinearLayoutManager(this));
        sales_history.setHasFixedSize(true);
        salesEntryHistoryAdapter = new SalesEntryHistoryAdapter(this);
        sales_history.setAdapter(salesEntryHistoryAdapter);

        bankViewModel.salesEntriesGroupList(Integer.parseInt(timeId));
        bankViewModel.emitSalesEntriesChildren().observe(this, sales -> {
            progressbar.setVisibility(View.GONE);
            salesEntryHistoryAdapter.setSalesHiatory(sales);
        });
    }

}

