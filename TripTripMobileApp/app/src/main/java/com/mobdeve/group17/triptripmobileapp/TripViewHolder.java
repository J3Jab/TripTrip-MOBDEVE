package com.mobdeve.group17.triptripmobileapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class TripViewHolder extends RecyclerView.ViewHolder {

    private ImageView ivTripPic;
    private TextView tvTripTitle;
    private TextView tvStartDate;
    private TextView tvEndDate;
    private TextView tvStartLocation;
    private TextView tvEndLocation;

    public TripViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);

        this.ivTripPic = itemView.findViewById(R.id.iv_trip_pic);
        this.tvTripTitle = itemView.findViewById(R.id.tv_trip_title);
        this.tvStartDate = itemView.findViewById(R.id.tv_start_date);
        this.tvEndDate = itemView.findViewById(R.id.tv_end_date);
        this.tvStartLocation = itemView.findViewById(R.id.tv_start_location);
        this.tvEndLocation = itemView.findViewById(R.id.tv_end_location);

    }

    public void setTripPic(byte[] tripPic) {
        //if pic is not set, hide imageview
        if (tripPic == null){
            this.ivTripPic.setVisibility(View.GONE);
        }

        else{
            this.ivTripPic.setImageBitmap(ImageHelper.toImage(tripPic));
            this.ivTripPic.setVisibility(View.VISIBLE);
        }
    }

    public void setTripTitle(String tripTitle) {
        this.tvTripTitle.setText(tripTitle);
    }

    public void setStartDate(String startDate) {
        this.tvStartDate.setText(startDate);
    }

    public void setEndDate(String endDate) {
        this.tvEndDate.setText(endDate);
    }

    public void setStartLocation(String startLocation) {
        this.tvStartLocation.setText(startLocation);
    }

    public void setEndLocation(String endLocation) {
        this.tvEndLocation.setText(endLocation);
    }


}
