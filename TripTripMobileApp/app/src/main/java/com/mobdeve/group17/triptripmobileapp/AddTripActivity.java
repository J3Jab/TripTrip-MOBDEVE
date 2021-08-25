package com.mobdeve.group17.triptripmobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class AddTripActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        initDropdown();
    }

    public void initDropdown(){
        Spinner dropdown = (Spinner) findViewById(R.id.dropdown_trip_type);

//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this,
//                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.sort_options));

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddTripActivity.this,
                R.layout.item_dropdown, getResources().getStringArray(R.array.type_options));

        arrayAdapter.setDropDownViewResource(R.layout.item_dropdown);
        dropdown.setAdapter(arrayAdapter);
    }
}