package com.mobdeve.group17.triptripmobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ViewTripActivity extends AppCompatActivity {

    private Button btnEditTrip;
    private Button btnDeleteTrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_trip);

        this.btnEditTrip = findViewById(R.id.btn_view_edit_trip);
        this.btnDeleteTrip = findViewById(R.id.btn_view_delete_trip);

        btnEditTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditTripActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnDeleteTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TripsActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}