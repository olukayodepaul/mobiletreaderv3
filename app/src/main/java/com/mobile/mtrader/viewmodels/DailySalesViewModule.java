package com.mobile.mtrader.viewmodels;


import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.mobile.mtrader.data.AllTablesStructures.Products;
import com.mobile.mtrader.data.DataRepository;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;


public class DailySalesViewModule extends ViewModel {

    private DataRepository repository;
    List<Products> mproducts;
    Long countItems;

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

    public Single<Long> validateUserSalesEntries(String updatestatus) {
        return repository.validateUserSalesEntries(updatestatus).map(
                mItems -> {
                    countItems = mItems;
                    return mItems;
                }
        );
    }

    public void setDailySalesByCustomers(String inventory, int pricing, String orders, String customerno,
                                      String updatestatus,  int id, String separator, String productcode) {
        Products updates = new Products(
                id,
                separator,
                "",
                productcode,
                "",
                "",
                "",
                "",
                "",
                inventory,
                pricing,
                orders,
                customerno,
                updatestatus
        );
        new UpdateCustomerSales().execute(updates);
    }

    private class UpdateCustomerSales extends AsyncTask<Products, Void, Void> {
        @Override
        protected Void doInBackground(Products... item) {
            repository.updateDailySalesBySku(item[0].inventory, item[0].pricing, item[0].orders,item[0].customerno,
                    item[0].updatestatus, item[0].id, item[0].separator,item[0].productcode );
            return null;
        }
    }




}
