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
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        user = new User();
        name = (EditText) findViewById(R.id.et_register_name);
        birthday = (EditText) findViewById(R.id.et_register_bday);
        email = (EditText) findViewById(R.id.et_register_email);
        password = (EditText) findViewById(R.id.et_register_password);
        repassword = (EditText) findViewById(R.id.et_register_confirm_password);
        db = new DatabaseHelper(this);

        register = (Button) findViewById(R.id.btn_register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setName(name.getText().toString());
                user.setBirthday(birthday.getText().toString());
                user.setEmail(email.getText().toString());
                user.setPassword(password.getText().toString());
                String user_repassword = repassword.getText().toString();

                if(user.getName().equals("") || user.getBirthday().equals("") || user.getEmail().equals("")
                        || user.getPassword().equals("") || user_repassword.equals(""))
                    Toast.makeText(RegisterActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                else{
                    if(user.getPassword().equals(user_repassword)){
                        Boolean checkuser = db.checkUserEmail(user.getEmail());
                        if(!checkuser){
                            Boolean insert = db.addUser(user);
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