package com.mobdeve.group17.triptripmobileapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobdeve.group17.triptripmobileapp.utils.PreferenceUtils;

public class ViewTripActivity extends AppCompatActivity {

    private Button btnEditTrip;
    private Button btnDeleteTrip;
    TextView tv_title, tv_startDate, tv_endDate, tv_startLocation, tv_endLocation, tv_descrip;
    ImageView iv_view_trip_pic;

    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_trip);

        this.btnEditTrip = findViewById(R.id.btn_view_edit_trip);
        this.btnDeleteTrip = findViewById(R.id.btn_view_delete_trip);

        this.tv_title = findViewById(R.id.tv_view_trip_title);
        this.tv_startDate = findViewById(R.id.tv_view_start_date);
        this.tv_endDate = findViewById(R.id.tv_view_end_date);
        this.tv_startLocation = findViewById(R.id.tv_view_start_location);
        this.tv_endLocation = findViewById(R.id.tv_view_end_location);
        this.tv_descrip = findViewById(R.id.tv_view_description);
        this.iv_view_trip_pic = findViewById(R.id.iv_view_trip_pic);

        db = new DatabaseHelper(this);
        Trip trip = new Trip();
        trip = db.getSpecificTrip(PreferenceUtils.getTripId(this));

        this.tv_title.setText(trip.getTripTitle());
        this.tv_startDate.setText(trip.getStartDate());
        this.tv_endDate.setText(trip.getEndDate());
        this.tv_startLocation.setText(trip.getStartLocation());
        this.tv_endLocation.setText(trip.getEndLocation());
        this.tv_descrip.setText(trip.getDescription());

        if(trip.getTripPicId() != null) {
            this.iv_view_trip_pic.setVisibility(View.VISIBLE);
            this.iv_view_trip_pic.setImageBitmap(ImageHelper.toImage(trip.getTripPicId()));
        }

        btnEditTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditTripActivity.class);
                startActivity(intent);
            }
        });

        btnDeleteTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ViewTripActivity.this)
                        .setTitle("Delete")
                        .setMessage("Would you like to delete this trip?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // delete
                                db.deleteTrip(PreferenceUtils.getTripId(ViewTripActivity.this));
                                Intent intent = new Intent(ViewTripActivity.this, TripsActivity.class);
                                startActivity(intent);
                                finish();
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

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ViewTripActivity.this, TripsActivity.class);
        startActivity(intent);
        finish();
    }
}