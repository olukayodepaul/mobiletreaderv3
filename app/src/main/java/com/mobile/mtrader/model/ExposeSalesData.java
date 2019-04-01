package com.mobile.mtrader.model;

public class ExposeSalesData {

    public String product_name;
    public String product_code;
    public String separator;
    public String soq;
    public String qty;
    public double rollprice;
    public double packprice;
    public String order;
    public String customer_id;
    public String  customer_name;
    public String inventory;
    public String pricing;
    public int status;
    public int s_status;
    public String seperatorname;

    public ExposeSalesData(String product_name, String product_code, String separator, String soq, String qty,
                           double rollprice, double packprice, String order, String customer_id, String customer_name,
                           String inventory, String pricing, int status, int s_status, String seperatorname) {
        this.product_name = product_name;
        this.product_code = product_code;
        this.separator = separator;
        this.soq = soq;
        this.qty = qty;
        this.rollprice = rollprice;
        this.packprice = packprice;
        this.order = order;
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.inventory = inventory;
        this.pricing = pricing;
        this.status = status;
        this.s_status = s_status;
        this.seperatorname = seperatorname;
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

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
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

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    public String getPricing() {
        return pricing;
    }

    public void setPricing(String pricing) {
        this.pricing = pricing;
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

    public String getSeperatorname() {
        return seperatorname;
    }

    public void setSeperatorname(String seperatorname) {
        this.seperatorname = seperatorname;
    }
}
