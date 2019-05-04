package com.mobile.mtrader.data.AllTablesStructures;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class UserSpinners {

    @PrimaryKey(autoGenerate = true)
    public int auto;
    public int id;
    public String name;
    public String sep;

    public UserSpinners(int id, String name, String sep) {
        this.id = id;
        this.name = name;
        this.sep = sep;
    }

    public int getAuto() {
        return auto;
    }

    public void setAuto(int auto) {
        this.auto = auto;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSep() {
        return sep;
    }
}
