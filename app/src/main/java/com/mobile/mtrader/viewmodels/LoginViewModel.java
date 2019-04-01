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
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;


public class LoginViewModel extends ViewModel {

    private DataRepository repository;
    private MutableLiveData<String> observeResponse = new MutableLiveData<>();
    private MutableLiveData<Throwable> observeThrowable = new MutableLiveData<>();

    LoginViewModel(DataRepository repository) {
        this.repository = repository;
    }

    public MutableLiveData<String> getNetRes() {
        return observeResponse;
    }

    public MutableLiveData<Throwable> getThrowable() {
        return observeThrowable;
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

                        ModelEmployees loginModel = response.body();
                        if (response.code() == 200) {

                           if(loginModel.status==200){

                               Employees reg = new Employees(
                                       loginModel.id,
                                       loginModel.name,
                                       loginModel.dbroute,
                                       loginModel.customer_code,
                                       loginModel.lat,
                                       loginModel.lng,
                                       loginModel.depot_waiver,
                                       loginModel.clokin,
                                       loginModel.clokout
                               );
                               new AddEmployees().execute(reg);

                               for (int i = 0; i < loginModel.modules.size(); i++) {
                                   Modules modules = new Modules(
                                           loginModel.modules.get(i).id,
                                           loginModel.modules.get(i).nav,
                                           loginModel.modules.get(i).name,
                                           loginModel.modules.get(i).imageurl
                                   );
                                   new AddModules().execute(modules);
                               }

                               for (int i = 0; i < loginModel.customers.size(); i++) {
                                   Customers customers = new Customers(
                                           loginModel.customers.get(i).id,
                                           loginModel.customers.get(i).notice,
                                           loginModel.customers.get(i).urno,
                                           loginModel.customers.get(i).customerno,
                                           loginModel.customers.get(i).outletname,
                                           loginModel.customers.get(i).lat,
                                           loginModel.customers.get(i).lng,
                                           loginModel.customers.get(i).sort,
                                           loginModel.customers.get(i).outlet_waiver,
                                           loginModel.customers.get(i).token,
                                           loginModel.customers.get(i).rostertime
                                   );
                                   new AddCustomers().execute(customers);
                               }

                               for(int i = 0; i < loginModel.product.size(); i++) {
                                    Products products = new Products(
                                            loginModel.product.get(i).id,
                                            loginModel.product.get(i).separator,
                                            loginModel.product.get(i).separatorname,
                                            loginModel.product.get(i).productcode,
                                            loginModel.product.get(i).qty,
                                            loginModel.product.get(i).soq,
                                            loginModel.product.get(i).rollprice,
                                            loginModel.product.get(i).packprice,
                                            loginModel.product.get(i).productname,
                                            "",
                                            "",
                                            "",
                                            ""
                                    );
                                    new AddProducts().execute(products);
                               }
                               observeResponse.postValue(Integer.toString(loginModel.status) + "~" + "");
                           }else{
                               observeResponse.postValue(Integer.toString(loginModel.status) + "~" + loginModel.msg);
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
}
