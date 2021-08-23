package com.mobdeve.group17.triptripmobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv_trips;
    private ArrayList<Trip> dataTrips;
    private RecyclerView.LayoutManager layoutManager;
    private TripAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("message", "main1 working");
        setContentView(R.layout.activity_main);
        Log.d("message", "main2 working");
        initRecyclerView();
        Log.d("message", "main3 working");

        Spinner dropdown = (Spinner) findViewById(R.id.dropdown);

//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this,
//                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.sort_options));

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this,
                R.layout.item_dropdown, getResources().getStringArray(R.array.sort_options));

                arrayAdapter.setDropDownViewResource(R.layout.item_dropdown);
        dropdown.setAdapter(arrayAdapter);
    }

    public void initRecyclerView(){

        this.rv_trips = findViewById(R.id.rv_trips);
        this.dataTrips = new DataHelper().getTrips();

        this.layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        this.rv_trips.setLayoutManager(this.layoutManager);

        this.adapter = new TripAdapter(this.dataTrips);
        this.rv_trips.setAdapter(this.adapter);

        Log.d("message", "main working");

    }
}