package com.mobile.mtrader.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.mobile.mtrader.data.AllTablesStructures.Sales;
import com.mobile.mtrader.data.DataRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class BankViewModel extends ViewModel {

    CompositeDisposable mDis = new CompositeDisposable();

    private DataRepository repository;
    private MutableLiveData<List<Sales>> elistresps = new MutableLiveData<>();
    private MutableLiveData<List<Sales>> groupList = new MutableLiveData<>();
    private MutableLiveData<List<Sales>> listchild = new MutableLiveData<>();

    BankViewModel(DataRepository repository) {
        this.repository = repository;
    }

    public MutableLiveData<List<Sales>> emitSalesEntries() {
        return elistresps;
    }

    public MutableLiveData<List<Sales>> emitSalesEntriesParent() {
        return groupList;
    }

    public MutableLiveData<List<Sales>> emitSalesEntriesChildren() {
        return groupList;
    }

    public void salesEntriesToday() {
        mDis.add(repository.salesEntriesToday()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    elistresps.postValue(data);
                        }
                )
        );
    }

    public void salesEntriesGroup() {
        mDis.add(repository.salesEntriesGroup()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    groupList.postValue(data);
                        }
                )
        );
    }

    public void salesEntriesGroupList(int custid) {
        mDis.add(repository.salesEntriesGroupList(custid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    listchild.postValue(data);
                        }
                )
        );
    }




}
