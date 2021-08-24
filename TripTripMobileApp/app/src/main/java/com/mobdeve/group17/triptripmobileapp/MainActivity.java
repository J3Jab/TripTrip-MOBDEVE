package com.mobdeve.group17.triptripmobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv_trips;
    private ArrayList<Trip> dataTrips;
    private RecyclerView.LayoutManager layoutManager;
    private TripAdapter adapter;

    EditText email, password;
    Button register, login;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("message", "main1 working");
        setContentView(R.layout.activity_login);
        Log.d("message", "main2 working");
        //        initRecyclerView();
        Log.d("message", "main3 working");

        email = (EditText) findViewById(R.id.et_login_email);
        password = (EditText) findViewById(R.id.et_login_password);

        db = new DatabaseHelper(this);

        login = (Button) findViewById(R.id.btn_login);
        register = (Button) findViewById(R.id.btn_register_account);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_email = email.getText().toString();
                String user_password = password.getText().toString();

                if(user_email.equals("") || user_password.equals(""))
                    Toast.makeText(MainActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkuserpass = db.checkUserEmailPassword(user_email, user_password);
                    if(checkuserpass){
                        Toast.makeText(MainActivity.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), TripsActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

//        Spinner dropdown = (Spinner) findViewById(R.id.dropdown);

//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this,
//                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.sort_options));

//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this,
//                R.layout.item_dropdown, getResources().getStringArray(R.array.sort_options));
//
//                arrayAdapter.setDropDownViewResource(R.layout.item_dropdown);
//        dropdown.setAdapter(arrayAdapter);
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