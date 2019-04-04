package com.mobile.mtrader.ui;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.mobile.mtrader.adapter.ModuleAdapter;
import com.mobile.mtrader.data.AllTablesStructures.Modules;
import com.mobile.mtrader.di.component.ApplicationComponent;
import com.mobile.mtrader.di.component.DaggerApplicationComponent;
import com.mobile.mtrader.di.module.ContextModule;
import com.mobile.mtrader.di.module.MvvMModule;
import com.mobile.mtrader.mobiletreaderv3.R;
import com.mobile.mtrader.viewmodels.ModuleViewModel;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ModuleActivity extends AppCompatActivity {


    ModuleViewModel moduleViewModel;

    @BindView(R.id.users_modules)
    RecyclerView users_modules;

    RecyclerView.LayoutManager layoutManager;

    ModuleAdapter moduleAdapter;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    ApplicationComponent component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeactivity);
        ButterKnife.bind(this);

        component = DaggerApplicationComponent.builder()
                .contextModule(new ContextModule(this))
                .mvvMModule(new MvvMModule(this))
                .build();
        component.inject(this);

        users_modules.setLayoutManager(new LinearLayoutManager(this));
        users_modules.setHasFixedSize(true);
        moduleAdapter = new ModuleAdapter(this);
        users_modules.setAdapter(moduleAdapter);

        moduleViewModel = ViewModelProviders.of(this, viewModelFactory).get(ModuleViewModel.class);
        moduleViewModel.getLiveModules().observe(this, modules -> moduleAdapter.setModulesAdapter(modules));

    }
}
