package com.mobile.mtrader.data.AllTablesStructures;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class SalesEntries {

    @PrimaryKey(autoGenerate = true)
    public int auto;
    public String productcode;
    public String productname;
    public int user_id;
    public String separator;
    public String separatorname;
    public String rollprice;
    public String packprice;
    public String inventory;
    public String pricing;
    public double orders;
    public String customerno;
    public String updatestatus;
    public String entry_date_time;
    public String soq;
    public int rollqty;
    public int packqty;

    public SalesEntries(String productcode, String productname, int user_id, String separator, String separatorname,
                        String rollprice, String packprice,
                        String inventory, String pricing, double orders, String customerno,
                        String updatestatus, String entry_date_time, String soq, int rollqty, int packqty) {
        this.productcode = productcode;
        this.productname = productname;
        this.user_id = user_id;
        this.separator = separator;
        this.separatorname = separatorname;
        this.rollprice = rollprice;
        this.packprice = packprice;
        this.inventory = inventory;
        this.pricing = pricing;
        this.orders = orders;
        this.customerno = customerno;
        this.updatestatus = updatestatus;
        this.entry_date_time = entry_date_time;
        this.soq = soq;
        this.rollqty = rollqty;
        this.packqty = packqty;

    }

    public int getAuto() {
        return auto;
    }

    public String getProductcode() {
        return productcode;
    }

    public String getProductname() {
        return productname;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getSeparator() {
        return separator;
    }

    public String getSeparatorname() {
        return separatorname;
    }

    public String getRollprice() {
        return rollprice;
    }

    public String getPackprice() {
        return packprice;
    }

    public String getInventory() {
        return inventory;
    }

    public String getPricing() {
        return pricing;
    }

    public double getOrders() {
        return orders;
    }

    public String getCustomerno() {
        return customerno;
    }

    public String getUpdatestatus() {
        return updatestatus;
    }

    public String getEntry_date_time() {
        return entry_date_time;
    }

    public void setAuto(int auto) {
        this.auto = auto;
    }

    public String getSoq() {
        return soq;
    }

    public int getRollqty() {
        return rollqty;
    }

    public int getPackqty() {
        return packqty;
    }
}
