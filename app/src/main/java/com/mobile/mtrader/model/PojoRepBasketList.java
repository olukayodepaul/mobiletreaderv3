package com.mobile.mtrader.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PojoRepBasketList {

    @SerializedName("id")
    @Expose
    public int id;

    @SerializedName("prodcode")
    @Expose
    public String prodcode;

    @SerializedName("prodname")
    @Expose
    public String prodname;

    @SerializedName("qty")
    @Expose
    public String qty;

    @SerializedName("price")
    @Expose
    public String price;

    @SerializedName("soq")
    @Expose
    public String soq;
}