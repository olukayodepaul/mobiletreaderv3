package com.mobile.mtrader.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.mobile.mtrader.data.DataRepository;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DeliverySalesMapViewmodel extends ViewModel {

    private DataRepository repository;

    MutableLiveData<String> rep = new MutableLiveData<>();

    CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    DeliverySalesMapViewmodel(DataRepository repository) {
        this.repository = repository;
    }

    public MutableLiveData<String> returnRep() {
        return rep;
    }

    public void updateFromSalesEntries() {

        Completable.fromAction(() -> repository.reInitialisSalesEntries())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onComplete() {

                        mCompositeDisposable.add(repository.getPreviousState()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(data->{
                                    rep.postValue("200~"+data.lat+"~"+data.lng);
                                }, throwable -> {
                                    rep.postValue("300~0~0");
                                })
                        );
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mCompositeDisposable.clear();
    }
}

