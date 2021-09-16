package com.mobdeve.group17.triptripmobileapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mobdeve.group17.triptripmobileapp.utils.PreferenceUtils;

import java.util.Calendar;

public class EditTripActivity extends AppCompatActivity {

    EditText etTripTitle, etStartDate, etEndDate, etStartLocation, etEndLocation, etTripDescription;


    Spinner type_dropdown;

    //stores chosen date to limit the datePicker
    Calendar startCal;
    Calendar endCal;

    Button edit, delete;

    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_trip);

        //TODO set edit text to trip details from shared preferences
        db = new DatabaseHelper(this);
        Trip trip = new Trip();

        trip = db.getSpecificTrip(PreferenceUtils.getTripId(this));
        this.etTripTitle = findViewById(R.id.et_edit_trip_title);
        this.etStartDate = findViewById(R.id.et_edit_start_date);
        this.etEndDate = findViewById(R.id.et_edit_end_date);
        this.etStartLocation = findViewById(R.id.et_edit_start_location);
        this.etEndLocation = findViewById(R.id.et_edit_end_location);
        this.etTripDescription = findViewById(R.id.et_edit_description);

        this.etTripTitle.setText(trip.getTripTitle());
        this.etStartDate.setText(trip.getStartDate());
        this.etEndDate.setText(trip.getEndDate());
        this.etStartLocation.setText(trip.getStartLocation());
        this.etEndLocation.setText(trip.getEndLocation());
        this.etTripDescription.setText(trip.getDescription());

        initDatePickerDialog();
        initDropdown();
        this.edit = findViewById(R.id.btn_edit_trip);
        this.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String trip_title = etTripTitle.getText().toString().trim();
                String start_date = etStartDate.getText().toString().trim();
                String end_date = etEndDate.getText().toString().trim();
                String start_location = etStartLocation.getText().toString().trim();
                String end_location = etEndLocation.getText().toString().trim();
                String trip_description = etTripDescription.getText().toString().trim();

                TextView tv = (TextView) type_dropdown.getSelectedView();
                String trip_type = tv.getText().toString().trim();

                if(trip_title.isEmpty()||start_date.isEmpty()||end_date.isEmpty()||
                        start_location.isEmpty()||end_location.isEmpty()||trip_type.isEmpty()){
                    Toast.makeText(EditTripActivity.this, "Please enter all required fields", Toast.LENGTH_SHORT).show();
                }

                else {
                    Trip trip = new Trip();

                    trip.setId(PreferenceUtils.getTripId(EditTripActivity.this));
                    trip.setTripTitle(trip_title);
                    trip.setStartDate(start_date);
                    trip.setEndDate(end_date);
                    trip.setStartLocation(start_location);
                    trip.setEndLocation(end_location);
                    trip.setTripType(trip_type);

                    if(trip_description.isEmpty())
                        trip.setDescription(null);
                    else
                        trip.setDescription(trip_description);

                    db.updateTrip(trip);

                    Intent intent = new Intent(EditTripActivity.this, TripsActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        this.delete = findViewById(R.id.btn_edit_trip_delete);
        this.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(EditTripActivity.this)
                        .setTitle("Delete")
                        .setMessage("Would you like to delete this trip?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // delete
                                db.deleteTrip(PreferenceUtils.getTripId(EditTripActivity.this));
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

    public void initDropdown(){
        db = new DatabaseHelper(this);
        Trip trip = new Trip();

        trip = db.getSpecificTrip(PreferenceUtils.getTripId(this));

        this.type_dropdown = (Spinner) findViewById(R.id.dropdown_edit_trip_type);

//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this,
//                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.sort_options));

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(EditTripActivity.this,
                R.layout.item_dropdown, getResources().getStringArray(R.array.type_options));

        arrayAdapter.setDropDownViewResource(R.layout.item_dropdown);
        type_dropdown.setAdapter(arrayAdapter);

        String trip_type = String.valueOf(type_dropdown.getSelectedItem());
        if(trip.getTripType().equals(trip_type))
            type_dropdown.setSelection(0);
        else
            type_dropdown.setSelection(1);
    }

    public void initDatePickerDialog(){
        this.etStartDate = findViewById(R.id.et_edit_start_date);
        this.etEndDate = findViewById(R.id.et_edit_end_date);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        etStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        EditTripActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month++; //month index starts at 0, so adding 1 will set it to display at Jan
                        String date = String.format("%02d", month)+"/"+String.format("%02d", day)+"/"+year;

                        startCal = Calendar.getInstance();
                        startCal.set(Calendar.YEAR, year);
                        startCal.set(Calendar.MONTH, month-1); //minus 1 to get actual index of start month
                        startCal.set(Calendar.DAY_OF_MONTH, day);

                        etStartDate.setText(date);
                    }
                }, year, month, day);

                //disables dates after end date if end date has been selected
                if(endCal!=null){
                    datePickerDialog.getDatePicker().setMaxDate(endCal.getTimeInMillis());
                }

                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
                datePickerDialog.show();
            }
        });

        etEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        EditTripActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month++; //month value starts at 0, so adding 1 will set it at Jan
                        String date = String.format("%02d", month)+"/"+String.format("%02d", day)+"/"+year;

                        endCal = Calendar.getInstance();
                        endCal.set(Calendar.YEAR, year);
                        endCal.set(Calendar.MONTH, month-1); //minus 1 to get actual index of start month
                        endCal.set(Calendar.DAY_OF_MONTH, day);

                        etEndDate.setText(date);
                    }
                }, year, month, day);

                //disables dates before start date if start date has been selected
                if(startCal!=null){
                    datePickerDialog.getDatePicker().setMinDate(startCal.getTimeInMillis()-1000);
                }

                else{
                    datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
                }

                datePickerDialog.show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Cancel Editing Trip")
                .setMessage("Would you like to cancel editing this trip?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // delete
                        EditTripActivity.super.onBackPressed();
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