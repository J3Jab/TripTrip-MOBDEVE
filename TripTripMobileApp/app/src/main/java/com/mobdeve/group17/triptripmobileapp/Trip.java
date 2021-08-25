package com.mobdeve.group17.triptripmobileapp;

public class Trip {
    private int tripPicId;
    private String tripTitle, startDate, endDate, startLocation, endLocation, type, description;

    public Trip(int tripPicId, String title, String startDate, String endDate, String startLocation, String endLocation) {
        this.tripPicId = tripPicId;
        this.tripTitle = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
    }

    public int getTripPicId() {
        return tripPicId;
    }

    public String getTripTitle() {
        return tripTitle;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public String getTripType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

}
