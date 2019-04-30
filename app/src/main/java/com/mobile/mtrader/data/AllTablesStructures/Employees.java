package com.mobile.mtrader.data.AllTablesStructures;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Employees {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public int user_id;
    public String name;
    public String dbroute;
    public String customer_code;
    public String depotlat;
    public String  depotlng;
    public String depot_waiver;
    public String clokin;
    public String clokout;
    public String mdate;
    public String distance;

    public Employees(int user_id, String name, String dbroute, String customer_code, String depotlat,
                     String depotlng, String depot_waiver, String clokin, String clokout, String mdate,
                     String distance) {
        this.user_id = user_id;
        this.name = name;
        this.dbroute = dbroute;
        this.customer_code = customer_code;
        this.depotlat = depotlat;
        this.depotlng = depotlng;
        this.depot_waiver = depot_waiver;
        this.clokin = clokin;
        this.clokout = clokout;
        this.mdate = mdate;
        this.distance = distance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDbroute() {
        return dbroute;
    }

    public String getCustomer_code() {
        return customer_code;
    }

    public String getDepotlat() {
        return depotlat;
    }

    public String getDepotlng() {
        return depotlng;
    }

    public String getDepot_waiver() {
        return depot_waiver;
    }

    public String getClokin() {
        return clokin;
    }

    public String getClokout() {
        return clokout;
    }

    public String getMdate() {
        return mdate;
    }

    public String getDistance() {
        return distance;
    }
}
