package com.mobile.mtrader.viewmodels;


import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.mobile.mtrader.data.AllTablesStructures.Employees;
import com.mobile.mtrader.data.AllTablesStructures.Products;
import com.mobile.mtrader.data.DataRepository;
import com.mobile.mtrader.model.SoqMapperModel;

import java.util.List;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;


public class DailySalesViewModule extends ViewModel {

    private DataRepository repository;

    List<Products> mproducts;

    Integer countItems;

    Integer countAllItems;

    Employees employees;

    CompositeDisposable mDis = new CompositeDisposable();

    DailySalesViewModule(DataRepository repository) {
        this.repository = repository;
    }

    public Flowable<List<Products>> getLiveCustomers() {
        return repository.findAllUserProducts().map(
                products -> {
                    mproducts = products;
                    return products;
                }
        );
    }

    public Single<Integer> validateUserSalesEntries(String updatestatus) {
        return repository.validateUserSalesEntries(updatestatus).map(
                mItems -> {
                    countItems = mItems;
                    return mItems;
                }
        );
    }

    public Single<Integer> countAllSalesEntries() {
        return repository.countAllSalesEntries().map(
                allMItems -> {
                    countAllItems = allMItems;
                    return allMItems;
                }
        );
    }

    public void mapSoqToProducts(String customerno) {

        repository.pullServerSoq(customerno)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<SoqMapperModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDis.add(d);
                    }

                    @Override
                    public void onSuccess(Response<SoqMapperModel> response) {

                        SoqMapperModel data = response.body();

                        if (response != null && response.isSuccessful() && response.body() != null && response.code() == 200) {

                            if (data.status == 200) {
                                for(int i =0; i < data.customersoq.size(); i++) {
                                    setNewSoqForCustomer(data.customersoq.get(i).soq, data.customersoq.get(i).skucode) ;

                                }
                            }else{
                            }
                        }else{
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }

    public void setNewSoqForCustomer(String soq, String productcode) {

        Completable.fromAction(()->repository.updateLocalSoq(soq,productcode))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) { mDis.add(d);}

                    @Override
                    public void onComplete() {}

                    @Override
                    public void onError(Throwable e) {}
                });
    }



    public Flowable<Employees> getUsersIndividualInformation() {
        return repository.findIndividualUsers().map(
                em -> {
                    employees = em;
                    return em;
                }
        );
    }

    public void updateSalesEntries(int user_id, String separator,String separatorname, String rollprice, String packprice,
                         String inventory, String pricing, String orders,
                         String customerno, String updatestatus, String entry_date_time,String soq, String productcode) {

         Completable.fromAction(() ->repository.updateSalesEntries (
         user_id, separator,separatorname, rollprice, packprice,
                inventory, pricing, orders,
                 customerno, updatestatus, entry_date_time,soq,productcode

         ))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDis.add(d);
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mDis.clear();
    }
}
