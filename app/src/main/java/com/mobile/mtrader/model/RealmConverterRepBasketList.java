package com.mobile.mtrader.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import io.realm.RealmObject;

public class RealmConverterRepBasketList extends RealmObject{

    public int id;
    public String prodcode;
    public String prodname;
    public double qty;
    public String price;
    public String soq;
    private String auto = UUID.randomUUID().toString();
    private String SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProdcode() {
        return prodcode;
    }

    public void setProdcode(String prodcode) {
        this.prodcode = prodcode;
    }

    public String getProdname() {
        return prodname;
    }

    public void setProdname(String prodname) {
        this.prodname = prodname;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSoq() {
        return soq;
    }

    public void setSoq(String soq) {
        this.soq = soq;
    }

    public String getAuto() {
        return auto;
    }

    public void setAuto(String auto) {
        this.auto = auto;
    }

    public String getSimpleDateFormat() {
        return SimpleDateFormat;
    }

    public void setSimpleDateFormat(String simpleDateFormat) {
        SimpleDateFormat = simpleDateFormat;
    }

}
