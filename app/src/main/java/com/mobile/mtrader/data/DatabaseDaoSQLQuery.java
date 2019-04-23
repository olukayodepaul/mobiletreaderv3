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
import com.mobile.mtrader.data.AllTablesStructures.Sales;
import com.mobile.mtrader.data.AllTablesStructures.SalesEntries;

import java.util.List;
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

    //add the sales entries
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertIntoSalesEntries(SalesEntries salesEntries);

    @Query("UPDATE SalesEntries SET user_id=:user_id,separator=:separator, separatorname=:separatorname,rollprice=:rollprice, packprice=:packprice, inventory=:inventory, pricing=:pricing, orders=:orders, customerno=:customerno, updatestatus=:updatestatus, entry_date_time=:entry_date_time where productcode=:productcode")
    void updateSalesEntries(int user_id, String separator,String separatorname, String rollprice, String packprice,
                            String inventory, String pricing, String orders,
                            String customerno, String updatestatus, String entry_date_time,String productcode);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertIntoProducts(Products products);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertIntoSales(Sales sales);

    @Query("SELECT * FROM Modules")
    LiveData<List<Modules>> findAllModules();

    @Query("SELECT * FROM Customers order by sort asc")

    LiveData<List<Customers>> findAllCustomers();

    @Query("SELECT * FROM Products WHERE separator=:separator")
    LiveData<List<Products>> findAllMyProduct(String separator);

    @Query("SELECT * FROM Employees limit 1")
    Flowable<Employees> findIndividulUsers();

    @Query("SELECT * FROM Products order by separator asc")
    Flowable<List<Products>> findAllUserProducts();

    @Query("SELECT COUNT(auto) FROM SalesEntries WHERE  updatestatus =:updatestatus")
    Single<Long> validateUserSalesEntries(String updatestatus);

    @Query("SELECT COUNT(id) FROM Employees WHERE  mdate =:mdate")
    Single<Long> checkFirstLogin(String mdate);

    @Query("SELECT * FROM SalesEntries WHERE updatestatus=:updatestatus AND customerno =:customerno")
    Flowable<List<SalesEntries>> salesEnteryRecord(String updatestatus, String customerno);

    @Query("Delete from Employees")
    void deleteFromEmployee();

    @Query("Delete from Modules")
    void deleteFromModules();

    @Query("Delete from Products")
    void deleteFromProduct();

    @Query("Delete from Customers")
    void deleteFromCustomers();

    /*
    @Query("SELECT * FROM Products WHERE updatestatus=:updatestatus AND customerno =:customerno")
    Flowable<List<Products>> pustSalesToServer(String updatestatus, String customerno);
    */

    @Query("SELECT count(mdate) FROM Employees")
    Flowable<Long> checkUsersInit();

    @Query("SELECT * FROM Employees")
    Flowable<Employees> checkRosterDate();

    @Delete()
    int deleteAllProducts(Modules modules);

    @Query("SELECT * FROM Sales WHERE separatorname = 'own brands' and localstatus = '1'")
    Flowable<List<Sales>> salesEntriesToday();

    @Query("SELECT * FROM Sales group by customerno")
    Flowable<List<Sales>> salesEntriesGroup();

    /*@Query("SELECT * FROM Sales where customerno=:cust")
    Flowable<List<Sales>> salesEntriesGroupList(String cust);
    */

    //return live data here.
    @Query("SELECT * FROM Sales where customerno=:cust")
    LiveData<List<Sales>> salesEntriesGroupList(String cust);


    @Query("UPDATE Customers SET rostertime = :rostertime WHERE sort=:sort")
    void updateIndividualCustomers(String rostertime, int sort);

    @Query("UPDATE Customers SET rostertime =:rostertime WHERE urno=:urno")
    void updateIndividualCustomersSalesTime(String rostertime, String urno);

    /*@Query("UPDATE Products SET inventory =:inventory, pricing =:pricing, orders=:orders, customerno=:customerno, updatestatus=:updatestatus ")
    void reInitialisProducts(String inventory, int pricing, String orders, String customerno,String updatestatus);*/

    @Query("SELECT COUNT(auto) FROM Sales WHERE  customerno =:customerno AND localstatus =:localstatus ")
    Single<Long> trackUnPushDataToServer(String customerno, String localstatus);

    @Query("SELECT * FROM Products where separator = 1")
    Flowable<List<Products>> salesStockBalance();

    @Query("SELECT SUM(orders) FROM Sales WHERE productcode =:productcode")
    Single<Long> sunAllSoldProduct(String productcode);

    @Query("SELECT SUM(orders) FROM Sales")
    Single<Long> sumTotalBasketQTY();

    @Query("SELECT SUM(qty) FROM Products")
    Single<Long> sumTotalBasket();

    @Query("SELECT SUM((rollprice*rollqty)+(packprice*packqty)) FROM Sales WHERE separatorname = 'own brands' AND localstatus = '1'")
    Single<Long> totalSalesValue();


}




