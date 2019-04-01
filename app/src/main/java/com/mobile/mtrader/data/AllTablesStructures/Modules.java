package com.mobile.mtrader.data.AllTablesStructures;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Modules {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public int module_id;
    public String nav;
    public String name;
    public String imageurl;

    public Modules(int module_id, String nav, String name, String imageurl) {
        this.module_id = module_id;
        this.nav = nav;
        this.name = name;
        this.imageurl = imageurl;
    }

    public int getId() {
        return id;
    }

    public int getModule_id() {
        return module_id;
    }

    public String getNav() {
        return nav;
    }

    public String getName() {
        return name;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setId(int id) {
        this.id = id;
    }
}


