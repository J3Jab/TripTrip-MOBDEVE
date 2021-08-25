package com.mobdeve.group17.triptripmobileapp;

public class Trip {
    private int tripPicId, id;
    private String tripTitle, startDate, endDate, startLocation, endLocation, type, userEmail;

    public Trip(int tripPicId, String title, String startDate, String endDate, String endLocation, String type) {
        this.tripPicId = tripPicId;
        this.tripTitle = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.type = type;
        this.userEmail = userEmail;
    }

    public Trip() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTripPicId() {
        return tripPicId;
    }

    public void setTripPicId(int tripPicId) {
        this.tripPicId = tripPicId;
    }

    public String getTripTitle() {
        return tripTitle;
    }

    public void setTripTitle(String tripTitle) {
        this.tripTitle = tripTitle;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartLocation() { return startLocation; }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
