package com.mobile.mtrader.ui;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import com.mobile.mtrader.adapter.OrderHistoryAdapter;
import com.mobile.mtrader.di.component.ApplicationComponent;
import com.mobile.mtrader.di.component.DaggerApplicationComponent;
import com.mobile.mtrader.di.module.ContextModule;
import com.mobile.mtrader.di.module.MvvMModule;
import com.mobile.mtrader.mobiletreaderv3.R;
import com.mobile.mtrader.viewmodels.BankViewModel;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;


public class SalesHistoryFragment extends Fragment {

    @BindView(R.id.lvExp)
    RecyclerView customersales;

    OrderHistoryAdapter customerListAdapter;

    BankViewModel bankViewModel;

    ApplicationComponent component;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @BindView(R.id.progressbar)
    ProgressBar progressbar;

    public SalesHistoryFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_sales_history, container, false);
        ButterKnife.bind(this,view);

        component = DaggerApplicationComponent.builder()
                .contextModule(new ContextModule(getActivity()))
                .mvvMModule(new MvvMModule(getActivity()))
                .build();
        component.inject(this);
        bankViewModel = ViewModelProviders.of(this,viewModelFactory).get(BankViewModel.class);
        progressbar.setVisibility(View.VISIBLE);

        customersales.setLayoutManager(new LinearLayoutManager(getActivity()));
        customersales.setHasFixedSize(true);
        customerListAdapter = new OrderHistoryAdapter(getActivity(), bankViewModel);
        customersales.setAdapter(customerListAdapter);

        bankViewModel.salesEntriesGroup();
        bankViewModel.emitSalesEntriesParent().observe(this, sales -> {
            progressbar.setVisibility(View.GONE);
            customerListAdapter.setSalesHiatory(sales);

        });

        return view;
    }

}
