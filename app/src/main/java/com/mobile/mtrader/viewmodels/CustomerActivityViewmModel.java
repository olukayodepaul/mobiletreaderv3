package com.mobile.mtrader.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.mobile.mtrader.data.AllTablesStructures.AllRepCustomers;
import com.mobile.mtrader.data.AllTablesStructures.UserSpinners;
import com.mobile.mtrader.data.DataRepository;
import com.mobile.mtrader.model.ModelAttendant;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class CustomerActivityViewmModel extends ViewModel {

    private DataRepository repository;

    AllRepCustomers allRepCustomers;

    CompositeDisposable disposable = new CompositeDisposable();

    private MutableLiveData<String> broadcast = new MutableLiveData<>();

    public CustomerActivityViewmModel(DataRepository repository) {
        this.repository = repository;
    }

    public MutableLiveData<String> BroadCastResponse() {
        return broadcast;
    }

    public LiveData<List<AllRepCustomers>> getAllCustomersList() {
        return repository.getAllCustomersList();
    }

    public LiveData<List<UserSpinners>> getGroupUserSpinners(int sep) {
        return repository.getGroupUserSpinners(sep);
    }

    public Single<AllRepCustomers> getIndividualCustomerProfiles(int id) {
        return repository.getIndividualCustomerProfiles(id).map(
                profile -> {
                    allRepCustomers = profile;
                    return profile;
                }
        );
    }

    public void reSetUserProfile(String outletName, String contactName, String address, Long phone,
                                 int outlet_class_id, int outlet_language_id, int outlet_type_id, int custno, double lat, double lng) {

        repository.reSetCustomerProfile(outletName, contactName, address, phone, outlet_class_id, outlet_language_id, outlet_type_id, custno, lat, lng)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<ModelAttendant>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(Response<ModelAttendant> response) {

                        ModelAttendant res = response.body();

                        if (response != null && response.isSuccessful() && response.body() != null && response.code() == 200) {

                            if(res.status==200){
                                updateLocalDb(outletName, contactName, address, phone, outlet_class_id, outlet_language_id, outlet_type_id, custno, lat, lng);
                            }else{
                                broadcast.postValue("400~"+res.msg);
                            }

                        } else {
                            broadcast.postValue("400~Connection Error, Please try again");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        broadcast.postValue("400~Channel throwable error");
                    }
                });
    }

    public void updateLocalDb(String outletName, String contactName, String address, Long phone,
                              int outlet_class_id, int outlet_language_id, int outlet_type_id, int custno, double lat, double lng) {

        Completable.fromAction(()->repository.updateMultipleCustomers(outletName, address,contactName, phone, outlet_class_id, outlet_language_id, outlet_type_id, lat, lng, custno ))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onComplete() {
                        Completable.fromAction(()->repository.updateLocalCustomers(outletName, Double.toString(lat), Double.toString(lng), custno))
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new CompletableObserver() {
                                    @Override
                                    public void onSubscribe(Disposable d) {
                                        disposable.add(d);
                                    }

                                    @Override
                                    public void onComplete() {
                                        broadcast.postValue("200~");
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        broadcast.postValue("400~Channel throwable error");
                                    }
                                });
                    }

                    @Override
                    public void onError(Throwable e) {
                        broadcast.postValue("400~Channel throwable error");
                    }
                });
    }

}
