package com.mobile.mtrader.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.mobile.mtrader.Apps;
import com.mobile.mtrader.adapter.OrderHistoryAdapter;
import com.mobile.mtrader.mobiletreaderv3.R;


import butterknife.BindView;
import butterknife.ButterKnife;


public class SalesHistoryFragment extends Fragment {


    //@Inject
    //RealmService realmService;

    @BindView(R.id.lvExp)
    RecyclerView users_modules;

    OrderHistoryAdapter customerListAdapter;

    RecyclerView.LayoutManager layoutManager;

    public SalesHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_sales_history, container, false);
        ButterKnife.bind(this,view);
        //ApplicationComponent applicationComponent = Apps.get(getActivity()).getApplicationComponent();
/*
        salasHistoryFragmentComponent = DaggerSalasHistoryFragmentComponent.builder()
                .applicationComponent(applicationComponent)
                .salesHistoryFragmentContextModule(new SalesHistoryFragmentContextModule(this))
                .build();
        salasHistoryFragmentComponent.injectSalesHistoryFragment(this);
        onLoadData();
        */
        return view;

    }

    /*@Override
    public void onStart() {
        super.onStart();
        onLoadData();
    }
    */


    /*public void onLoadData(){
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        users_modules.setLayoutManager(layoutManager);
        customerListAdapter = new OrderHistoryAdapter(getContext(),realmService);
        users_modules.setAdapter(customerListAdapter);
    }
    */





}
