package com.mobile.mtrader.data.AllTablesStructures;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Customers {

    @PrimaryKey(autoGenerate = true)
    public int auto;
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
    public String rostertime;

    public Customers(int id, String notice, String urno, String customerno, String outletname, String lat, String lng, int sort,
                     String outlet_waiver, String token, String rostertime) {
        this.id = id;
        this.notice = notice;
        this.urno = urno;
        this.customerno = customerno;
        this.outletname = outletname;
        this.lat = lat;
        this.lng = lng;
        this.sort = sort;
        this.outlet_waiver = outlet_waiver;
        this.token = token;
        this.rostertime = rostertime;
    }

    public int getAuto() {
        return auto;
    }

    public int getId() {
        return id;
    }

    public String getNotice() {
        return notice;
    }

    public String getUrno() {
        return urno;
    }

    public String getCustomerno() {
        return customerno;
    }

    public String getOutletname() {
        return outletname;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public int getSort() {
        return sort;
    }

    public String getOutlet_waiver() {
        return outlet_waiver;
    }

    public String getToken() {
        return token;
    }

    public void setAuto(int auto) {
        this.auto = auto;
    }


}
