package com.mobdeve.group17.triptripmobileapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.mobdeve.group17.triptripmobileapp.utils.PreferenceUtils;

import java.util.Calendar;

public class EditProfileActivity extends AppCompatActivity {

    EditText etname, etpassword, etrepassword;
    Button edit;

    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        db = new DatabaseHelper(this);

        this.etname = findViewById(R.id.et_edit_name);
        this.etpassword = findViewById(R.id.et_edit_password);
        this.etrepassword = findViewById(R.id.et_edit_confirm_password);

        this.etname.setText(PreferenceUtils.getName(this));

        this.edit = findViewById(R.id.btn_edit_profile);
        this.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etname.getText().toString().trim();
                String password = etpassword.getText().toString().trim();
                String repassword = etrepassword.getText().toString().trim();

                if(name.equals("") || password.equals("") || repassword.equals("")){
                    Toast.makeText(EditProfileActivity.this, "Please enter all required fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(password.equals(repassword)){
                        if(password.equals(PreferenceUtils.getPassword(EditProfileActivity.this)))
                            Toast.makeText(EditProfileActivity.this, "New password should not be the same as old password", Toast.LENGTH_LONG).show();
                        else{
                            User user = new User();

                            PreferenceUtils.saveName(name, EditProfileActivity.this);
                            PreferenceUtils.savePassword(password, EditProfileActivity.this);

                            user.setName(PreferenceUtils.getName(EditProfileActivity.this));
                            user.setBirthday(PreferenceUtils.getBirthday(EditProfileActivity.this));
                            user.setEmail(PreferenceUtils.getEmail(EditProfileActivity.this));
                            user.setPassword(PreferenceUtils.getPassword(EditProfileActivity.this));

                            db.updateUser(user);

                            Toast.makeText(EditProfileActivity.this, "Changes made successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(EditProfileActivity.this, ViewProfileActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                    else{
                        Toast.makeText(EditProfileActivity.this, "Password not matching", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Cancel Editing Profile")
                .setMessage("Would you like to cancel editing your account?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }
}
