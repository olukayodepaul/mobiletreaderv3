package com.mobile.mtrader.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.mobile.mtrader.data.AllTablesStructures.Products;
import com.mobile.mtrader.data.DataRepository;

import java.util.List;

public class DailySalesViewModule extends ViewModel {

    private DataRepository repository;
    MutableLiveData<String> test = new MutableLiveData<>();

    DailySalesViewModule(DataRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<Products>> findAllUserProducts() {
        return repository.findAllUserProducts();
    }

    public MutableLiveData<String> getNetRes() {
        return test;
    }

    public void getTest(){
        test.postValue("paul");
    }


}
