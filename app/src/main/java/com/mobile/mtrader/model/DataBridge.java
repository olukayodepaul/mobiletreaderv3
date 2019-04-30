package com.mobile.mtrader.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class DataBridge {

    //Response
    @SerializedName("mstatus")
    @Expose
    public int mstatus;

    @SerializedName("urno")
    @Expose
    public String urno;

    @SerializedName("mdate")
    @Expose
    public String  mdate;

    @SerializedName("push")
    @Expose
    public List<ModelSales> push;

    //Request

    @SerializedName("entrytime")
    @Expose
    public String entrytime;

    @SerializedName("product_name")
    @Expose
    public String product_name;

    @SerializedName("product_code")
    @Expose
    public String product_code;

    @SerializedName("customer_id")
    @Expose
    public String customer_id;

    @SerializedName("userid")
    @Expose
    public int userid;

    @SerializedName("separator")
    @Expose
    public String separator;

    @SerializedName("order")
    @Expose
    public double order;

    @SerializedName("invs")
    @Expose
    public double invs;

    @SerializedName("pricing")
    @Expose
    public int pricing;

    @SerializedName("status")
    @Expose
    public int status;

    @SerializedName("rollprice")
    @Expose
    public double rollprice;

    @SerializedName("packprice")
    @Expose
    public double packprice;

    @SerializedName("separatorname")
    @Expose
    public String separatorname;

    @SerializedName("lat")
    @Expose
    public String lat;

    @SerializedName("lng")
    @Expose
    public String lng;

    @SerializedName("location_entry_time")
    @Expose
    public String location_entry_time;

    @SerializedName("token")
    @Expose
    public String token;

    @SerializedName("trans")
    @Expose
    public String trans;

    public DataBridge(String entrytime, String product_name, String product_code, String customer_id,
                      int userid, String separator, double order, double invs, int pricing, int status,
                      double rollprice, double packprice, String separatorname, String lat, String lng,
                      String location_entry_time, String token, String trans) {
        this.entrytime = entrytime;
        this.product_name = product_name;
        this.product_code = product_code;
        this.customer_id = customer_id;
        this.userid = userid;
        this.separator = separator;
        this.order = order;
        this.invs = invs;
        this.pricing = pricing;
        this.status = status;
        this.rollprice = rollprice;
        this.packprice = packprice;
        this.separatorname = separatorname;
        this.lat = lat;
        this.lng = lng;
        this.location_entry_time = location_entry_time;
        this.token = token;
        this.trans = trans;
    }

    public String getEntrytime() {
        return entrytime;
    }

    public void setEntrytime(String entrytime) {
        this.entrytime = entrytime;
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

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public double getOrder() {
        return order;
    }

    public void setOrder(double order) {
        this.order = order;
    }

    public double getInvs() {
        return invs;
    }

    public void setInvs(double invs) {
        this.invs = invs;
    }

    public int getPricing() {
        return pricing;
    }

    public void setPricing(int pricing) {
        this.pricing = pricing;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public String getSeparatorname() {
        return separatorname;
    }

    public void setSeparatorname(String separatorname) {
        this.separatorname = separatorname;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLocation_entry_time() {
        return location_entry_time;
    }

    public void setLocation_entry_time(String location_entry_time) {
        this.location_entry_time = location_entry_time;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTrans() {
        return trans;
    }

    public void setTrans(String trans) {
        this.trans = trans;
    }
}
