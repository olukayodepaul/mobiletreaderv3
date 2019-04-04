package com.mobile.mtrader.data.AllTablesStructures;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Products {

    @PrimaryKey(autoGenerate = true)
    public int auto;
    public int id;
    public String separator;
    public String separatorname;
    public String productcode;
    public String qty;
    public String soq;
    public String rollprice;
    public String packprice;
    public String productname;
    public String inventory;
    public int pricing;
    public String orders;
    public String customerno;
    public String updatestatus;


    public Products(int id, String separator, String separatorname, String productcode, String qty, String soq, String rollprice, String packprice, String productname,
                    String inventory, int pricing, String orders, String customerno, String updatestatus) {

        this.id = id;
        this.separator = separator;
        this.separatorname = separatorname;
        this.productcode = productcode;
        this.qty = qty;
        this.soq = soq;
        this.rollprice = rollprice;
        this.packprice = packprice;
        this.productname = productname;
        this.inventory = inventory;
        this.pricing = pricing;
        this.orders = orders;
        this.customerno = customerno;
        this.updatestatus = updatestatus;
    }

    public int getId() {
        return id;
    }

    public String getSeparator() {
        return separator;
    }

    public String getSeparatorname() {
        return separatorname;
    }

    public String getProductcode() {
        return productcode;
    }

    public String getQty() {
        return qty;
    }

    public String getSoq() {
        return soq;
    }

    public String getRollprice() {
        return rollprice;
    }

    public String getPackprice() {
        return packprice;
    }

    public String getProductname() {
        return productname;
    }

    public String getInventory() {
        return inventory;
    }

    public int getPricing() {
        return pricing;
    }

    public String getOrders() {
        return orders;
    }

    public String getCustomerno() {
        return customerno;
    }

    public String getUpdatestatus() {
        return updatestatus;
    }

    public void setAuto(int auto) {
        this.auto = auto;
    }

    public int getAuto() {
        return auto;
    }
}
