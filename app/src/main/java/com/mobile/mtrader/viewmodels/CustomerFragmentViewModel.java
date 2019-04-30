package com.mobile.mtrader.viewmodels;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.mobile.mtrader.data.AllTablesStructures.Customers;
import com.mobile.mtrader.data.AllTablesStructures.LastLoation;
import com.mobile.mtrader.data.DataRepository;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class CustomerFragmentViewModel extends ViewModel {

    private DataRepository repository;

    LastLoation Geolocation;

    CustomerFragmentViewModel(DataRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<Customers>> getLiveModules() {
        return repository.findAllCustomers();
    }

    public Single<LastLoation> LastGeolocation() {
        return repository.getPreviousState().map(
                latlng -> {
                    Geolocation = latlng;
                    return latlng;
                }
        );
    }

}
