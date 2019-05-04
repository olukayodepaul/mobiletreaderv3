package com.mobile.mtrader.data.AllTablesStructures;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class LocationChange {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public double lat;
    public double lng;

    public LocationChange(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}
