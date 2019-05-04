package com.mobile.mtrader.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.mobile.mtrader.data.AllTablesStructures.AllRepCustomers;
import com.mobile.mtrader.data.AllTablesStructures.UserSpinners;
import com.mobile.mtrader.data.DataRepository;

import java.util.List;

import io.reactivex.Single;

public class CustomerActivityViewmModel extends ViewModel {

    private DataRepository repository;

    AllRepCustomers allRepCustomers;

    public CustomerActivityViewmModel(DataRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<AllRepCustomers>> getAllCustomersList() {
        return repository.getAllCustomersList();
    }

    public LiveData<List<UserSpinners>> getGroupUserSpinners(int sep) {
        return repository.getGroupUserSpinners(sep);
    }

    public Single<AllRepCustomers> getIndividualCustomerProfiles(int id) {
        return repository.getIndividualCustomerProfiles(id).map(
                profile -> {
                    allRepCustomers = profile;
                    return profile;
                }
        );
    }
}
