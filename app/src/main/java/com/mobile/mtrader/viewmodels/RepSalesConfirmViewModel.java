package com.mobile.mtrader.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.mobile.mtrader.data.AllTablesStructures.Customers;
import com.mobile.mtrader.data.AllTablesStructures.Products;
import com.mobile.mtrader.data.AllTablesStructures.Sales;
import com.mobile.mtrader.data.DataRepository;
import com.mobile.mtrader.model.DataBridge;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import io.reactivex.Flowable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class RepSalesConfirmViewModel extends ViewModel {

    private DataRepository repository;
    MutableLiveData<String> response = new MutableLiveData<>();
    CompositeDisposable mDis = new CompositeDisposable();
    List<Products> mproducts;
    List<DataBridge> result;
    DataBridge dataBridge;


    public RepSalesConfirmViewModel(DataRepository repository) {
        this.repository = repository;
    }

    public MutableLiveData<String> observeResponse() {
        return response;
    }


    public Flowable<List<Products>> salesEnteryRecord(String updatestatus, String customerno) {
        return repository.salesEnteryRecord(updatestatus, customerno).map(
                products -> {
                    mproducts = products;
                    return products;
                }
        );
    }

    public void pustSalesToServer(String updatestatus, String customerno,
                                  int user_id, String artime, String outletstatus,
                                  String lat, String lng, String uiid) {

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
                                        user_id,
                                        data.get(i).separator,
                                        Double.parseDouble(data.get(i).orders),
                                        Double.parseDouble(data.get(i).inventory),
                                        data.get(i).pricing,
                                        1,
                                        Double.parseDouble(data.get(i).rollprice),
                                        Double.parseDouble(data.get(i).packprice),
                                        data.get(i).separatorname,
                                        lat,
                                        lng,
                                        artime,
                                        uiid,
                                        outletstatus
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
        response.postValue("501");
        repository.sentSalesToServer(results)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<DataBridge>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response<DataBridge> resp) {

                        DataBridge data = resp.body();

                        if (data.mstatus == 200) {
                            for (int i = 0; i < data.push.size(); i++) {
                                Sales sales = new Sales(
                                        data.push.get(i).map_token,
                                        data.push.get(i).separatorname,
                                        data.push.get(i).productcode,
                                        data.push.get(i).productname,
                                        "1",
                                        data.push.get(i).inventory,
                                        data.push.get(i).pricing,
                                        data.push.get(i).order,
                                        data.push.get(i).customerno,
                                        data.push.get(i).salestime,
                                        data.push.get(i).rollqty,
                                        data.push.get(i).packqty,
                                        data.push.get(i).rollprice,
                                        data.push.get(i).packprice,
                                        data.push.get(i).customername
                                );
                                new SaveSalesRecord().execute(sales);
                            }

                            Customers customers = new Customers(
                                    0, "", data.urno, "",
                                    "", "", "", 0,
                                    "", "", data.mdate
                            );
                            new UpdateCustomerTime().execute(customers);
                            response.postValue("200");
                        } else {
                            response.postValue("400");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        response.postValue("500");
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private class SaveSalesRecord extends AsyncTask<Sales, Void, Void> {
        @Override
        protected Void doInBackground(Sales... item) {
            repository.insertIntoSales(item[0]);
            return null;
        }
    }

    private class UpdateCustomerTime extends AsyncTask<Customers, Void, Void> {
        @Override
        protected Void doInBackground(Customers... item) {
            repository.updateIndividualCustomersSalesTime(item[0].rostertime, item[0].urno);
            return null;
        }
    }
}
