package com.mobile.mtrader.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.mobile.mtrader.data.DataRepository;
import com.mobile.mtrader.di.scopes.ApplicationScope;
import javax.inject.Inject;


@ApplicationScope
public class CustomViewModelFactory implements ViewModelProvider.Factory {

    private final DataRepository repository;

    @Inject
    public CustomViewModelFactory(DataRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(repository);
        }else if(modelClass.isAssignableFrom(ModuleViewModel.class)) {
            return (T) new ModuleViewModel(repository);
        }else if(modelClass.isAssignableFrom(CustomerFragmentViewModel.class)) {
            return (T) new CustomerFragmentViewModel(repository);
        }else if(modelClass.isAssignableFrom(ClockInViewModel.class)) {
            return (T) new ClockInViewModel(repository);
        }else if(modelClass.isAssignableFrom(DailySalesViewModule.class)) {
            return (T) new DailySalesViewModule(repository);
        }else if(modelClass.isAssignableFrom(RepSalesConfirmViewModel.class)) {
            return (T) new RepSalesConfirmViewModel(repository);
        }else if(modelClass.isAssignableFrom(BankViewModel.class)) {
            return (T) new BankViewModel(repository);
        }else if(modelClass.isAssignableFrom(DeliverySalesMapViewmodel.class)) {
            return (T) new DeliverySalesMapViewmodel(repository);
        }else{
            throw new IllegalArgumentException("ViewModel Not Found");
        }
    }
}


