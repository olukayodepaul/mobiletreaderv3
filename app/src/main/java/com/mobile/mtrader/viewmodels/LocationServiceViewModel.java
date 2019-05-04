package com.mobile.mtrader.viewmodels;

import android.arch.lifecycle.ViewModel;

import com.mobile.mtrader.data.DataRepository;

public class LocationServiceViewModel extends ViewModel {

    private DataRepository repository;

    LocationServiceViewModel(DataRepository repository) {
        this.repository = repository;
    }
}
