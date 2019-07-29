package com.mobile.mtrader.data.AllTablesStructures;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Products {

    @PrimaryKey
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

    public Products(int auto, int id, String separator, String separatorname, String productcode,
                    String qty, String soq, String rollprice,
                    String packprice, String productname) {

        this.auto =  auto;
        this.id = id;
        this.separator = separator;
        this.separatorname = separatorname;
        this.productcode = productcode;
        this.qty = qty;
        this.soq = soq;
        this.rollprice = rollprice;
        this.packprice = packprice;
        this.productname = productname;

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

    public String getProductname() {
        return productname;
    }

}
