package com.mobile.mtrader.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SoqMapperModel {

    @SerializedName("status")
    @Expose
    public int status;

    @SerializedName("customersoq")
    @Expose
    public List<SoqMapper> customersoq;

}
