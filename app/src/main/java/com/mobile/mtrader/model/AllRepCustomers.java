package com.mobile.mtrader.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllRepCustomers {

    @SerializedName("id")
    @Expose
    public int id;

    @SerializedName("urno")
    @Expose
    public String urno;

    @SerializedName("customerno")
    @Expose
    public String customerno;

    @SerializedName("outletclassid")
    @Expose
    public String outletclassid;

    @SerializedName("outletlanguageid")
    @Expose
    public String outletlanguageid;

    @SerializedName("outlettypeid")
    @Expose
    public String outlettypeid;

    @SerializedName("outletname")
    @Expose
    public String outletname;

    @SerializedName("outletaddress")
    @Expose
    public String outletaddress;

    @SerializedName("contactname")
    @Expose
    public String contactname;

    @SerializedName("contactphone")
    @Expose
    public String contactphone;

    @SerializedName("latitude")
    @Expose
    public String latitude;

    @SerializedName("longitude")
    @Expose
    public String longitude;

    @SerializedName("outlet_pic")
    @Expose
    public String outlet_pic;

}
