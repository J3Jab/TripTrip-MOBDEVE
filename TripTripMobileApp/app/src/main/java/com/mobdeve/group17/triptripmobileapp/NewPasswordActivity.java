package com.mobdeve.group17.triptripmobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mobdeve.group17.triptripmobileapp.utils.PreferenceUtils;

public class NewPasswordActivity extends AppCompatActivity {

    EditText et_Password, et_RePassword;
    Button save;

    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        save = (Button) findViewById(R.id.btn_newPsword);
        et_Password = findViewById(R.id.et_newPsword_password);
        et_RePassword = findViewById(R.id.et_newPsword_confirm_password);

        db = new DatabaseHelper(this);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_password = et_Password.getText().toString().trim();
                String user_repassword = et_RePassword.getText().toString().trim();
                if(user_password.equals("") || user_repassword.equals(""))
                    Toast.makeText(NewPasswordActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                else{
                    if(user_password.equals(user_repassword)){
                        User user = new User();

                        user.setEmail(PreferenceUtils.getEmail(NewPasswordActivity.this));

                        PreferenceUtils.savePassword(user_password, NewPasswordActivity.this);
                        user.setPassword(PreferenceUtils.getPassword(NewPasswordActivity.this));

                        user.setName(PreferenceUtils.getName(NewPasswordActivity.this));
                        user.setBirthday(PreferenceUtils.getBirthday(NewPasswordActivity.this));

                        db.updateUser(user);

                        Toast.makeText(NewPasswordActivity.this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(NewPasswordActivity.this, "Password not matching", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}