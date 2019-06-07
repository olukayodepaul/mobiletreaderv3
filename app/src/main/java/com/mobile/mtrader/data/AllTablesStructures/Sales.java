package com.mobile.mtrader.data.AllTablesStructures;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Sales {

    @PrimaryKey(autoGenerate = true)
    public int auto;
    public String map_token;
    public String separatorname;
    public String productcode;
    public String productname;
    public String localstatus; // 1 push to server 2. save to phone but not been push to the server
    public String inventory;
    public String pricing;
    public String orders;
    public String customerno;
    public String salestime;
    public String rollqty;
    public String packqty;
    public String rollprice;
    public String packprice;
    public String cutomersname;
    public double salescommission;

    
    public Sales(String map_token, String separatorname, String productcode, String productname, String localstatus, String inventory, String pricing, String orders, String customerno,
                 String salestime, String rollqty, String packqty, String rollprice, String packprice, String cutomersname, double salescommission) {
        this.map_token = map_token;
        this.separatorname = separatorname;
        this.productcode = productcode;
        this.productname = productname;
        this.localstatus = localstatus;
        this.inventory = inventory;
        this.pricing = pricing;
        this.orders = orders;
        this.customerno = customerno;
        this.salestime = salestime;
        this.rollqty = rollqty;
        this.packqty = packqty;
        this.rollprice = rollprice;
        this.packprice = packprice;
        this.cutomersname = cutomersname;
        this.salescommission = salescommission;
    }

    public double getSalescommission() {
        return salescommission;
    }

    public String getMap_token() {
        return map_token;
    }

    public String getSeparatorname() {
        return separatorname;
    }

    public String getProductcode() {
        return productcode;
    }

    public String getProductname() {
        return productname;
    }

    public String getLocalstatus() {
        return localstatus;
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

    public String getSalestime() {
        return salestime;
    }

    public String getRollqty() {
        return rollqty;
    }

    public String getPackqty() {
        return packqty;
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

    public String getCutomersname() {
        return cutomersname;
    }

    public int getAuto() {
        return auto;
    }
}
