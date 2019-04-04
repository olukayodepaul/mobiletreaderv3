package com.mobile.mtrader.ui;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mobile.mtrader.adapter.OrderHistoryAdapter;
import com.mobile.mtrader.data.AllTablesStructures.Sales;
import com.mobile.mtrader.di.component.ApplicationComponent;
import com.mobile.mtrader.di.component.DaggerApplicationComponent;
import com.mobile.mtrader.di.module.ContextModule;
import com.mobile.mtrader.di.module.MvvMModule;
import com.mobile.mtrader.mobiletreaderv3.R;
import com.mobile.mtrader.viewmodels.DailySalesViewModule;
import java.util.List;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;


public class SalesHistoryFragment extends Fragment {

    @BindView(R.id.lvExp)
    RecyclerView customersales;

    OrderHistoryAdapter customerListAdapter;

    RecyclerView.LayoutManager layoutManager;

    DailySalesViewModule dailySalesViewModule;

    ApplicationComponent component;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    MediatorLiveData liveDataMerger = new MediatorLiveData<>();

    LiveData<List<Sales>> orders;

    public SalesHistoryFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_sales_history, container, false);
        ButterKnife.bind(this,view);
        dailySalesViewModule = ViewModelProviders.of(getActivity(), viewModelFactory).get(DailySalesViewModule.class);

        component = DaggerApplicationComponent.builder()
                .contextModule(new ContextModule(getActivity()))
                .mvvMModule(new MvvMModule(getActivity()))
                .build();
        component.inject(this);

        customersales.setLayoutManager(new LinearLayoutManager(getActivity()));
        customersales.setHasFixedSize(true);
        customersales.setLayoutManager(layoutManager);
        //customerListAdapter = new OrderHistoryAdapter(getActivity());
        customersales.setAdapter(customerListAdapter);



        return view;
    }

}
