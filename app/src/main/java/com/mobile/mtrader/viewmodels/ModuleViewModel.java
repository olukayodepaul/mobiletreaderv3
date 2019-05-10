package com.mobile.mtrader.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.mobile.mtrader.data.AllTablesStructures.Modules;
import com.mobile.mtrader.data.DataRepository;

import java.util.List;

public class ModuleViewModel extends ViewModel {

    private DataRepository repository;

    ModuleViewModel(DataRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<Modules>> getLiveModules() {
        return repository.findAllModules();
    }
}
