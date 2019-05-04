package com.mobile.mtrader.data;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import com.mobile.mtrader.data.AllTablesStructures.AllRepCustomers;
import com.mobile.mtrader.data.AllTablesStructures.Customers;
import com.mobile.mtrader.data.AllTablesStructures.Employees;
import com.mobile.mtrader.data.AllTablesStructures.LastLoation;
import com.mobile.mtrader.data.AllTablesStructures.Modules;
import com.mobile.mtrader.data.AllTablesStructures.Products;
import com.mobile.mtrader.data.AllTablesStructures.Sales;
import com.mobile.mtrader.data.AllTablesStructures.SalesEntries;
import com.mobile.mtrader.data.AllTablesStructures.UserSpinners;

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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertIntoSalesEntries(SalesEntries salesEntries);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertIntoAllCustomers(AllRepCustomers allRepCustomers);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertIntoUserSpinners(UserSpinners userSpinners);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertLastLocation(LastLoation lastLoation);

    @Query("UPDATE SalesEntries SET user_id=:user_id,separator=:separator, separatorname=:separatorname,rollprice=:rollprice, packprice=:packprice, inventory=:inventory, pricing=:pricing, orders=:orders, customerno=:customerno, updatestatus=:updatestatus, entry_date_time=:entry_date_time, soq=:soq where productcode=:productcode")
    void updateSalesEntries(int user_id, String separator,String separatorname, String rollprice, String packprice,
                            String inventory, String pricing, String orders,
                            String customerno, String updatestatus, String entry_date_time,String soq , String productcode);

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
    Single<Integer> validateUserSalesEntries(String updatestatus);

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

    @Query("Delete from SalesEntries")
    void deleteFromSalesEntries();

    @Query("Delete from AllRepCustomers")
    void deleteFromAllRepCustomers();

    @Query("Delete from UserSpinners")
    void deleteFromAllUserSpinners();

    @Query("SELECT * FROM SalesEntries WHERE updatestatus=:updatestatus")
    Flowable<List<SalesEntries>> pustSalesToServer(String updatestatus);

    @Query("SELECT * FROM Sales group by customerno")
    Flowable<List<Sales>> salesEntriesGroup();

    @Query("SELECT * FROM Sales where customerno=:cust")
    LiveData<List<Sales>> salesEntriesGroupList(String cust);

    @Query("UPDATE Customers SET rostertime = :rostertime WHERE sort=:sort")
    void updateIndividualCustomers(String rostertime, int sort);

    @Query("UPDATE Customers SET rostertime =:rostertime WHERE urno=:urno")
    void updateIndividualCustomersSalesTime(String rostertime, String urno);

    @Query("UPDATE SalesEntries SET user_id=0, separator ='', separatorname ='', rollprice='', packprice='', inventory='', pricing='', orders='', customerno='', updatestatus='', entry_date_time=''")
    void reInitialisSalesEntries();

    @Query("SELECT COUNT(auto) FROM Sales WHERE  customerno =:customerno AND localstatus =:localstatus ")
    Single<Long> trackUnPushDataToServer(String customerno, String localstatus);

    @Query("SELECT SUM(orders) FROM Sales WHERE productcode =:productcode")
    Single<Double> sunAllSoldProduct(String productcode);

    @Query("SELECT SUM(orders) FROM Sales")
    Single<Double> sunAllTotalSoldProduct();

    @Query("SELECT SUM(packprice+rollprice) FROM Sales WHERE productcode =:productcode")
    Single<Double> skuTotalSum(String productcode);

    @Query("SELECT SUM(orders) FROM Sales WHERE productcode =:productcode")
    Single<Double> sumAllOrder(String productcode);

    @Query("SELECT * FROM Customers WHERE sort='1' limit 1")
    Flowable<Customers> getLastloaction();

    @Query("SELECT * FROM LastLoation ORDER BY id DESC  limit 1")
    Single<LastLoation> getPreviousState ();

    @Query("SELECT COUNT(auto) FROM SalesEntries")
    Single<Integer> countAllSalesEntries();

    @Query("SELECT * FROM AllRepCustomers")
    LiveData<List<AllRepCustomers>> getAllCustomersList();

    @Query("SELECT * FROM AllRepCustomers WHERE id=:id")
    Single<AllRepCustomers> getIndividualCustomerProfiles (int id);

    @Query("SELECT * FROM userspinners WHERE sep=:sep")
    LiveData<List<UserSpinners>> getGroupUserSpinners(int sep);

}




