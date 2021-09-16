package com.mobdeve.group17.triptripmobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddTripActivity extends AppCompatActivity {

    EditText etTripTitle, etStartDate, etEndDate, etStartLocation, etEndLocation, etTripDescription;
    Spinner type_dropdown;

    //stores chosen date to limit the datePicker
    Calendar startCal;
    Calendar endCal;
//
//    DatePickerDialog.OnDateSetListener setListener;

    ImageView ivTripImg;
    FloatingActionButton fabAddTripImg;

    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        this.etTripTitle = findViewById(R.id.et_add_trip_title);

        this.etStartDate = findViewById(R.id.et_add_start_date);
        this.etEndDate = findViewById(R.id.et_add_end_date);

        etStartDate.setFocusable(false);
        etEndDate.setFocusable(false);

        this.etStartLocation = findViewById(R.id.et_add_start_location);
        this.etEndLocation = findViewById(R.id.et_add_end_location);

        etStartLocation.setFocusable(false);
        etEndLocation.setFocusable(false);

        this.etTripDescription = findViewById(R.id.et_add_description);

        //initialize date picker
        initDatePickerDialog();

        //initialize map intents
        initLocationMaps();

        //initialize trip type dropdown
        initDropdown();

        this.save = findViewById(R.id.btn_create_trip);
        this.save.setOnClickListener(new View.OnClickListener() {
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
                start_location.isEmpty()||end_location.isEmpty()){
                    Toast.makeText(AddTripActivity.this, "Please enter all required fields", Toast.LENGTH_SHORT).show();
                }

//                //if trip date range is invalid
//                else if(!validDates(start_date, end_date)) {
//                    Toast.makeText(AddTripActivity.this, "Invalid date range", Toast.LENGTH_SHORT).show();
//                }

                else{
                    Intent intent = new Intent(AddTripActivity.this, TripsActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }

    public void initDatePickerDialog(){

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        etStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddTripActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month++; //month index starts at 0, so adding 1 will set it to display at Jan
                        String date = String.format("%02d", month)+"/"+String.format("%02d", day)+"/"+year;

                        Log.d("startSet", String.valueOf(month));

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

                //disables past dates
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
                datePickerDialog.show();
            }
        });

        etEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddTripActivity.this, new DatePickerDialog.OnDateSetListener() {
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
//                Log.d("start", String.valueOf(year));

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

    public void initLocationMaps(){

        this.etStartLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddTripActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });

        this.etEndLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddTripActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });
    }

    public void initDropdown(){
        this.type_dropdown = (Spinner) findViewById(R.id.dropdown_add_trip_type);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddTripActivity.this,
                R.layout.item_dropdown, getResources().getStringArray(R.array.type_options));

        arrayAdapter.setDropDownViewResource(R.layout.item_dropdown);
        type_dropdown.setAdapter(arrayAdapter);
    }

//    public boolean validDates(String start, String end){
//        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
//        Date startDate = null;
//        Date endDate = null;
//        try {
//            startDate = format.parse(start);
//            endDate = format.parse(end);
//
//            //if end date is set before the start date
//            if(endDate.before(startDate))
//                return false;
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        return true;
//    }
}