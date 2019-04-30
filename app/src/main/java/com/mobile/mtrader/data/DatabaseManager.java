package com.mobile.mtrader.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import com.mobile.mtrader.data.AllTablesStructures.Customers;
import com.mobile.mtrader.data.AllTablesStructures.Employees;
import com.mobile.mtrader.data.AllTablesStructures.LastLoation;
import com.mobile.mtrader.data.AllTablesStructures.Modules;
import com.mobile.mtrader.data.AllTablesStructures.Products;
import com.mobile.mtrader.data.AllTablesStructures.Sales;
import com.mobile.mtrader.data.AllTablesStructures.SalesEntries;

@Database(entities = {Employees.class, Modules.class, Customers.class, Products.class, Sales.class,
        SalesEntries.class, LastLoation.class}, version = 1 , exportSchema = false)
public abstract class DatabaseManager extends RoomDatabase {

    public abstract DatabaseDaoSQLQuery getDataaccess();

}


