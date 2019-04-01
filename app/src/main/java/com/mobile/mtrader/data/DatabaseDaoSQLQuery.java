package com.mobile.mtrader.data;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.mobile.mtrader.data.AllTablesStructures.Customers;
import com.mobile.mtrader.data.AllTablesStructures.Employees;
import com.mobile.mtrader.data.AllTablesStructures.Modules;
import com.mobile.mtrader.data.AllTablesStructures.Products;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface DatabaseDaoSQLQuery {

    @Query("SELECT * FROM Modules")
    LiveData<List<Modules>> findAllModules();

    @Query("SELECT * FROM Customers order by sort asc")
    LiveData<List<Customers>> findAllCustomers();

    @Query("SELECT * FROM Products WHERE separator=:separator")
    LiveData<List<Products>> findAllMyProduct(String separator);

    @Query("SELECT SUM(qty)  FROM Products WHERE separator=:separator")
    LiveData<Long> sumAllMyProduct(String separator);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Employees employees);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertIntoModules(Modules modules);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertIntoCustomers(Customers customers);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertIntoProducts(Products products);

    @Query("SELECT * FROM Employees limit 1")
    LiveData<Employees> findIndividulUsers();

    @Query("UPDATE Customers SET rostertime = :rostertime WHERE sort=:sort")
    void updateIndividualCustomers(String rostertime, int sort);

}
