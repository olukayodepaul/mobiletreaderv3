package com.mobile.mtrader.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.mobile.mtrader.data.DataRepository;
import com.mobile.mtrader.model.ModelAttendant;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class DeliverySalesMapViewmodel extends ViewModel {

    private DataRepository repository;

    MutableLiveData<String> rep = new MutableLiveData<>();

    MutableLiveData<String> rp = new MutableLiveData<>();

    CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    DeliverySalesMapViewmodel(DataRepository repository) {
        this.repository = repository;
    }

    public MutableLiveData<String> returnRep() {
        return rep;
    }

    public MutableLiveData<String> rpRep() {
        return rp;
    }

    public void setOutletClose(int userid, String urno, double lat, double lng, String  arivaltime) {

        repository.setOutletClose(userid, urno, lat, lng, arivaltime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<ModelAttendant>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(Response<ModelAttendant> response) {

                        ModelAttendant res = response.body();

                        if (response != null && response.isSuccessful() && response.body() != null && response.code() == 200) {
                            rp.postValue("200");
                        } else {
                            rp.postValue("400~Connection Error, Please try again");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        rp.postValue("400~Server Error");
                    }
                });
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

