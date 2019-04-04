package com.mobile.mtrader.data;


import android.arch.lifecycle.LiveData;

import com.mobile.mtrader.data.AllTablesStructures.Customers;
import com.mobile.mtrader.data.AllTablesStructures.Employees;
import com.mobile.mtrader.data.AllTablesStructures.Modules;
import com.mobile.mtrader.data.AllTablesStructures.Products;
import com.mobile.mtrader.data.AllTablesStructures.Sales;
import com.mobile.mtrader.model.ModelAttendant;
import com.mobile.mtrader.model.ModelEmployees;


import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.Response;

public class DataRepository  {


    private final DatabaseDaoSQLQuery daoSQLQuery;
    private final Api api;

    @Inject
    public DataRepository(DatabaseDaoSQLQuery daoSQLQuery, Api api) {
        this.daoSQLQuery = daoSQLQuery;
        this.api = api;
    }

    public Long insert(Employees employees) {
        return this.daoSQLQuery.insert(employees);
    }

    public Long insertIntoModules(Modules modules) {
        return this.daoSQLQuery.insertIntoModules(modules);
    }

    public Long insertIntoCustomers(Customers customers) {
        return this.daoSQLQuery.insertIntoCustomers(customers);
    }

    public Long insertIntoProducts(Products products) {
        return this.daoSQLQuery.insertIntoProducts(products);
    }

    /*
    public int deleteItem(Employees registrationEntityTable) {
        return daoSQLQuery.deleteItem(registrationEntityTable);
    }
    */

    public Flowable<List<Customers>> findAllCustomers() {
        return daoSQLQuery.findAllCustomers();
    }

    public LiveData<List<Modules>> findAllModules() {
        return daoSQLQuery.findAllModules();
    }

    public LiveData<List<Products>> findAllMyProduct(String id) {
        return this.daoSQLQuery.findAllMyProduct(id);
    }

    public LiveData<Long> sumAllMyProduct(String id) {
        return this.daoSQLQuery.sumAllMyProduct(id);
    }

    public LiveData<Employees> findIndividualUsers() {
        return this.daoSQLQuery.findIndividulUsers();
    }

    public Flowable<List<Products>>findAllUserProducts() {
         return this.daoSQLQuery.findAllUserProducts();
    }

    public void updateIndividualCustomers(String rostertime,int sort ) {
        this.daoSQLQuery.updateIndividualCustomers(rostertime,sort);
    }

    public Observable<Response<ModelEmployees>> userLogin(String username, String password, String imei) {
        return api.getUserLogin(username, password, imei);
    }

    public Observable<Response<ModelAttendant>> setRoster(int userid, int taskid, String dates,
                                                          String times,String lat, String lng,String rmsg) {
        return api.getUserRoster(userid, taskid, dates, times, lat, lng, rmsg);
    }



}

