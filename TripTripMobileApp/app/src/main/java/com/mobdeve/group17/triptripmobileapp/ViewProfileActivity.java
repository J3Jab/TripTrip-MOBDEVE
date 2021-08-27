package com.mobdeve.group17.triptripmobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ViewProfileActivity extends AppCompatActivity {

    private FloatingActionButton fab_edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        this.fab_edit = findViewById(R.id.fab_edit_profile);
        this.fab_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewProfileActivity.this, EditProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}