package com.mobile.mtrader.viewmodels;


import android.arch.lifecycle.ViewModel;

import com.mobile.mtrader.data.AllTablesStructures.Products;
import com.mobile.mtrader.data.DataRepository;

import java.util.List;

import io.reactivex.Flowable;


public class DailySalesViewModule extends ViewModel {

    private DataRepository repository;
    List<Products> mproducts;

    DailySalesViewModule(DataRepository repository) {
        this.repository = repository;
    }

    public Flowable<List<Products>> getLiveCustomers() {
        return repository.findAllUserProducts().map(
                products -> {
                    mproducts = products;
                    return products;
                }
        );
    }


}
