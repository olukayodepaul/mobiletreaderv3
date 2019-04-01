package com.mobile.mtrader.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelCustomers {

    @SerializedName("id")
    @Expose
    public int id;

    @SerializedName("notice")
    @Expose
    public String notice;

    @SerializedName("urno")
    @Expose
    public String urno;

    @SerializedName("customerno")
    @Expose
    public String customerno;

    @SerializedName("outletname")
    @Expose
    public String outletname;

    @SerializedName("lat")
    @Expose
    public String lat;

    @SerializedName("lng")
    @Expose
    public String lng;

    @SerializedName("sort")
    @Expose
    public int sort;

    @SerializedName("outlet_waiver")
    @Expose
    public String outlet_waiver;

    @SerializedName("token")
    @Expose
    public String token;

    @SerializedName("rostertime")
    @Expose
    public String rostertime;



}
