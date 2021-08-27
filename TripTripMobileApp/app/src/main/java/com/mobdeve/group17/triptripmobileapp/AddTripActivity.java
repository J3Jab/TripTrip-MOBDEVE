package com.mobdeve.group17.triptripmobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class AddTripActivity extends AppCompatActivity {

    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        initDropdown();
        this.save = findViewById(R.id.btn_create_trip);
        this.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddTripActivity.this, TripsActivity.class);
                startActivity(intent);
            }
        });
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