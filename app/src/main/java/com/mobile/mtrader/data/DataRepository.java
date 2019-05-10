package com.mobile.mtrader.data;


import android.arch.lifecycle.LiveData;

import com.mobile.mtrader.data.AllTablesStructures.AllRepCustomers;
import com.mobile.mtrader.data.AllTablesStructures.Customers;
import com.mobile.mtrader.data.AllTablesStructures.Employees;
import com.mobile.mtrader.data.AllTablesStructures.LastLoation;
import com.mobile.mtrader.data.AllTablesStructures.Modules;
import com.mobile.mtrader.data.AllTablesStructures.Products;
import com.mobile.mtrader.data.AllTablesStructures.Sales;
import com.mobile.mtrader.data.AllTablesStructures.SalesEntries;
import com.mobile.mtrader.data.AllTablesStructures.UserSpinners;
import com.mobile.mtrader.model.DataBridge;
import com.mobile.mtrader.model.ModelAttendant;
import com.mobile.mtrader.model.ModelEmployees;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
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

    public Long insertIntoAllCustomers(AllRepCustomers allRepCustomers) {
        return this.daoSQLQuery.insertIntoAllCustomers(allRepCustomers);
    }

    public Long insertLastLocation(LastLoation lastLoation) {
        return this.daoSQLQuery.insertLastLocation(lastLoation);
    }

    public Long insertIntoUserSpinners(UserSpinners userSpinners) {
        return this.daoSQLQuery.insertIntoUserSpinners(userSpinners);
    }

    public void updateSalesEntries(int user_id, String separator,String separatorname, String rollprice, String packprice,
                                   String inventory, String pricing, String orders,
                                   String customerno, String updatestatus, String entry_date_time,String soq, String productcode ) {
        this.daoSQLQuery.updateSalesEntries(user_id, separator,separatorname, rollprice, packprice,
                inventory, pricing, orders,
                customerno, updatestatus, entry_date_time,soq,productcode);
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

    public Single<Integer> validateUserSalesEntries(String updatestatus) {
        return this.daoSQLQuery.validateUserSalesEntries(updatestatus);
    }

    public Single<Long> checkFirstLogin(String dates) {
        return this.daoSQLQuery.checkFirstLogin(dates);
    }

    public Flowable<List<SalesEntries>> salesEnteryRecord(String updatestatus, String customerno) {
        return this.daoSQLQuery.salesEnteryRecord(updatestatus,customerno);
    }

    public Flowable<List<SalesEntries>> pustSalesToServer(String updatestatus) {
        return this.daoSQLQuery.pustSalesToServer(updatestatus);
    }

    public void deleteFromEmployee() {
        this.daoSQLQuery.deleteFromEmployee();
    }

    public void deleteFromModules() {
        this.daoSQLQuery.deleteFromModules();
    }

    public void deleteFromProduct() {
        this.daoSQLQuery.deleteFromProduct();
    }

    public void deleteFromSalesEntries() {
        this.daoSQLQuery.deleteFromSalesEntries();
    }

    public void deleteFromAllRepCustomers() {
        this.daoSQLQuery.deleteFromAllRepCustomers();
    }

    public void deleteFromAllUserSpinners() {
        this.daoSQLQuery.deleteFromAllUserSpinners();
    }

    public void deleteFromCustomers() {
        this.daoSQLQuery.deleteFromCustomers();
    }

    public Observable<Response<DataBridge>> sentSalesToServer(List<DataBridge> salesEntries) {
        return api.moveDataToServer(salesEntries);
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

    public void reInitialisSalesEntries() {
        this.daoSQLQuery.reInitialisSalesEntries();
    }

    public Single<Long> trackUnPushDataToServer(String customerno, String localstatus) {
        return this.daoSQLQuery.trackUnPushDataToServer(customerno,localstatus);
    }

    public Single<Double> sunAllSoldProduct(String productid) {
        return this.daoSQLQuery.sunAllSoldProduct(productid);
    }

    public Single<Double> sunAllTotalSoldProduct() {
        return this.daoSQLQuery.sunAllTotalSoldProduct();
    }

    public Single<Double> skuTotalSum(String productcode) {
        return this.daoSQLQuery.skuTotalSum(productcode);
    }

    public Single<Double> sumAllOrder(String productcode) {
        return this.daoSQLQuery.sumAllOrder(productcode);
    }

    public Flowable<Customers> getLastloaction() {
        return this.daoSQLQuery.getLastloaction();
    }

    public Single<LastLoation> getPreviousState() {
        return this.daoSQLQuery.getPreviousState();
    }

    public Single<Integer> countAllSalesEntries() {
        return this.daoSQLQuery.countAllSalesEntries();
    }

    public LiveData<List<AllRepCustomers>> getAllCustomersList() {
        return this.daoSQLQuery.getAllCustomersList();
    }

    public Single<AllRepCustomers> getIndividualCustomerProfiles(int id) {
        return this.daoSQLQuery.getIndividualCustomerProfiles(id);
    }

    public LiveData<List<UserSpinners>> getGroupUserSpinners(int sep) {
        return this.daoSQLQuery.getGroupUserSpinners(sep);
    }

    public Single<Response<ModelAttendant>> reSetCustomerProfile(String outletname, String contactname, String address, Long phone,
                                                                 int outlet_class_id, int outlet_language_id, int outlet_type_id,
                                                                 int custno, double lat, double lng) {
        return api.reSetCustomerProfile(outletname, contactname, address, phone, outlet_class_id, outlet_language_id, outlet_type_id,
        custno, lat, lng);
    }

    public void updateMultipleCustomers(String outletname, String outletaddress, String contactname, Long contactphone,
                                        int outletclassid, int outletlanguageid, int outlettypeid,
                                        double latitude, double longitude, int id) {
        this.daoSQLQuery.updateMultipleCustomers(outletname,outletaddress, contactname, contactphone,
        outletclassid, outletlanguageid, outlettypeid, latitude, longitude, id);
    }

    public void updateLocalCustomers(String outletname, String lat, String lng, int id) {
        this.daoSQLQuery.updateLocalCustomers(outletname,lat,lng,id);
    }

    public Single<Response<ModelAttendant>> setOutletClose(int userid, String urno, double lat, double lng, String  arivaltime) {
        return api.setOutletClose(userid, urno, lat,  lng, arivaltime);
    }
}
