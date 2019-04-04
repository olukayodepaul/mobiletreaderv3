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
    public String pricing;
    public String orders;
    public String customerno;
    public String updatestatus;


    public Products(int id, String separator, String separatorname, String productcode, String qty, String soq, String rollprice, String packprice, String productname,
                    String inventory, String pricing, String orders, String customerno, String updatestatus) {

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

    public String getUpdatestatus() {
        return updatestatus;
    }

    public int getAuto() {
        return auto;
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

    public void setAuto(int auto) {
        this.auto = auto;
    }

    public String getProductname() {
        return productname;
    }

    public String getInventory() {
        return inventory;
    }

    public String getPricing() {
        return pricing;
    }

    public String getOrder() {
        return orders;
    }

    public String getCustomerno() {
        return customerno;
    }
}
