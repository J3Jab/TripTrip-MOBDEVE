package com.mobdeve.group17.triptripmobileapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mobdeve.group17.triptripmobileapp.utils.PreferenceUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class TripsActivity extends AppCompatActivity {

    private RecyclerView rv_trips;
    private ArrayList<Trip> dataTrips;
    private RecyclerView.LayoutManager layoutManager;
    private TripAdapter adapter;
    private FloatingActionButton fabAdd, fabEdit;

    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trips);
        initDropdown();
        initRecyclerView();
    }

    public void initRecyclerView() {
        db = new DatabaseHelper(this);
        this.rv_trips = findViewById(R.id.rv_trips);
        //this.dataTrips = new DataHelper().getTrips();
        this.dataTrips = db.getTripsByUser(PreferenceUtils.getEmail(this));

        this.layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        this.rv_trips.setLayoutManager(this.layoutManager);

        this.adapter = new TripAdapter(this.dataTrips, this);
        this.rv_trips.setAdapter(this.adapter);

        this.fabAdd = findViewById(R.id.fab_add_trip);
        this.fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TripsActivity.this, AddTripActivity.class);
                startActivity(intent);
                finish();
                //adapter.setData(db.getTripsByUser(PreferenceUtils.getEmail(TripsActivity.this)));
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


        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] options = getResources().getStringArray(R.array.sort_options);

                switch(options[position]){
                    case "Start Date":
                        Collections.sort(dataTrips, new Comparator<Trip>() {
                            @Override
                            public int compare(Trip o1, Trip o2) {
                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                long time1 = 0, time2 = 0;
                                try {
                                    Date date1 = sdf.parse(o1.getStartDate());
                                    time1 = date1.getTime();

                                    Date date2 = sdf.parse(o2.getStartDate());
                                    time2 = date2.getTime();
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                return Long.compare(time1, time2);
                            }
                        });
                        adapter.notifyDataSetChanged();
                        break;
                    case "End Date":
                        Collections.sort(dataTrips, new Comparator<Trip>() {
                            @Override
                            public int compare(Trip o1, Trip o2) {
                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                long time1 = 0, time2 = 0;
                                try {
                                    Date date1 = sdf.parse(o1.getEndDate());
                                    time1 = date1.getTime();

                                    Date date2 = sdf.parse(o2.getEndDate());
                                    time2 = date2.getTime();
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                return Long.compare(time1, time2);
                            }
                        });
                        adapter.notifyDataSetChanged();
                        break;
                    case "Start Location":
                        Log.d("message", "start location");
                        break;
                    case "End Location":
                        Log.d("message", "end location");
                        break;

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.item_view_profile:
                Intent view = new Intent(TripsActivity.this, ViewProfileActivity.class);
                startActivity(view);
                break;
            case R.id.item_edit_profile:
                Intent edit = new Intent(TripsActivity.this, EditProfileActivity.class);
                startActivity(edit);
                break;
            case R.id.item_logout:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Would you like to logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // delete
                        PreferenceUtils.saveLogin(false, TripsActivity.this);
                        Intent intent = new Intent(TripsActivity.this, MainActivity.class);
                        finish();
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // user doesn't want to delete
                    }
                })
                .show();
    }
}
