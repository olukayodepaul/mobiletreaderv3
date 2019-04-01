package com.mobile.mtrader.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import io.realm.RealmObject;

public class RealmConverterUserModules extends RealmObject {

    private String auto = UUID.randomUUID().toString();
    public int id;
    public String nav;
    public String name;
    public String imageurl;
    private String SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    public String getAuto() {
        return auto;
    }

    public void setAuto(String auto) {
        this.auto = auto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNav() {
        return nav;
    }

    public void setNav(String nav) {
        this.nav = nav;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getSimpleDateFormat() {
        return SimpleDateFormat;
    }

    public void setSimpleDateFormat(String simpleDateFormat) {
        SimpleDateFormat = simpleDateFormat;
    }
}