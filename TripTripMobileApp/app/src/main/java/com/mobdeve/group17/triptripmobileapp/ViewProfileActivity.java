package com.mobdeve.group17.triptripmobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mobdeve.group17.triptripmobileapp.utils.PreferenceUtils;

public class ViewProfileActivity extends AppCompatActivity {

    private FloatingActionButton fab_edit;
    TextView tv_Name, tv_Bday, tv_Email;

    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        this.tv_Name = findViewById(R.id.tv_view_name);
        this.tv_Bday = findViewById(R.id.tv_view_bday);
        this.tv_Email = findViewById(R.id.tv_view_email);

        tv_Name.setText(PreferenceUtils.getName(this));
        tv_Bday.setText(PreferenceUtils.getBirthday(this));
        tv_Email.setText(PreferenceUtils.getEmail(this));

        this.fab_edit = findViewById(R.id.fab_edit_profile);
        this.fab_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewProfileActivity.this, EditProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}