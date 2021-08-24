package com.mobdeve.group17.triptripmobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText name, birthday, email, password, repassword;
    Button register;
    DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = (EditText) findViewById(R.id.et_register_name);
        birthday = (EditText) findViewById(R.id.et_register_bday);
        email = (EditText) findViewById(R.id.et_register_email);
        password = (EditText) findViewById(R.id.et_register_password);
        repassword = (EditText) findViewById(R.id.et_register_confirm_password);
        db = new DatabaseHelper(this);

        register = (Button) findViewById(R.id.btn_edit_profile);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_name = name.getText().toString();
                String user_bday = birthday.getText().toString();
                String user_email = email.getText().toString();
                String user_password = password.getText().toString();
                String user_repassword = repassword.getText().toString();

                if(user_name.equals("") || user_bday.equals("") || user_email.equals("")
                        || user_password.equals("") || user_repassword.equals(""))
                    Toast.makeText(RegisterActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                else{
                    if(user_password.equals(user_repassword)){
                        Boolean checkuser = db.checkUserEmail(user_email);
                        if(!checkuser){
                            Boolean insert = db.addUser(user_email, user_name, user_bday, user_password);
                            if(insert == true){
                                Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(RegisterActivity.this, "Registered failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(RegisterActivity.this, "User already exists", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(RegisterActivity.this, "Password not matching", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }
}