package com.mobile.mtrader.ui;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mobile.mtrader.adapter.CustomerListAdapter;
import com.mobile.mtrader.di.component.ApplicationComponent;
import com.mobile.mtrader.di.component.DaggerApplicationComponent;
import com.mobile.mtrader.di.module.ContextModule;
import com.mobile.mtrader.di.module.MvvMModule;
import com.mobile.mtrader.mobiletreaderv3.R;
import com.mobile.mtrader.viewmodels.CustomerFragmentViewModel;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static android.support.v4.provider.FontsContractCompat.FontRequestCallback.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerFragment extends Fragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    ApplicationComponent component;

    CustomerFragmentViewModel cFViewModel;

    @BindView(R.id.customlist)
    RecyclerView customlist;

    CustomerListAdapter customerListAdapter;

    public CustomerFragment() {}

    SalesActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.fragment_customer, container, false);
        ButterKnife.bind(this,view);
        component = DaggerApplicationComponent.builder()
                .contextModule(new ContextModule(getActivity()))
                .mvvMModule(new MvvMModule(getActivity()))
                .build();
        component.inject(this);
        if ( getActivity() instanceof SalesActivity){ activity = (SalesActivity) getActivity(); }
        activity.showProgressBar(true);
        cFViewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(CustomerFragmentViewModel.class);

        customlist.setLayoutManager(new LinearLayoutManager(getActivity()));
        customlist.setHasFixedSize(true);
        customerListAdapter = new CustomerListAdapter(getContext());
        customlist.setAdapter(customerListAdapter);

        cFViewModel.getLiveModules().observe(this,
                customers ->
                {
                    activity.showProgressBar(false);
                    customerListAdapter.setModulesAdapter(customers);
                }
        );

        return view;
    }
}
