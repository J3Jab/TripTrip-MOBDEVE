package com.mobdeve.group17.triptripmobileapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.mobdeve.group17.triptripmobileapp.utils.PreferenceUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText email, password;
    Button register, login, forgotPass;
    DatabaseHelper db;

    private static final String TAG = "MainActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.et_login_email);
        password = (EditText) findViewById(R.id.et_login_password);

        db = new DatabaseHelper(this);

        login = (Button) findViewById(R.id.btn_login);
        register = (Button) findViewById(R.id.btn_register_account);
        forgotPass = (Button) findViewById(R.id.btn_forgot_password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_email = email.getText().toString().trim();
                String user_password = password.getText().toString().trim();

                if(user_email.equals("") || user_password.equals(""))
                    Toast.makeText(MainActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkuserpass = db.checkUserEmailPassword(user_email, user_password);
                    if(checkuserpass){
                        User user = db.getUser(user_email);
                        //SharedPreferences
                        PreferenceUtils.saveEmail(user.getEmail(), MainActivity.this);
                        PreferenceUtils.savePassword(user.getPassword(), MainActivity.this);
                        PreferenceUtils.saveName(user.getName(), MainActivity.this);
                        PreferenceUtils.saveBirthday(user.getBirthday(), MainActivity.this);

                        Toast.makeText(MainActivity.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), TripsActivity.class);
                        startActivity(intent);

                        //prevents user from returning to login activity
                        //finish();
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

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        //disables login button if user does not have the correct Google Services version
        if(!isServicesOk()){
            login.setEnabled(false);
        }
        else{
            login.setEnabled(true);
        }
    }

    /**
     * checks if user's device has the correct Google Services version
     * @return true if Google Services version is ok, false if not
     */
    public boolean isServicesOk(){
        //log message for checking and debugging
        Log.d(TAG, "isServicesOk: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        // if ok and user can make map requests
        if(available== ConnectionResult.SUCCESS){
            Log.d(TAG, "isServicesOk: Google Play Services working");

            return true;
        }

        //if an error occurs but is resolvable
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Log.d(TAG, "isServicesOk: error occurred but fixable");
            Toast.makeText(this, "An error occurred with your current Google Services version.", Toast.LENGTH_SHORT).show();

            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }

        //if error occurs and is not resolvable
        else{
            Toast.makeText(this, "Map requests can't be made for this app. Please update your Google Services version.", Toast.LENGTH_SHORT).show();
        }

        return false;
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Would you like to exit the app?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.super.onBackPressed();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }
}