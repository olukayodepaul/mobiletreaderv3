package com.mobile.mtrader.data.AllTablesStructures;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class LastLoation {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String urno;
    public String lat;
    public String lng;

    public LastLoation(String urno, String lat, String lng) {
        this.urno = urno;
        this.lat = lat;
        this.lng = lng;
    }

    public int getId() {
        return id;
    }

    public String getUrno() {
        return urno;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public void setId(int id) {
        this.id = id;
    }
}
