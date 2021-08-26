package com.mobdeve.group17.triptripmobileapp;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TripAdapter extends RecyclerView.Adapter<TripViewHolder> {

    private ArrayList<Trip> dataTrips;
    private FloatingActionButton fabEdit;

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

        view.findViewById(R.id.fab_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditTripActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TripViewHolder holder, int position) {
        holder.setTripPic(dataTrips.get(position).getTripPicId());
        holder.setTripTitle(dataTrips.get(position).getTripTitle());
        holder.setStartDate(dataTrips.get(position).getStartDate());
        holder.setEndDate(dataTrips.get(position).getEndDate());
        holder.setStartLocation(dataTrips.get(position).getStartLocation());
        holder.setEndLocation(dataTrips.get(position).getEndLocation());
    }

    @Override
    public int getItemCount() {
        return this.dataTrips.size();
    }
}
