package com.mobdeve.group17.triptripmobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText etBday;
    EditText etEmail;

    Button verify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initDatePickerDialog();
        etEmail = findViewById(R.id.et_forgot_email);

        verify = (Button) findViewById(R.id.btn_verify);

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewPasswordActivity.class);
                startActivity(intent);
            }
        });

    }

    public void initDatePickerDialog() {
        etBday = findViewById(R.id.et_forgot_bday);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        etBday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        ForgotPasswordActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month++; //month index starts at 0, so adding 1 will set it to display at Jan
                        String date = String.format("%02d", month) + "/" + String.format("%02d", day) + "/" + year;

                        etBday.setText(date);
                    }
                }, year, month, day);

                //disables future dates
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

                datePickerDialog.show();
            }
        });
    }
}