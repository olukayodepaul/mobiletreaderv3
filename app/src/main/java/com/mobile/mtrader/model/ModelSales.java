package com.mobile.mtrader.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelSales {

    @SerializedName("map_token")
    @Expose
    public String map_token;

    @SerializedName("separatorname")
    @Expose
    public String separatorname;

    @SerializedName("productcode")
    @Expose
    public String productcode;

    @SerializedName("productname")
    @Expose
    public String productname;

    @SerializedName("inventory")
    @Expose
    public String inventory;

    @SerializedName("pricing")
    @Expose
    public String pricing;

    @SerializedName("order")
    @Expose
    public String order;

    @SerializedName("customerno")
    @Expose
    public String customerno;

    @SerializedName("salestime")
    @Expose
    public String salestime;

}
