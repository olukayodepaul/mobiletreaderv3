package com.mobile.mtrader.data;


import android.arch.lifecycle.LiveData;

import com.mobile.mtrader.data.AllTablesStructures.Customers;
import com.mobile.mtrader.data.AllTablesStructures.Employees;
import com.mobile.mtrader.data.AllTablesStructures.Modules;
import com.mobile.mtrader.data.AllTablesStructures.Products;
import com.mobile.mtrader.data.AllTablesStructures.Sales;
import com.mobile.mtrader.data.AllTablesStructures.SalesEntries;
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

    public Long insertIntoSalesEntries(SalesEntries salesEntries) {
        return this.daoSQLQuery.insertIntoSalesEntries(salesEntries);
    }

    public void updateSalesEntries(int user_id, String separator,String separatorname, String rollprice, String packprice,
                                   String inventory, String pricing, String orders,
                                   String customerno, String updatestatus, String entry_date_time,String productcode) {
        this.daoSQLQuery.updateSalesEntries(user_id, separator,separatorname, rollprice, packprice,
                inventory, pricing, orders,
                customerno, updatestatus, entry_date_time,productcode);
    }

    public Long insertIntoProducts(Products products) {
        return this.daoSQLQuery.insertIntoProducts(products);
    }

    public Long insertIntoSales(Sales sales) {
        return this.daoSQLQuery.insertIntoSales(sales);
    }

    public LiveData<List<Customers>> findAllCustomers() {
        return daoSQLQuery.findAllCustomers();
    }

    public LiveData<List<Modules>> findAllModules() {
        return daoSQLQuery.findAllModules();
    }

    public LiveData<List<Products>> findAllMyProduct(String id) {
        return this.daoSQLQuery.findAllMyProduct(id);
    }

    public Flowable<Employees> findIndividualUsers() {
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

    public Single<Long> validateUserSalesEntries(String updatestatus) {
        return this.daoSQLQuery.validateUserSalesEntries(updatestatus);
    }

    public Single<Long> checkFirstLogin(String dates) {
        return this.daoSQLQuery.checkFirstLogin(dates);
    }

    public Flowable<List<SalesEntries>> salesEnteryRecord(String updatestatus, String customerno) {
        return this.daoSQLQuery.salesEnteryRecord(updatestatus,customerno);
    }

     /*
    public Flowable<List<Products>> pustSalesToServer(String updatestatus, String customerno) {
        return this.daoSQLQuery.pustSalesToServer(updatestatus,customerno);
    }*/

    public void deleteFromEmployee() {
        this.daoSQLQuery.deleteFromEmployee();
    }

    public void deleteFromModules() {
        this.daoSQLQuery.deleteFromModules();
    }

    public void deleteFromProduct() {
        this.daoSQLQuery.deleteFromProduct();
    }

    public void deleteFromCustomers() {
        this.daoSQLQuery.deleteFromCustomers();
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

    public Flowable<List<Sales>> salesEntriesGroup() {
        return this.daoSQLQuery.salesEntriesGroup();
    }

    public LiveData<List<Sales>> salesEntriesGroupList(String custid) {
        return this.daoSQLQuery.salesEntriesGroupList(custid);
    }

    public void updateIndividualCustomers(String rostertime,int sort ) {
        this.daoSQLQuery.updateIndividualCustomers(rostertime,sort);
    }

    public void updateIndividualCustomersSalesTime(String rostertime, String urno) {
        this.daoSQLQuery.updateIndividualCustomersSalesTime(rostertime,urno);
    }

    /*public void reInitialisProducts(String inventory, int pricing, String order,  String customerno,String updatestatus) {
        this.daoSQLQuery.reInitialisProducts(inventory,pricing,order,customerno,updatestatus);
    }*/

    public Single<Long> trackUnPushDataToServer(String customerno, String localstatus) {
        return this.daoSQLQuery.trackUnPushDataToServer(customerno,localstatus);
    }

    public Flowable<List<Products>> salesStockBalance() {
        return this.daoSQLQuery.salesStockBalance();
    }

    public Single<Long> sunAllSoldProduct(String productid) {
        return this.daoSQLQuery.sunAllSoldProduct(productid);
    }

    public Single<Long> sumTotalBasketQTY() {
        return this.daoSQLQuery.sumTotalBasketQTY();
    }

    public Single<Long> sumTotalBasket() {
        return this.daoSQLQuery.sumTotalBasket();
    }

    public Single<Long> totalSalesValue() {
        return this.daoSQLQuery.totalSalesValue();
    }



}



