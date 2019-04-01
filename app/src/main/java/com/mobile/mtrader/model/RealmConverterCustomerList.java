package com.mobile.mtrader.model;



import io.realm.RealmObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class RealmConverterCustomerList extends RealmObject {

    private String auto = UUID.randomUUID().toString();
    private String SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    public int id;
    public String notice;
    public String urno;
    public String customerno;
    public String outletname;
    public String lat;
    public String lng;
    public int sort;
    public String outlet_waiver;
    public String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getUrno() {
        return urno;
    }

    public void setUrno(String urno) {
        this.urno = urno;
    }

    public String getCustomerno() {
        return customerno;
    }

    public void setCustomerno(String customerno) {
        this.customerno = customerno;
    }

    public String getOutletname() {
        return outletname;
    }

    public void setOutletname(String outletname) {
        this.outletname = outletname;
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

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getOutlet_waiver() {
        return outlet_waiver;
    }

    public void setOutlet_waiver(String outlet_waiver) {
        this.outlet_waiver = outlet_waiver;
    }
}
