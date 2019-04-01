package com.mobile.mtrader.model;

import io.realm.RealmObject;

public class RealmCustomers  extends RealmObject{

    public String customername;
    public String customerid;

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }
}
