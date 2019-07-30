package com.mobile.mtrader.data.AllTablesStructures;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class AllRepCustomers {

    @PrimaryKey
    public int auto;
    public int id;
    public String urno;
    public String customerno;
    public String outletclassid;
    public String outletlanguageid;
    public String outlettypeid;
    public String outletname;
    public String outletaddress;
    public String contactname;
    public String contactphone;
    public String latitude;
    public String longitude;
    public String outlet_pic;

    public AllRepCustomers(int auto, int id, String urno, String customerno, String outletclassid, String outletlanguageid,
                           String outlettypeid, String outletname, String outletaddress, String contactname,
                           String contactphone, String latitude, String longitude,String outlet_pic) {
        this.auto = auto;
        this.id = id;
        this.urno = urno;
        this.customerno = customerno;
        this.outletclassid = outletclassid;
        this.outletlanguageid = outletlanguageid;
        this.outlettypeid = outlettypeid;
        this.outletname = outletname;
        this.outletaddress = outletaddress;
        this.contactname = contactname;
        this.contactphone = contactphone;
        this.latitude = latitude;
        this.longitude = longitude;
        this.outlet_pic = outlet_pic;
    }

    public int getAuto() {
        return auto;
    }

    public int getId() {
        return id;
    }

    public String getUrno() {
        return urno;
    }

    public String getCustomerno() {
        return customerno;
    }

    public String getOutletclassid() {
        return outletclassid;
    }

    public String getOutletlanguageid() {
        return outletlanguageid;
    }

    public String getOutlettypeid() {
        return outlettypeid;
    }

    public String getOutletname() {
        return outletname;
    }

    public String getOutletaddress() {
        return outletaddress;
    }

    public String getContactname() {
        return contactname;
    }

    public String getContactphone() {
        return contactphone;
    }

    public String getOutlet_pic() {
        return outlet_pic;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

}
