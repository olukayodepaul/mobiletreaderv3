package com.mobile.mtrader.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SoqMapper {

    @SerializedName("id")
    @Expose
    public int id;

    @SerializedName("customerno")
    @Expose
    public String customerno;

    @SerializedName("skucode")
    @Expose
    public String skucode;

    @SerializedName("soq")
    @Expose
    public String soq;

}
