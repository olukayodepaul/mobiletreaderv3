package com.mobile.mtrader.viewmodels;
import android.arch.lifecycle.ViewModel;

import com.mobile.mtrader.data.AllTablesStructures.Customers;
import com.mobile.mtrader.data.DataRepository;

import java.util.List;

import io.reactivex.Flowable;

public class CustomerFragmentViewModel extends ViewModel {

    private DataRepository repository;

    List<Customers> mcustomers;

    CustomerFragmentViewModel(DataRepository repository) {
        this.repository = repository;
    }

    public Flowable<List<Customers>> getLiveCustomers() {
        return repository.findAllCustomers().map(
            customers -> {
               mcustomers = customers;
               return customers;
            }
        );
    }
}
