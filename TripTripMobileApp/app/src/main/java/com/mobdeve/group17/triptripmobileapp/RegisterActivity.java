package com.mobdeve.group17.triptripmobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

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

        //initializes date picker for bday
        initDatePickerDialog();

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
                                finish();
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

    public void initDatePickerDialog(){
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        birthday.setFocusable(false);

        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month++; //month value starts at 0, so adding 1 will set it at Jan
                        String date = String.format("%02d", month) + "/" + String.format("%02d", day) + "/" + year;

                        birthday.setText(date);
                    }
                }, year, month, day);

                //disables future dates
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

                datePickerDialog.show();
            }
        });

    }
}