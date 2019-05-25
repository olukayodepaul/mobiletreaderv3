package com.mobile.mtrader.viewmodels;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.mobile.mtrader.data.AllTablesStructures.Customers;
import com.mobile.mtrader.data.AllTablesStructures.Products;
import com.mobile.mtrader.data.AllTablesStructures.Sales;
import com.mobile.mtrader.data.DataRepository;
import com.mobile.mtrader.model.ModelAttendant;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;


public class BankViewModel extends ViewModel {

    private DataRepository repository;

    private MutableLiveData<String> res = new MutableLiveData<>();

    CompositeDisposable mDis = new CompositeDisposable();

    Long countItems;

    Double totalAmount;

    Double totalOrder;

    private MutableLiveData<List<Sales>> groupList = new MutableLiveData<>();

    BankViewModel(DataRepository repository) {
        this.repository = repository;
    }

    public MutableLiveData<String> getObserveResponse() {
        return res;
    }

    public MutableLiveData<List<Sales>> emitSalesEntriesParent() {
        return groupList;
    }

    public LiveData<List<Products>> setBasket() {
        return repository.findAllMyProduct("1");
    }

    public LiveData<List<Sales>> salesEntriesGroupList(String custid){
        return repository.salesEntriesGroupList(custid);
    }

    public void salesEntriesGroup() {
        mDis.add(repository.salesEntriesGroup()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> groupList.postValue(data))
        );
    }

    public Single<Long> trackUnPushDataToServer(String customerno, String localstatus) {
        return repository.trackUnPushDataToServer(customerno,localstatus).map(
                nos -> {
                    countItems = nos;
                    return nos;
                }
        );
    }

   

    public Single<Double> sumAllOrder(String productid) {
        return repository.sumAllOrder(productid).map(
                totals -> {
                    totalOrder = totals;
                    return totals;
                }
        );
    }

    public void dailyRoster() {

        String dates = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String times = new SimpleDateFormat("HH:mm:ss").format(new Date());

        mDis.add(repository.findIndividualUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {

                    repository.setRoster(
                            data.user_id,
                            5,
                            dates,
                            times,
                            "0.0",
                            "0.0",
                            "Payment already taken")
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<Response<ModelAttendant>>() {
                                @Override
                                public void onSubscribe(Disposable d) { }

                                @Override
                                public void onNext(Response<ModelAttendant> response) {
                                    ModelAttendant roster = response.body();

                                    if (response.code() == 200) {
                                        if (roster.status == 200) {
                                            Customers customers = new Customers(0, "", "", "", "", "", "", 3, "", "", times);
                                            new UpdateCustomerTime().execute(customers);
                                            res.postValue(Integer.toString(roster.status) + "~" + "Successful~" + times);
                                        } else {
                                            res.postValue(Integer.toString(roster.status) + "~" + roster.msg);
                                        }
                                    } else {
                                        res.postValue("401" + "~" + "Server cant be reach");
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {
                                    res.postValue("401" + "~" + e.getMessage());
                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                })
        );
    }

    private class UpdateCustomerTime extends AsyncTask<Customers, Void, Void> {

        @Override
        protected Void doInBackground(Customers... item) {
            repository.updateIndividualCustomers(item[0].rostertime, item[0].sort);
            return null;
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mDis.clear();
    }


}


