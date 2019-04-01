package com.mobile.mtrader.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmConverterAddProducts extends RealmObject {

    public String entrydate = new SimpleDateFormat("HH:mm:ss").format(new Date());;
    public String product_name;
    public String product_code;
    public String separator;
    public String soq;
    public String qty;
    public double rollprice;
    public double packprice;
    public String customer_id;
    public String  customer_name;
    public double inventory;
    public int pricing;
    public double order;
    public int status;
    public int s_status;
    public String separatorname;

    public String getEntrydate() {
        return entrydate;
    }

    public void setEntrydate(String entrydate) {
        this.entrydate = entrydate;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public String getSoq() {
        return soq;
    }

    public void setSoq(String soq) {
        this.soq = soq;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public double getRollprice() {
        return rollprice;
    }

    public void setRollprice(double rollprice) {
        this.rollprice = rollprice;
    }

    public double getPackprice() {
        return packprice;
    }

    public void setPackprice(double packprice) {
        this.packprice = packprice;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public double getInventory() {
        return inventory;
    }

    public void setInventory(double inventory) {
        this.inventory = inventory;
    }

    public int getPricing() {
        return pricing;
    }

    public void setPricing(int pricing) {
        this.pricing = pricing;
    }

    public double getOrder() {
        return order;
    }

    public void setOrder(double order) {
        this.order = order;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getS_status() {
        return s_status;
    }

    public void setS_status(int s_status) {
        this.s_status = s_status;
    }

    public String getSeparatorname() {
        return separatorname;
    }

    public void setSeparatorname(String separatorname) {
        this.separatorname = separatorname;
    }
}
