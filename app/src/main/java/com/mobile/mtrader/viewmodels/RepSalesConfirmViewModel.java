package com.mobile.mtrader.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.mobile.mtrader.data.AllTablesStructures.Products;
import com.mobile.mtrader.data.DataRepository;
import com.mobile.mtrader.frameworks.login.LoginContract;
import com.mobile.mtrader.model.DataBridge;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import io.reactivex.Flowable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class RepSalesConfirmViewModel extends ViewModel {

    private DataRepository repository;
    MutableLiveData<String> response = new MutableLiveData<>();
    CompositeDisposable mDis = new CompositeDisposable();
    List<Products> mproducts;
    List<DataBridge> result;
    DataBridge dataBridge;


    public MutableLiveData<String> getNetRes() {
        return response;
    }

    public RepSalesConfirmViewModel(DataRepository repository) {
        this.repository = repository;
    }

    public Flowable<List<Products>> salesEnteryRecord(String updatestatus, String customerno) {
        return repository.salesEnteryRecord(updatestatus, customerno).map(
                products -> {
                    mproducts = products;
                    return products;
                }
        );
    }

    public void pustSalesToServer(String updatestatus, String customerno) {

        mDis.add(repository.pustSalesToServer(updatestatus, customerno)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                            result = new ArrayList<>();

                            for (int i = 0; i < data.size(); i++) {
                                dataBridge = new DataBridge(
                                        new SimpleDateFormat("HH:mm:ss").format(new Date()),
                                        data.get(i).productname,
                                        data.get(i).productcode,
                                        data.get(i).customerno,
                                        277,
                                        data.get(i).separator,
                                        Double.parseDouble(data.get(i).orders),
                                        Double.parseDouble(data.get(i).inventory),
                                        data.get(i).pricing,
                                        1,
                                        Double.parseDouble(data.get(i).rollprice),
                                        Double.parseDouble(data.get(i).packprice),
                                        data.get(i).separatorname,
                                        "lat",
                                        "lng",
                                        "",
                                        UUID.randomUUID().toString(),
                                        ""

                                );
                                result.add(dataBridge);
                            }
                            pushToServer(result);
                        },
                        throwable -> response.postValue(throwable.getMessage())
                )
        );
    }

    public void pushToServer(List<DataBridge> results) {

        repository.sentSalesToServer(results)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<DataBridge>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<DataBridge> dataBridgeResponse) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
