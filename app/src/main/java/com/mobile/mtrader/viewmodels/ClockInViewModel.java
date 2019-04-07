package com.mobile.mtrader.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.mobile.mtrader.data.AllTablesStructures.Customers;
import com.mobile.mtrader.data.AllTablesStructures.Employees;
import com.mobile.mtrader.data.AllTablesStructures.Products;
import com.mobile.mtrader.data.DataRepository;
import com.mobile.mtrader.model.ModelAttendant;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ClockInViewModel extends ViewModel {

    private DataRepository repository;
    private MutableLiveData<String> observeResponse = new MutableLiveData<>();
    private MutableLiveData<Throwable> observeThrowable = new MutableLiveData<>();

    public ClockInViewModel(DataRepository repository) {
        this.repository = repository;
    }

    public MutableLiveData<Throwable> getThrowable() {
        return observeThrowable;
    }

    public MutableLiveData<String> getObserveResponse() {
        return observeResponse;
    }

    public LiveData<Employees> setEmployeeDetails() {
        return repository.findIndividualUsers();
    }

    public LiveData<List<Products>> setBasket(String separator) {
        return repository.findAllMyProduct(separator);
    }

    public LiveData<Long> sumAllMyProduct(String separator) {
        return repository.sumAllMyProduct(separator);
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
                                        0, "", "", "", "", "", "", 1, "", "", times
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


    private class UpdateCustomerTime extends AsyncTask<Customers, Void, Void> {
        @Override
        protected Void doInBackground(Customers... item) {
            repository.updateIndividualCustomers(item[0].rostertime, item[0].sort);
            return null;
        }
    }


}
