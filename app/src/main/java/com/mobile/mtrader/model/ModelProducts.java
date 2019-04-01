package com.mobile.mtrader.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelProducts {


    @SerializedName("id")
    @Expose
    public int id;

    @SerializedName("separator")
    @Expose
    public String separator;

    @SerializedName("separatorname")
    @Expose
    public String separatorname;

    @SerializedName("productcode")
    @Expose
    public String productcode;

    @SerializedName("qty")
    @Expose
    public String qty;

    @SerializedName("soq")
    @Expose
    public String soq;

    @SerializedName("rollprice")
    @Expose
    public String rollprice;

    @SerializedName("packprice")
    @Expose
    public String packprice;

    @SerializedName("productname")
    @Expose
    public String productname;


}
