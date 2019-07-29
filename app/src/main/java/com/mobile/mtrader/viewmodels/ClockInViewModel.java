package com.mobile.mtrader.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;
import com.mobile.mtrader.data.AllTablesStructures.Customers;
import com.mobile.mtrader.data.AllTablesStructures.LastLoation;
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

public class ClockInViewModel extends ViewModel {

    private DataRepository repository;

    private MutableLiveData<String> observeResponse = new MutableLiveData<>();

    CompositeDisposable mDis = new CompositeDisposable();

    public ClockInViewModel(DataRepository repository) {
        this.repository = repository;
    }

    public MutableLiveData<String> getObserveResponse() {
        return observeResponse;
    }

    public LiveData<List<Products>> setBasket(String separator) {
        return repository.findAllMyProduct(separator);
    }

    public void insertLastLocation() {
        mDis.add(repository.getLastloaction()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(data->{
            LastLoation lastLoation = new LastLoation(data.urno, data.lat, data.lng);
            new AddLastLocation().execute(lastLoation);
         })
        );
    }


    public void dailyClockOut() {

        String dates = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String times = new SimpleDateFormat("HH:mm:ss").format(new Date());

        mDis.add(repository.findIndividualUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {

                    repository.setRoster(data.user_id, 2, dates, times, "0.0", "0.0",
                            "Clockout already taken")
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<Response<ModelAttendant>>() {
                                @Override
                                public void onSubscribe(Disposable d) {
                                    mDis.add(d);
                                }

                                @Override
                                public void onNext(Response<ModelAttendant> response) {

                                    ModelAttendant roster = response.body();

                                    if (response.code() == 200) {
                                        if (roster.status == 200) {
                                            observeResponse.postValue(Integer.toString(roster.status) + "~" + "Successful~" + times);
                                        } else {
                                            observeResponse.postValue(Integer.toString(roster.status) + "~" + roster.msg);
                                        }
                                    } else {
                                        observeResponse.postValue("401" + "~" + "Server cant be reach");
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {
                                    observeResponse.postValue("401" + "~" + e.getMessage());
                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                })
        );
    }

    public void dailyRoster() {

        String dates = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String times = new SimpleDateFormat("HH:mm:ss").format(new Date());

        mDis.add(repository.findIndividualUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {

                    repository.setRoster(data.user_id, 1, dates, times, "0.0", "0.0",
                            "Resumption already taken")
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<Response<ModelAttendant>>() {
                                @Override
                                public void onSubscribe(Disposable d) {
                                    mDis.add(d);
                                }

                                @Override
                                public void onNext(Response<ModelAttendant> response) {

                                    ModelAttendant roster = response.body();

                                    if (response.code() == 200) {
                                        if (roster.status == 200) {
                                            Customers customers = new Customers(0,0, "", "", "", "", "", "", 1, "", "", times);
                                            new UpdateCustomerTime().execute(customers);
                                            observeResponse.postValue(Integer.toString(roster.status) + "~" + "Successful~" + times);
                                        } else {
                                            observeResponse.postValue(Integer.toString(roster.status) + "~" + roster.msg);
                                        }
                                    } else {
                                        observeResponse.postValue("401" + "~" + "Server cant be reach");
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {
                                    observeResponse.postValue("401" + "~" + e.getMessage());
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

    private class AddLastLocation extends AsyncTask<LastLoation, Void, Void> {
        @Override
        protected Void doInBackground(LastLoation... item) {
            repository.insertLastLocation(item[0]);
            return null;
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mDis.clear();
    }
}
