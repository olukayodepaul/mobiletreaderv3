package com.mobile.mtrader.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmConverterSkuList extends RealmObject{

    private String auto = UUID.randomUUID().toString();
    private String SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    public String SimpleTimeFormat = new SimpleDateFormat("HH:mm:ss").format(new Date());
    public int id;
    public String separator;
    public String productcode;
    public String productname;
    public String qty;
    public String soq;
    public double rollprice;
    public double packprice;
    public String separatorname;

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

    public String getSimpleTimeFormat() {
        return SimpleTimeFormat;
    }

    public void setSimpleTimeFormat(String simpleTimeFormat) {
        SimpleTimeFormat = simpleTimeFormat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public String getProductcode() {
        return productcode;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getSoq() {
        return soq;
    }

    public void setSoq(String soq) {
        this.soq = soq;
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
}
