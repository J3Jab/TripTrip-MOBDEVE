package com.mobdeve.group17.triptripmobileapp;

public class Trip {
    private int tripPicId;
    private String tripTitle, startDate, endDate, destination, type;

    public Trip(int tripPicId, String title, String startDate, String endDate, String destination, String type) {
        this.tripPicId = tripPicId;
        this.tripTitle = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.destination = destination;
        this.type = type;
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

    public String getDestination() {
        return destination;
    }

    public String getTripType() {
        return type;
    }
}
