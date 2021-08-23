package com.mobdeve.group17.triptripmobileapp;

import android.util.Log;

import java.util.ArrayList;

/* Temporary data to test recyclerview */
public class DataHelper {
    public ArrayList<Trip> getTrips() {
        ArrayList<Trip> trips = new ArrayList<Trip>();

        trips.add(new Trip(
                R.drawable.mountains,
                "Nice Trip",
                "08/23/21",
                "08/23/21",
                "Philippines",
                "Round"));

        trips.add(new Trip(
                0,
                "Nice Trip",
                "08/23/21",
                "08/23/21",
                "Philippines",
                "Single"));

        trips.add(new Trip(
                R.drawable.mountains,
                "Nice Trip",
                "08/23/21",
                "08/23/21",
                "Philippines",
                "Round"));
        return trips;
    }
}
