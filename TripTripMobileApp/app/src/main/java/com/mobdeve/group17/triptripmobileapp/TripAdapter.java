package com.mobdeve.group17.triptripmobileapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TripAdapter extends RecyclerView.Adapter<TripViewHolder> {

    private ArrayList<Trip> dataTrips;

    public TripAdapter(ArrayList<Trip> dataTrips){
        this.dataTrips = dataTrips;
    }

    @NonNull
    @NotNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_trip, parent, false);

        TripViewHolder viewHolder = new TripViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TripViewHolder holder, int position) {
        holder.setTripPic(dataTrips.get(position).getTripPicId());
        holder.setTripTitle(dataTrips.get(position).getTripTitle());
        holder.setStartDate(dataTrips.get(position).getStartDate());
        holder.setEndDate(dataTrips.get(position).getEndDate());
        holder.setDestination(dataTrips.get(position).getDestination());

    }

    @Override
    public int getItemCount() {
        return this.dataTrips.size();
    }
}
