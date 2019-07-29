package com.mobile.mtrader.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.mobile.mtrader.BaseActivity;
import com.mobile.mtrader.adapter.AllRepCustomersAdapter;
import com.mobile.mtrader.di.component.ApplicationComponent;
import com.mobile.mtrader.di.component.DaggerApplicationComponent;
import com.mobile.mtrader.di.module.ContextModule;
import com.mobile.mtrader.di.module.MvvMModule;
import com.mobile.mtrader.mobiletreaderv3.R;
import com.mobile.mtrader.viewmodels.CustomerActivityViewmModel;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomerActivity extends BaseActivity {

    @BindView(R.id.back_page)
    ImageView back_page;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.customrs_list)
    RecyclerView customrs_list;

    ApplicationComponent component;

    CustomerActivityViewmModel customerActivityViewmModel;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    AllRepCustomersAdapter allRepCustomersAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer2);
        ButterKnife.bind(this);
        component = DaggerApplicationComponent.builder()
                .contextModule(new ContextModule(this))
                .mvvMModule(new MvvMModule(this))
                .build();
        component.inject(this);
        customerActivityViewmModel = ViewModelProviders.of(this, viewModelFactory).get(CustomerActivityViewmModel.class);
        showProgressBar(true);

        initCustomers();

        fab.setOnClickListener(view -> {
            Intent intent = new Intent(CustomerActivity.this, AddCustomers.class);
            startActivity(intent);
        });

        back_page.setOnClickListener(view -> {
            onBackPressed();
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        initCustomers();
    }

    public void initCustomers(){
        customrs_list.setLayoutManager(new LinearLayoutManager(this));
        customrs_list.setHasFixedSize(true);
        allRepCustomersAdapter = new AllRepCustomersAdapter(this);
        customrs_list.setAdapter(allRepCustomersAdapter);
        customerActivityViewmModel.getAllCustomersList().observe(this, data -> {
            allRepCustomersAdapter.setModulesAdapter(data);
            allRepCustomersAdapter.notifyDataSetChanged();
            showProgressBar(false);
        });
    }
}
