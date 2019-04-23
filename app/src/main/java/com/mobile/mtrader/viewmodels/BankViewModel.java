package com.mobile.mtrader.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.mobile.mtrader.data.AllTablesStructures.Customers;
import com.mobile.mtrader.data.AllTablesStructures.Employees;
import com.mobile.mtrader.data.AllTablesStructures.Products;
import com.mobile.mtrader.data.AllTablesStructures.Sales;
import com.mobile.mtrader.data.DataRepository;
import com.mobile.mtrader.model.ModelAttendant;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class BankViewModel extends ViewModel {

    CompositeDisposable mDis = new CompositeDisposable();

    private DataRepository repository;
    private MutableLiveData<Long> mTotalSalesValue = new MutableLiveData<>();
    private MutableLiveData<List<Sales>> groupList = new MutableLiveData<>();
    private MutableLiveData<Long> basketResponce = new MutableLiveData<>();
    private MutableLiveData<Long> basketqty = new MutableLiveData<>();
    //private MutableLiveData<List<Sales>> listchild = new MutableLiveData<>();
    private MutableLiveData<String> observeResponse = new MutableLiveData<>();
    private MutableLiveData<Throwable> observeThrowable = new MutableLiveData<>();
    Long countItems;
    Long totalProductSold;

    private MutableLiveData<List<Products>> salesBalane = new MutableLiveData<>();

    BankViewModel(DataRepository repository) {
        this.repository = repository;
    }

    public MutableLiveData<List<Products>> emitSalesBalance() {
        return salesBalane;
    }

    public MutableLiveData<Long> sumTotalBasket() {
        return basketResponce;
    }

    public MutableLiveData<Long> sumQtyofProducts() {
        return basketqty;
    }

    public MutableLiveData<List<Sales>> emitSalesEntriesParent() {
        return groupList;
    }

    /*public MutableLiveData<List<Sales>> emitSalesEntriesChildren() {
        return listchild;
    }*/

    public MutableLiveData<Throwable> getThrowable() {
        return observeThrowable;
    }

    public MutableLiveData<String> getObserveResponse() {
        return observeResponse;
    }

    public MutableLiveData<Long> getTotalSalesvalue() {
        return mTotalSalesValue;
    }


    public void salesStockBalance() {
        mDis.add(repository.salesStockBalance()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    salesBalane.postValue(data); }
                )
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

    public Single<Long> sunAllSoldProduct(String productid) {
        return repository.sunAllSoldProduct(productid).map(
                totals -> {
                    totalProductSold = totals;
                    return totals;
                }
        );
    }

    public void sumTotalBasketQTY() {
        mDis.add(repository.sumTotalBasketQTY()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data ->  {
                    basketResponce.postValue(data);
                },
                Throwable->{}
                )
        );
    }

    public void totalSalesValue() {
        mDis.add(repository.totalSalesValue()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        data ->  {
                            mTotalSalesValue.postValue(data);
                        },
                        Throwable->{

                        }
                )
        );
    }

    public void sumTotalBasketSales() {
        mDis.add(repository.sumTotalBasket()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data ->  basketqty.postValue(data))
        );
    }




    public void setUserDailyAttendant(int userid, int taskid, String dates,
                                      String times, String lat, String lng, String rmsg) {

        repository.setRoster(userid, taskid, dates, times, lat, lng, rmsg)
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
                                Customers customers = new Customers(
                                        0, "", "", "", "", "", "", 3, "", "", times
                                );
                                new UpdateCustomerTime().execute(customers);
                                observeResponse.postValue(Integer.toString(roster.status) + "~" + "Successful~"+times);
                            } else {
                                observeResponse.postValue(Integer.toString(roster.status) + "~" + roster.msg);
                            }
                        } else {
                            observeResponse.postValue("401" + "~" + "Server cant be reach");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        observeThrowable.postValue(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }



    public void salesEntriesGroup() {
        mDis.add(repository.salesEntriesGroup()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> groupList.postValue(data))
        );
    }

    /*public void salesEntriesGroupList(String custid) {
        mDis.add(repository.salesEntriesGroupList(custid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data ->  listchild.postValue(data))
        );
    }*/

    public LiveData<List<Sales>> salesEntriesGroupList(String custid){
        return repository.salesEntriesGroupList(custid);
    }


    private class UpdateCustomerTime extends AsyncTask<Customers, Void, Void> {
        @Override
        protected Void doInBackground(Customers... item) {
            repository.updateIndividualCustomers(item[0].rostertime, item[0].sort);
            return null;
        }
    }

}
