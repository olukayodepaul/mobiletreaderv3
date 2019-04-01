package com.mobile.mtrader.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelEmployees {

    @SerializedName("id")
    @Expose
    public int id;

    @SerializedName("status")
    @Expose
    public int status;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("dbroute")
    @Expose
    public String dbroute;

    @SerializedName("customer_code")
    @Expose
    public String customer_code;

    @SerializedName("lat")
    @Expose
    public String lat;

    @SerializedName("lng")
    @Expose
    public String lng;

    @SerializedName("depot_waiver")
    @Expose
    public String depot_waiver;

    @SerializedName("clokin")
    @Expose
    public String clokin;

    @SerializedName("clokout")
    @Expose
    public String clokout;

    @SerializedName("banks")
    @Expose
    public String banks;

    @SerializedName("msg")
    @Expose
    public String msg;

    @SerializedName("modules")
    @Expose
    public List<ModelModules> modules;

    @SerializedName("customers")
    @Expose
    public List<ModelCustomers> customers;

    @SerializedName("product")
    @Expose
    public List<ModelProducts> product;

}
