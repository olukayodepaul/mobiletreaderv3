package com.mobile.mtrader.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import com.mobile.mtrader.data.AllTablesStructures.Customers;
import com.mobile.mtrader.data.AllTablesStructures.Employees;
import com.mobile.mtrader.data.AllTablesStructures.Modules;
import com.mobile.mtrader.data.AllTablesStructures.Products;

@Database(entities = {Employees.class, Modules.class, Customers.class, Products.class}, version = 1 , exportSchema = false)
public abstract class DatabaseManager extends RoomDatabase {

    public abstract DatabaseDaoSQLQuery getDataaccess();

}


