package com.mobile.mtrader.viewmodels;


import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.mobile.mtrader.data.AllTablesStructures.Customers;
import com.mobile.mtrader.data.AllTablesStructures.Products;
import com.mobile.mtrader.data.DataRepository;
import com.mobile.mtrader.data.AllTablesStructures.Employees;
import com.mobile.mtrader.data.AllTablesStructures.Modules;
import com.mobile.mtrader.model.ModelEmployees;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;


public class LoginViewModel extends ViewModel {

    private DataRepository repository;
    private MutableLiveData<String> observeResponse = new MutableLiveData<>();
    private MutableLiveData<Long> initcount;
    private MutableLiveData<Throwable> observeThrowable = new MutableLiveData<>();
    CompositeDisposable mDis = new CompositeDisposable();
    private MutableLiveData<Employees> obemployee = new MutableLiveData<>();

    LoginViewModel(DataRepository repository) {
        this.repository = repository;
    }

    public MutableLiveData<String> getNetRes() {
        return observeResponse;
    }

    public MutableLiveData<Throwable> getThrowable() {
        return observeThrowable;
    }

    public MutableLiveData<Long> getInitCount() {
        if (initcount == null) {
            initcount = new MutableLiveData<>();
            getInitSize();
        }
        return initcount;
    }

    public MutableLiveData<Employees> emDetails() {
        return obemployee;
    }



    public void getEmployesProfiles() {
        mDis.add(repository.checkRosterDate()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    obemployee.postValue(data);
                        }
                )
        );
    }

    public void getInitSize() {
        mDis.add(repository.checkUsersInit()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    initcount.postValue(data);
                        }
                )
        );
    }

    public void setEmployeesDailySalesData(String username, String password, String imei) {

        repository.userLogin(username, password, imei)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<ModelEmployees>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<ModelEmployees> response) {

                        ModelEmployees data = response.body();
                        if (response.code() == 200) {

                            if (data.status == 200) {
                                Employees employeerdata = new Employees(
                                        data.id,
                                        data.name,
                                        data.dbroute,
                                        data.customer_code,
                                        data.lat,
                                        data.lng,
                                        data.depot_waiver,
                                        data.clokin,
                                        data.clokout,
                                        new SimpleDateFormat("yyyy-MM-dd").format(new Date())
                                );

                                new AddEmployees().execute(employeerdata);

                                for (int i = 0; i < data.modules.size(); i++) {
                                    Modules modulesdata = new Modules(
                                            data.modules.get(i).id,
                                            data.modules.get(i).nav,
                                            data.modules.get(i).name,
                                            data.modules.get(i).imageurl
                                    );
                                    new DeleteModules().execute(modulesdata);
                                    new AddModules().execute(modulesdata);
                                }

                                for (int i = 0; i < data.customers.size(); i++) {
                                    Customers customersdata = new Customers(
                                            data.customers.get(i).id,
                                            data.customers.get(i).notice,
                                            data.customers.get(i).urno,
                                            data.customers.get(i).customerno,
                                            data.customers.get(i).outletname,
                                            data.customers.get(i).lat,
                                            data.customers.get(i).lng,
                                            data.customers.get(i).sort,
                                            data.customers.get(i).outlet_waiver,
                                            data.customers.get(i).token,
                                            data.customers.get(i).rostertime
                                    );
                                    new AddCustomers().execute(customersdata);
                                }

                                for (int i = 0; i < data.product.size(); i++) {
                                    Products productsData = new Products(
                                            data.product.get(i).id,
                                            data.product.get(i).separator,
                                            data.product.get(i).separatorname,
                                            data.product.get(i).productcode,
                                            data.product.get(i).qty,
                                            data.product.get(i).soq,
                                            data.product.get(i).rollprice,
                                            data.product.get(i).packprice,
                                            data.product.get(i).productname,
                                            "",
                                            0,
                                            "",
                                            "",
                                            ""
                                    );
                                    new AddProducts().execute(productsData);
                                }
                                observeResponse.postValue(Integer.toString(data.status) + "~" + "");
                            } else {
                                observeResponse.postValue(Integer.toString(data.status) + "~" + data.msg);
                            }
                        } else {
                            observeResponse.postValue("404" + "~" + "Server cant be reach");
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

    private class AddModules extends AsyncTask<Modules, Void, Void> {
        @Override
        protected Void doInBackground(Modules... item) {
            repository.insertIntoModules(item[0]);
            return null;
        }
    }

    private class AddEmployees extends AsyncTask<Employees, Void, Void> {
        @Override
        protected Void doInBackground(Employees... item) {
            repository.insert(item[0]);
            return null;
        }
    }

    private class AddCustomers extends AsyncTask<Customers, Void, Void> {
        @Override
        protected Void doInBackground(Customers... item) {
            repository.insertIntoCustomers(item[0]);
            return null;
        }
    }

    private class AddProducts extends AsyncTask<Products, Void, Void> {
        @Override
        protected Void doInBackground(Products... item) {
            repository.insertIntoProducts(item[0]);
            return null;
        }
    }

    private class DeleteModules extends AsyncTask<Modules, Void, Void> {
        @Override
        protected Void doInBackground(Modules... item) {
            repository.deleteAllProducts(item[0]);
            return null;
        }
    }

}
