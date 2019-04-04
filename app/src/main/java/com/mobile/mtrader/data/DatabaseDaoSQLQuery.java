package com.mobile.mtrader.data;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.mobile.mtrader.data.AllTablesStructures.Customers;
import com.mobile.mtrader.data.AllTablesStructures.Employees;
import com.mobile.mtrader.data.AllTablesStructures.Modules;
import com.mobile.mtrader.data.AllTablesStructures.Products;
import com.mobile.mtrader.data.AllTablesStructures.Sales;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface DatabaseDaoSQLQuery {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Employees employees);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertIntoModules(Modules modules);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertIntoCustomers(Customers customers);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertIntoProducts(Products products);

    @Query("SELECT * FROM Modules")
    LiveData<List<Modules>> findAllModules();

    @Query("SELECT * FROM Customers order by sort asc")
    Flowable<List<Customers>> findAllCustomers();

    @Query("SELECT * FROM Products WHERE separator=:separator")
    LiveData<List<Products>> findAllMyProduct(String separator);

    @Query("SELECT SUM(qty)  FROM Products WHERE separator=:separator")
    LiveData<Long> sumAllMyProduct(String separator);

    @Query("SELECT * FROM Employees limit 1")
    LiveData<Employees> findIndividulUsers();

    @Query("UPDATE Customers SET rostertime = :rostertime WHERE sort=:sort")
    void updateIndividualCustomers(String rostertime, int sort);

    @Query("SELECT * FROM Products order by separator asc")
    Flowable<List<Products>> findAllUserProducts();

    @Query("UPDATE Products SET inventory = :inventory, pricing =:pricing, orders =:orders, customerno = :customerno, updatestatus =:updatestatus WHERE id=:id AND separator =:separator AND productcode =:productcode")
    void updateDailySalesBySku(String inventory, int pricing, String orders, String customerno,
                                    String updatestatus,  int id, String separator, String productcode);

    @Query("SELECT COUNT(id) FROM PRODUCTS WHERE  updatestatus =:updatestatus")
    Single<Long> validateUserSalesEntries(String updatestatus);

    @Query("SELECT * FROM Products WHERE updatestatus=:updatestatus AND customerno =:customerno")
    Flowable<List<Products>> salesEnteryRecord(String updatestatus, String customerno);

    @Query("SELECT SUM(pricing) FROM Products WHERE updatestatus=:updatestatus AND customerno =:customerno")
    Flowable<Long> totalSalesValue(String updatestatus, String customerno);

    @Query("SELECT SUM(inventory) FROM Products WHERE updatestatus=:updatestatus AND customerno =:customerno")
    Flowable<Long> totalEntryInventory(String updatestatus, String customerno);

    @Query("SELECT SUM(orders) FROM Products WHERE updatestatus=:updatestatus AND customerno =:customerno")
    Flowable<Long> totalEntryOrder(String updatestatus, String customerno);



}




