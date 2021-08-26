package com.mobdeve.group17.triptripmobileapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class TripsActivity extends AppCompatActivity {

    private RecyclerView rv_trips;
    private ArrayList<Trip> dataTrips;
    private RecyclerView.LayoutManager layoutManager;
    private TripAdapter adapter;
    private FloatingActionButton fabAdd, fabEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trips);
        initDropdown();
        initRecyclerView();
        Intent intent = getIntent();
        String curr_user = intent.getStringExtra(MainActivity.KEY_CURR_USER);
    }

    public void initRecyclerView() {
        this.rv_trips = findViewById(R.id.rv_trips);
        this.dataTrips = new DataHelper().getTrips();

        this.layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        this.rv_trips.setLayoutManager(this.layoutManager);

        this.adapter = new TripAdapter(this.dataTrips);
        this.rv_trips.setAdapter(this.adapter);

        this.fabAdd = findViewById(R.id.fab_add_trip);
        this.fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TripsActivity.this, AddTripActivity.class);
                startActivity(intent);
            }
        });

        Log.d("message", "main working");

    }
    public void initDropdown(){
        Spinner dropdown = (Spinner) findViewById(R.id.dropdown_sort_date);

//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this,
//                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.sort_options));

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(TripsActivity.this,
                R.layout.item_dropdown, getResources().getStringArray(R.array.sort_options));

        arrayAdapter.setDropDownViewResource(R.layout.item_dropdown);
        dropdown.setAdapter(arrayAdapter);
    }
}
