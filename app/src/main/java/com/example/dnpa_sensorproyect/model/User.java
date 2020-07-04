package com.example.dnpa_sensorproyect.model;

import java.util.ArrayList;

public class User {
    private String userID;
    private String email;
    private ArrayList<GPSLocation> place;

    public User(){}
    public User(String userID, String email){
        this.userID=userID;
        this.email=email;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<GPSLocation> getPlace() {
        return place;
    }

    public void setPlace(ArrayList<GPSLocation> place) {
        this.place = place;
    }
}
