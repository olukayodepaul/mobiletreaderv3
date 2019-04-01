package com.mobile.mtrader.model;

import io.realm.RealmList;
import io.realm.RealmObject;

public class RealmConverterLogin extends RealmObject {

    public int id;
    public String name;
    public String ecode;
    public String custno;
    public String dbroute;
    public String sellingunit;
    public int auth;
    public double lat;
    public double lng;
    public String depot_waiver;
    public String clockintime;
    public String clockouttime;
    public String vehiclename;
    public String vehicleid;
    public RealmList<RealmConverterUserModules> modules;
    public RealmList<RealmConverterCustomerList> customers;
    public RealmList<RealmConverterRepBasketList> basket;
    public RealmList<RealmConverterSkuList> product;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEcode() {
        return ecode;
    }

    public void setEcode(String ecode) {
        this.ecode = ecode;
    }

    public String getCustno() {
        return custno;
    }

    public void setCustno(String custno) {
        this.custno = custno;
    }

    public String getDbroute() {
        return dbroute;
    }

    public void setDbroute(String dbroute) {
        this.dbroute = dbroute;
    }

    public String getSellingunit() {
        return sellingunit;
    }

    public void setSellingunit(String sellingunit) {
        this.sellingunit = sellingunit;
    }

    public int getAuth() {
        return auth;
    }

    public void setAuth(int auth) {
        this.auth = auth;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getDepot_waiver() {
        return depot_waiver;
    }

    public void setDepot_waiver(String depot_waiver) {
        this.depot_waiver = depot_waiver;
    }

    public String getClockintime() {
        return clockintime;
    }

    public void setClockintime(String clockintime) {
        this.clockintime = clockintime;
    }

    public String getClockouttime() {
        return clockouttime;
    }

    public void setClockouttime(String clockouttime) {
        this.clockouttime = clockouttime;
    }

    public String getVehiclename() {
        return vehiclename;
    }

    public void setVehiclename(String vehiclename) {
        this.vehiclename = vehiclename;
    }

    public String getVehicleid() {
        return vehicleid;
    }

    public void setVehicleid(String vehicleid) {
        this.vehicleid = vehicleid;
    }

    public RealmList<RealmConverterUserModules> getModules() {
        return modules;
    }

    public void setModules(RealmList<RealmConverterUserModules> modules) {
        this.modules = modules;
    }

    public RealmList<RealmConverterCustomerList> getCustomers() {
        return customers;
    }

    public void setCustomers(RealmList<RealmConverterCustomerList> customers) {
        this.customers = customers;
    }

    public RealmList<RealmConverterRepBasketList> getBasket() {
        return basket;
    }

    public void setBasket(RealmList<RealmConverterRepBasketList> basket) {
        this.basket = basket;
    }

    public RealmList<RealmConverterSkuList> getProduct() {
        return product;
    }

    public void setProduct(RealmList<RealmConverterSkuList> product) {
        this.product = product;
    }
}
