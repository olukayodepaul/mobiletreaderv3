package com.mobile.mtrader.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.mobile.mtrader.data.AllTablesStructures.Customers;
import com.mobile.mtrader.data.AllTablesStructures.Employees;
import com.mobile.mtrader.data.AllTablesStructures.Modules;
import com.mobile.mtrader.data.AllTablesStructures.Products;
import com.mobile.mtrader.data.AllTablesStructures.SalesEntries;
import com.mobile.mtrader.data.DataRepository;
import com.mobile.mtrader.model.ModelEmployees;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class LoginViewModel extends ViewModel {

    private MutableLiveData<String> loginres = new MutableLiveData<>();

    private DataRepository repository;

    CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    LoginViewModel(DataRepository repository) {
        this.repository = repository;
    }

    public MutableLiveData<String> callBallAllResponse() {
        return loginres;
    }

    public void processLogin(String userName, String password, String imei, String todaysDate) {

        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password) || imei.isEmpty() || todaysDate.isEmpty()) {
            loginres.postValue("1~Please enter username and password");
        } else {

            repository.userLogin(userName, password, imei)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Response<ModelEmployees>>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            mCompositeDisposable.add(d);
                        }

                        @Override
                        public void onNext(Response<ModelEmployees> response) {

                            ModelEmployees data = response.body();

                            if (response != null && response.isSuccessful() && response.body() != null && response.code() == 200) {

                                if (data.status == 200) {

                                    Disposable mDisposable = repository.checkFirstLogin(todaysDate)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(mData -> {

                                                        if (mData == 0) {

                                                            deleteFromEmployee();
                                                            deleteFromModules();
                                                            deleteFromCustomers();
                                                            deleteFromProduct();
                                                            deleteFromSalesEntries();

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
                                                                    todaysDate,
                                                                    "0.0"
                                                            );
                                                            new AddEmployees().execute(employeerdata);

                                                            for (int i = 0; i < data.modules.size(); i++) {
                                                                Modules modulesdata = new Modules(
                                                                        data.modules.get(i).id,
                                                                        data.modules.get(i).nav,
                                                                        data.modules.get(i).name,
                                                                        data.modules.get(i).imageurl
                                                                );
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
                                                                        data.product.get(i).productname
                                                                );

                                                                SalesEntries salesEntries = new SalesEntries(
                                                                        data.product.get(i).productcode,
                                                                        data.product.get(i).productname,
                                                                        0,
                                                                        "",
                                                                        "",
                                                                        "",
                                                                        "",
                                                                        "",
                                                                        "",
                                                                        "",
                                                                        "",
                                                                        "",
                                                                        ""
                                                                );
                                                                new AddProducts().execute(productsData);
                                                                new AddSalesEntries().execute(salesEntries);
                                                            }

                                                            loginres.postValue("3~");

                                                        } else {
                                                            loginres.postValue("3~");
                                                        }

                                                    }, throwable -> loginres.postValue("1~" + throwable.getMessage())
                                            );

                                    mCompositeDisposable.add(mDisposable);

                                } else {
                                    loginres.postValue("1~" + data.msg);
                                }

                            } else {
                                loginres.postValue("1~Connection Error, Please try again");
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            loginres.postValue("1~" + e.getMessage());
                        }

                        @Override
                        public void onComplete() {
                        }
                    });

        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mCompositeDisposable.clear();
    }

    private void deleteFromEmployee() {
        Completable.fromAction(() -> repository.deleteFromEmployee())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }

    private void deleteFromCustomers() {
        Completable.fromAction(() -> repository.deleteFromCustomers())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }

    private void deleteFromModules() {
        Completable.fromAction(() -> repository.deleteFromModules())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }

    private void deleteFromProduct() {
        Completable.fromAction(() -> repository.deleteFromProduct())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }

    private void deleteFromSalesEntries() {
        Completable.fromAction(() -> repository.deleteFromSalesEntries())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }

    private class AddEmployees extends AsyncTask<Employees, Void, Void> {
        @Override
        protected Void doInBackground(Employees... item) {
            repository.insert(item[0]);
            return null;
        }
    }

    private class AddModules extends AsyncTask<Modules, Void, Void> {
        @Override
        protected Void doInBackground(Modules... item) {
            repository.insertIntoModules(item[0]);
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

    private class AddSalesEntries extends AsyncTask<SalesEntries, Void, Void> {
        @Override
        protected Void doInBackground(SalesEntries... item) {
            repository.insertIntoSalesEntries(item[0]);
            return null;
        }
    }

}
