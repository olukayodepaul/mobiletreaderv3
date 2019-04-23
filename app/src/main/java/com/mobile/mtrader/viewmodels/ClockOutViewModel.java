package com.mobile.mtrader.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.mobile.mtrader.data.AllTablesStructures.Customers;
import com.mobile.mtrader.data.AllTablesStructures.Products;
import com.mobile.mtrader.data.DataRepository;
import com.mobile.mtrader.model.ModelAttendant;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ClockOutViewModel extends ViewModel {

    private DataRepository repository;

    CompositeDisposable mDis = new CompositeDisposable();

    ClockOutViewModel(DataRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<Products>> setBasket(String separator) {
        return repository.findAllMyProduct(separator);
    }



    public void dailyRoster() {

        mDis.add(repository.findIndividualUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {

                    repository.setRoster(
                            data.user_id,
                            6,
                            new SimpleDateFormat("yyyy-MM-dd").format(new Date()),
                            new SimpleDateFormat("HH:mm:ss").format(new Date()),
                            "0.0",
                            "0.0",
                            "Closing already taken")
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
                               /* Customers customers = new Customers(
                                        0, "", "", "", "", "", "", 4, "", "", times
                                );
                                new BankViewModel.UpdateCustomerTime().execute(customers);
                                observeResponse.postValue(Integer.toString(roster.status) + "~" + "Successful~"+times);
                                */
                                        } else {
                                            // observeResponse.postValue(Integer.toString(roster.status) + "~" + roster.msg);
                                        }
                                    } else {
                                        //observeResponse.postValue("401" + "~" + "Server cant be reach");
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {

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

}
