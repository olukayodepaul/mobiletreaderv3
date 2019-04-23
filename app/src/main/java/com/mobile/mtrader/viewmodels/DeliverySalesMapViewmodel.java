package com.mobile.mtrader.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;
import com.mobile.mtrader.data.AllTablesStructures.Products;
import com.mobile.mtrader.data.DataRepository;

public class DeliverySalesMapViewmodel extends ViewModel {

    private DataRepository repository;
    MutableLiveData<String> rep = new MutableLiveData<>();

    DeliverySalesMapViewmodel(DataRepository repository) {
        this.repository = repository;
    }

    public MutableLiveData<String> returnRep() {
        return rep;
    }

    public void reInitialisProduct(String inventory, int pricing, String orders, String customerno, String updatestatus) {
        /*Products products = new Products(
            0,"","","","","","","",
                "",inventory, pricing, orders, customerno, updatestatus
        );
        new UpdateCustomerTime().execute(products);
        rep.postValue("200");
        */
    }

    /*private class UpdateCustomerTime extends AsyncTask<Products, Void, Void> {
        @Override
        protected Void doInBackground(Products... item) {
            repository.reInitialisProducts(item[0].inventory, item[0].pricing, item[0].orders, item[0].customerno, item[0].updatestatus);
            return null;
        }
    }*/
}
