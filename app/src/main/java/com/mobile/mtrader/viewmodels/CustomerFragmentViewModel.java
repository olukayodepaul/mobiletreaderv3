package com.mobile.mtrader.viewmodels;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.mobile.mtrader.data.AllTablesStructures.Customers;
import com.mobile.mtrader.data.DataRepository;

import java.util.List;

import io.reactivex.Flowable;

public class CustomerFragmentViewModel extends ViewModel {

    private DataRepository repository;


    CustomerFragmentViewModel(DataRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<Customers>> getLiveModules() {
        return repository.findAllCustomers();
    }

}
