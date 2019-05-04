package com.mobile.mtrader.firebasemodel;

public class UserLocation {

    public String email;
    public String lat;
    public String lng;

    public UserLocation(String email, String lat, String lng) {
        this.email = email;
        this.lat = lat;
        this.lng = lng;
    }

    public UserLocation(){
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
