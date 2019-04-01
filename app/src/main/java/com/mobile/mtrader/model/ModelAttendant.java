package com.mobile.mtrader.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ModelAttendant {

    @SerializedName("status")
    @Expose
    public int status;

    @SerializedName("msg")
    @Expose
    public String msg;

    @SerializedName("rtype")
    @Expose
    public int rtype;
}
