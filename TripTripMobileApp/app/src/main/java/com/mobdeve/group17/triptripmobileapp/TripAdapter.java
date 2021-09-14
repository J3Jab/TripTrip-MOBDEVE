package com.mobdeve.group17.triptripmobileapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mobdeve.group17.triptripmobileapp.utils.PreferenceUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TripAdapter extends RecyclerView.Adapter<TripViewHolder> {

    private ArrayList<Trip> dataTrips;
    private FloatingActionButton fabEdit;
    private Context context;

    DatabaseHelper db;

    public TripAdapter(ArrayList<Trip> dataTrips, Context context){
        this.context = context;
        this.dataTrips = dataTrips;
    }

    @NonNull
    @NotNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_trip, parent, false);

        TripViewHolder viewHolder = new TripViewHolder(view);

        db = new DatabaseHelper(parent.getContext());

        view.findViewById(R.id.fab_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceUtils.saveTripId(dataTrips.get(viewHolder.getBindingAdapterPosition()).getId(), parent.getContext());
                Intent intent = new Intent(v.getContext(), EditTripActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        view.findViewById(R.id.fab_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(parent.getContext())
                        .setTitle("Delete")
                        .setMessage("Would you like to delete this trip?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // delete
                                db.deleteTrip(dataTrips.get(viewHolder.getBindingAdapterPosition()).getId());
                                dataTrips.remove(viewHolder.getBindingAdapterPosition());
                                notifyDataSetChanged();

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // user doesn't want to delete
                            }
                        })
                        .show();
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

    public void setData(ArrayList<Trip> data){
        this.dataTrips = data;
        notifyDataSetChanged();
    }
}
