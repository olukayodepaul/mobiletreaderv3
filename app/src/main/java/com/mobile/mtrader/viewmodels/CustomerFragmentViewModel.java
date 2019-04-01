package com.mobile.mtrader.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;

import com.mobile.mtrader.data.AllTablesStructures.Customers;
import com.mobile.mtrader.data.DataRepository;

import java.util.List;

public class CustomerFragmentViewModel extends ViewModel {

    private DataRepository repository;
    //MediatorLiveData liveDataMerger = new MediatorLiveData<>();
    LiveData liveData = new MediatorLiveData<>();

    CustomerFragmentViewModel(DataRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<Customers>> getLiveCustomers() {
        return repository.findAllCustomers();
    }


}
