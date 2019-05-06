package com.mobile.mtrader.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mobile.mtrader.data.AllTablesStructures.AllRepCustomers;
import com.mobile.mtrader.data.AllTablesStructures.Customers;
import com.mobile.mtrader.data.AllTablesStructures.Employees;
import com.mobile.mtrader.data.AllTablesStructures.Modules;
import com.mobile.mtrader.data.AllTablesStructures.Products;
import com.mobile.mtrader.data.AllTablesStructures.SalesEntries;
import com.mobile.mtrader.data.AllTablesStructures.UserSpinners;
import com.mobile.mtrader.data.DataRepository;
import com.mobile.mtrader.firebasemodel.UserLocation;
import com.mobile.mtrader.mobiletreaderv3.R;
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

    public void processLogin(String userName, String password, String imei, String todaysDate, FirebaseAuth mAuth) {


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
                                    AuthFireBase(mAuth, data.id);
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
                                                            deleteFromAllRepCustomers();
                                                            deleteFromAllUserSpinners();

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

                                                            for(int i = 0 ; i < data.repcustomers.size(); i++) {

                                                                AllRepCustomers allRepCustomers = new AllRepCustomers(
                                                                        data.repcustomers.get(i).id,
                                                                        data.repcustomers.get(i).urno,
                                                                        data.repcustomers.get(i).customerno,
                                                                        data.repcustomers.get(i).outletclassid,
                                                                        data.repcustomers.get(i).outletlanguageid,
                                                                        data.repcustomers.get(i).outlettypeid,
                                                                        data.repcustomers.get(i).outletname,
                                                                        data.repcustomers.get(i).outletaddress,
                                                                        data.repcustomers.get(i).contactname,
                                                                        data.repcustomers.get(i).contactphone,
                                                                        data.repcustomers.get(i).latitude,
                                                                        data.repcustomers.get(i).longitude,
                                                                        data.repcustomers.get(i).outlet_pic
                                                                );

                                                                new AddRepCustomers().execute(allRepCustomers);
                                                            }

                                                            for(int i = 0 ; i < data.spinners.size(); i++){

                                                                UserSpinners userSpinners = new UserSpinners(
                                                                        data.spinners.get(i).id,
                                                                        data.spinners.get(i).name,
                                                                        data.spinners.get(i).sep
                                                                );
                                                                new AddUserSpinnersextends().execute(userSpinners);
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

    private void deleteFromAllRepCustomers() {
        Completable.fromAction(() -> repository.deleteFromAllRepCustomers())
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

    private void deleteFromAllUserSpinners() {
        Completable.fromAction(() -> repository.deleteFromAllUserSpinners())
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

    private class AddRepCustomers extends AsyncTask<AllRepCustomers, Void, Void> {
        @Override
        protected Void doInBackground(AllRepCustomers... item) {
            repository.insertIntoAllCustomers(item[0]);
            return null;
        }
    }

    private class AddUserSpinnersextends extends  AsyncTask<UserSpinners, Void, Void> {
        @Override
        protected Void doInBackground(UserSpinners... item) {
            repository.insertIntoUserSpinners(item[0]);
            return null;
        }
    }

    private void AuthFireBase(FirebaseAuth mAuth, int id) {

        mAuth.createUserWithEmailAndPassword("users"+id+"@mobiletrader.com","oQEJGbHKtXGjpCTZ6Bn8")
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            FirebaseUser c_user = mAuth.getCurrentUser();
                            if (c_user != null) {

                                FirebaseFirestore db = FirebaseFirestore.getInstance();

                                UserLocation userLocation = new UserLocation();
                                userLocation.setEmail("users"+id+"@mobiletrader.com");
                                userLocation.setLat("");
                                userLocation.setLng("");

                                db.collection("user location")
                                        .add(userLocation)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                            }
                                        });
                            }
                        }
                    }
                });
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        mCompositeDisposable.clear();
    }


}
