package com.mobile.mtrader.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;
import android.util.Log;
import com.mobile.mtrader.data.AllTablesStructures.Customers;
import com.mobile.mtrader.data.AllTablesStructures.Sales;
import com.mobile.mtrader.data.AllTablesStructures.SalesEntries;
import com.mobile.mtrader.data.DataRepository;
import com.mobile.mtrader.model.DataBridge;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
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

    List<DataBridge> result;

    DataBridge dataBridge;

    MutableLiveData<List<SalesEntries>> SalesEntriesdata = new MutableLiveData<>();


    public RepSalesConfirmViewModel(DataRepository repository) {
        this.repository = repository;
    }

    public MutableLiveData<String> observeResponse() {
        return response;
    }

    public MutableLiveData<List<SalesEntries>> SalesEntriesdata() {
        return SalesEntriesdata;
    }


    public void salesEntries(String updatestatus, String customerno) {
        mDis.add(repository.salesEnteryRecord(updatestatus, customerno)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data ->SalesEntriesdata.postValue(data),
                        throwable -> Log.e("ZERO_ITEM_POPULAR_ERROR", throwable.getMessage())
                )
        );
    }

    public void pustSalesToServer(String latlng, String artime, String custtoken) {

        String dates = new SimpleDateFormat("HH:mm:ss").format(new Date());
        String uiid = UUID.randomUUID().toString();
        String[] arr = latlng.split("\\~");

        mDis.add(repository.pustSalesToServer("1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {

                            result = new ArrayList<>();

                            for (int i = 0; i < data.size(); i++) {
                                dataBridge = new DataBridge(
                                        data.get(i).entry_date_time,
                                        data.get(i).productname,
                                        data.get(i).productcode,
                                        data.get(i).customerno,
                                        data.get(i).user_id,
                                        data.get(i).separator,
                                        data.get(i).orders,
                                        Double.parseDouble(data.get(i).inventory),
                                        Integer.parseInt(data.get(i).pricing),
                                        1,
                                        Double.parseDouble(data.get(i).rollprice),
                                        Double.parseDouble(data.get(i).packprice),
                                        data.get(i).separatorname,
                                        arr[0],
                                        arr[1],
                                        dates,
                                        uiid,
                                        "open",
                                        latlng,
                                        artime,
                                        new SimpleDateFormat("HH:mm:ss").format(new Date()),
                                        custtoken,
                                        data.get(i).soq
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
                        mDis.add(d);
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
                                        data.push.get(i).customername,
                                        data.push.get(i).salescommission
                                );
                                new SaveSalesRecord().execute(sales);
                            }

                            Customers customers = new Customers(
                                    0,0, "", data.urno, "",
                                    "", "", "", 0,
                                    "", "", "open "+data.mdate
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

    @Override
    protected void onCleared() {
        super.onCleared();
        mDis.clear();
    }
}
