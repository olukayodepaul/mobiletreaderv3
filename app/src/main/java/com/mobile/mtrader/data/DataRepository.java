package com.mobile.mtrader.data;


import android.arch.lifecycle.LiveData;

import com.mobile.mtrader.data.AllTablesStructures.Customers;
import com.mobile.mtrader.data.AllTablesStructures.Employees;
import com.mobile.mtrader.data.AllTablesStructures.Modules;
import com.mobile.mtrader.data.AllTablesStructures.Products;
import com.mobile.mtrader.data.AllTablesStructures.Sales;
import com.mobile.mtrader.model.DataBridge;
import com.mobile.mtrader.model.ModelAttendant;
import com.mobile.mtrader.model.ModelEmployees;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
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

    public Long insertIntoSales(Sales sales) {
        return this.daoSQLQuery.insertIntoSales(sales);
    }

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

    public Observable<Response<ModelEmployees>> userLogin(String username, String password, String imei) {
        return api.getUserLogin(username, password, imei);
    }

    public Observable<Response<ModelAttendant>> setRoster(int userid, int taskid, String dates,
                                                          String times,String lat, String lng,String rmsg) {
        return api.getUserRoster(userid, taskid, dates, times, lat, lng, rmsg);
    }

    public void updateDailySalesBySku(String inventory, int pricing, String orders, String customerno,
                                      String updatestatus,  int id, String separator, String productcode) {
        this.daoSQLQuery.updateDailySalesBySku(inventory,pricing,orders,customerno,updatestatus,id,separator,productcode);
    }

    public Single<Long> validateUserSalesEntries(String updatestatus) {
        return this.daoSQLQuery.validateUserSalesEntries(updatestatus);
    }

    public Flowable<List<Products>> salesEnteryRecord(String updatestatus, String customerno) {
        return this.daoSQLQuery.salesEnteryRecord(updatestatus,customerno);
    }

    public Flowable<List<Products>> pustSalesToServer(String updatestatus, String customerno) {
        return this.daoSQLQuery.pustSalesToServer(updatestatus,customerno);
    }

    public Observable<Response<DataBridge>> sentSalesToServer(List<DataBridge> salesEntries) {
        return api.moveDataToServer(salesEntries);
    }

    public Flowable<Long> checkUsersInit() {
        return this.daoSQLQuery.checkUsersInit();
    }

    public Flowable<Employees> checkRosterDate() {
        return this.daoSQLQuery.checkRosterDate();
    }

    public int deleteAllProducts(Modules modules) {
        return this.daoSQLQuery.deleteAllProducts(modules);
    }

    public Flowable<List<Sales>> salesEntriesToday() {
        return this.daoSQLQuery.salesEntriesToday();
    }

    public Flowable<List<Sales>> salesEntriesGroup() {
        return this.daoSQLQuery.salesEntriesGroup();
    }

    public Flowable<List<Sales>> salesEntriesGroupList(String custid) {
        return this.daoSQLQuery.salesEntriesGroupList(custid);
    }

    public void updateIndividualCustomers(String rostertime,int sort ) {
        this.daoSQLQuery.updateIndividualCustomers(rostertime,sort);
    }

    public void updateIndividualCustomersSalesTime(String rostertime, String urno) {
        this.daoSQLQuery.updateIndividualCustomersSalesTime(rostertime,urno);
    }

    public void reInitialisProducts(String inventory, int pricing, String order,  String customerno,String updatestatus) {
        this.daoSQLQuery.reInitialisProducts(inventory,pricing,order,customerno,updatestatus);
    }

    public Single<Long> trackUnPushDataToServer(String customerno, String localstatus) {
        return this.daoSQLQuery.trackUnPushDataToServer(customerno,localstatus);
    }

    public Flowable<List<Products>> salesStockBalance() {
        return this.daoSQLQuery.salesStockBalance();
    }

    public Single<Long> sunAllSoldProduct(String productid) {
        return this.daoSQLQuery.sunAllSoldProduct(productid);
    }

}



