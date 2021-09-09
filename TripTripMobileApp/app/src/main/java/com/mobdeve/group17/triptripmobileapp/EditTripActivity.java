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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class EditTripActivity extends AppCompatActivity {

    EditText etTripTitle, etStartDate, etEndDate, etStartLocation, etEndLocation, etTripDescription;
    Spinner type_dropdown;

    //stores chosen date to limit the datePicker
    Calendar startCal;
    Calendar endCal;

    Button edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_trip);
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
                        start_location.isEmpty()||end_location.isEmpty()){
                    Toast.makeText(EditTripActivity.this, "Please enter all required fields", Toast.LENGTH_SHORT).show();
                }

                else {
                    Intent intent = new Intent(EditTripActivity.this, TripsActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    public void initDropdown(){
        this.type_dropdown = (Spinner) findViewById(R.id.dropdown_edit_trip_type);

//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this,
//                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.sort_options));

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(EditTripActivity.this,
                R.layout.item_dropdown, getResources().getStringArray(R.array.type_options));

        arrayAdapter.setDropDownViewResource(R.layout.item_dropdown);
        type_dropdown.setAdapter(arrayAdapter);
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
}