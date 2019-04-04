package com.mobile.mtrader.data.AllTablesStructures;

import android.arch.lifecycle.ViewModel;
import android.arch.persistence.room.Entity;

@Entity
public class Sales extends ViewModel {

    public int auto;
    public int userid;
    public String separatorname;
    public String productcode;
    public String soq;
    public String rollprice;
    public String packprice;
    public String productname;
    public String status;
    public String localstatus;
    public String inventory;
    public String pricing;
    public String order;
    public String customerno;
    public String salestime;


    public Sales(int userid, String separatorname, String productcode, String soq, String rollprice,
                 String packprice, String productname, String status, String localstatus, String inventory,
                 String pricing, String order, String customerno, String salestime) {

        this.userid = userid;
        this.separatorname = separatorname;
        this.productcode = productcode;
        this.soq = soq;
        this.rollprice = rollprice;
        this.packprice = packprice;
        this.productname = productname;
        this.status = status;
        this.localstatus = localstatus;
        this.inventory = inventory;
        this.pricing = pricing;
        this.order = order;
        this.customerno = customerno;
        this.salestime = salestime;
    }

    public int getAuto() {
        return auto;
    }

    public int getUserid() {
        return userid;
    }

    public String getSeparatorname() {
        return separatorname;
    }

    public String getProductcode() {
        return productcode;
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

    public String getStatus() {
        return status;
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
        return order;
    }

    public String getCustomerno() {
        return customerno;
    }

    public void setAuto(int auto) {
        this.auto = auto;
    }

    public String getSalestime() {
        return salestime;
    }
}
