package com.mobile.mtrader.ui;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import com.mobile.mtrader.BaseActivity;
import com.mobile.mtrader.cache.OutletClassCache;
import com.mobile.mtrader.cache.OutletLanguageCache;
import com.mobile.mtrader.cache.OutletTypeCache;
import com.mobile.mtrader.data.AllTablesStructures.UserSpinners;
import com.mobile.mtrader.di.component.ApplicationComponent;
import com.mobile.mtrader.di.component.DaggerApplicationComponent;
import com.mobile.mtrader.di.module.ContextModule;
import com.mobile.mtrader.di.module.MvvMModule;
import com.mobile.mtrader.mobiletreaderv3.R;
import com.mobile.mtrader.viewmodels.CustomerActivityViewmModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class CustomerProfile extends BaseActivity{

    ApplicationComponent component;

    CustomerActivityViewmModel customerActivityViewmModel;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    Bundle bundle;

    int customerid;

    @BindView(R.id.u_name)
    TextInputEditText u_name;

    @BindView(R.id.u_email)
    TextInputEditText u_email;

    @BindView(R.id.u_paswd)
    TextInputEditText u_paswd;

    @BindView(R.id.u_phone)
    TextInputEditText u_phone;

    ArrayList<String> outletClassList;
    OutletClassCache outletClassCache;
    OutletLanguageCache outletLanguageCache;
    OutletTypeCache outletTypeCache;
    ArrayAdapter<String> arrayAdapter;
    ArrayAdapter<String> arrayAdapterLang;
    ArrayAdapter<String> arrayAdapterType;

    @BindView(R.id.u_group)
    Spinner u_group;

    @BindView(R.id.u_gender)
    Spinner u_gender;

    @BindView(R.id.vehicleType)
    Spinner vehicleType;

    CompositeDisposable cDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);
        ButterKnife.bind(this);
        component = DaggerApplicationComponent.builder()
                .contextModule(new ContextModule(this))
                .mvvMModule(new MvvMModule(this))
                .build();
        component.inject(this);
        customerActivityViewmModel = ViewModelProviders.of(this, viewModelFactory).get(CustomerActivityViewmModel.class);
        outletClassCache = new OutletClassCache();
        outletLanguageCache = new OutletLanguageCache();
        outletTypeCache = new OutletTypeCache();
        showProgressBar(false);
        bundle = getIntent().getExtras();

        if (bundle != null) {
            customerid = bundle.getInt("CUSTOMERS_ID_INFO_APPS");
        }

        customerActivityViewmModel.getGroupUserSpinners(1).observe(this, userSpinners -> {
            outletClassList = new ArrayList<>();
            if(outletClassCache.size()>0){outletClassCache.clear();}
            if(userSpinners!=null) {
                for(int i = 0 ; i < userSpinners.size() ; i++){
                    outletClassList.add(userSpinners.get(i).name);
                    //save in a cache
                    outletClassCache.add(
                            userSpinners.get(i).id,
                            userSpinners.get(i).name
                    );
                }
                arrayAdapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_item,outletClassList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                u_group.setAdapter(arrayAdapter);
            }
        });

        customerActivityViewmModel.getGroupUserSpinners(2).observe(this, userSpinners -> {
            outletClassList = new ArrayList<>();
            if(outletLanguageCache.size()>0){outletLanguageCache.clear();}
            if(userSpinners!=null) {
                for(int i = 0 ; i < userSpinners.size() ; i++){
                    outletClassList.add(userSpinners.get(i).name);
                    //save in a cache
                    outletLanguageCache.add(
                            userSpinners.get(i).id,
                            userSpinners.get(i).name
                    );
                }
                arrayAdapterLang = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_item,outletClassList);
                arrayAdapterLang.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                u_gender.setAdapter(arrayAdapterLang);
            }
        });

        customerActivityViewmModel.getGroupUserSpinners(3).observe(this, userSpinners -> {
            outletClassList = new ArrayList<>();
            if(outletTypeCache.size()>0){outletTypeCache.clear();}
            if(userSpinners!=null) {
                for(int i = 0 ; i < userSpinners.size() ; i++){
                    outletClassList.add(userSpinners.get(i).name);
                    //save in a cache
                    outletTypeCache.add(
                            userSpinners.get(i).id,
                            userSpinners.get(i).name
                    );
                }
                arrayAdapterType = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_item,outletClassList);
                arrayAdapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                vehicleType.setAdapter(arrayAdapterType);
            }
        });

        cDisposable.add(customerActivityViewmModel.getIndividualCustomerProfiles(customerid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {

                    u_name.setText(data.outletname);
                    u_email.setText(data.contactname);
                    u_paswd.setText(data.outletaddress);
                    u_phone.setText(data.contactphone);

                },throwable -> {

                })
        );
    }
}
