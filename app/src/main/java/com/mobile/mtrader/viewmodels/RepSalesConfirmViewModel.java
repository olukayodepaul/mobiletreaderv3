package com.mobile.mtrader.viewmodels;

import android.arch.lifecycle.ViewModel;

import com.mobile.mtrader.data.AllTablesStructures.Products;
import com.mobile.mtrader.data.DataRepository;
import com.mobile.mtrader.frameworks.login.LoginContract;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.functions.Function;

public class RepSalesConfirmViewModel extends ViewModel{

    private DataRepository repository;

    List<Products> mproducts;
    Long totalPricing;

    public RepSalesConfirmViewModel(DataRepository repository){
        this.repository = repository;
    }

    public Flowable<List<Products>> salesEnteryRecord(String updatestatus, String customerno) {
        return repository.salesEnteryRecord(updatestatus,customerno).map(
                products -> {
                    mproducts = products;
                    return products;
                }
        );
    }

    public Flowable<Long> totalEntryPrice(String updatestatus, String customerno) {
        return repository.totalSalesValue(updatestatus,customerno).map(
                total->{
                    totalPricing = total;
                    return total;
                }
        );
    }

    public Flowable<Long> totalEntryInventory(String updatestatus, String customerno) {
        return repository.totalEntryInventory(updatestatus,customerno).map(
                total->{
                    totalPricing = total;
                    return total;
                }
        );
    }

    public Flowable<Long> totalEntryOrder(String updatestatus, String customerno) {
        return repository.totalEntryOrder(updatestatus,customerno).map(
                total->{
                    totalPricing = total;
                    return total;
                }
        );
    }







}
