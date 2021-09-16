package com.mobdeve.group17.triptripmobileapp;


import androidx.appcompat.app.AlertDialog;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mobdeve.group17.triptripmobileapp.utils.PreferenceUtils;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

public class AddTripActivity extends AppCompatActivity {

    EditText etTripTitle, etStartDate, etEndDate, etStartLocation, etEndLocation, etTripDescription;
    Spinner type_dropdown;

    //stores chosen date to limit the datePicker
    Calendar startCal;
    Calendar endCal;

    ImageView ivTripImg;
    FloatingActionButton fabAddTripImg;

    ImageButton ibLocSearch;
    Button save;

    private ActivityResultLauncher<Intent> choosePicLauncher;
    private static final String READ_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    private static final String WRITE_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private static final int STORAGE_PERMISSION_REQUEST_CODE = 123;

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        this.etTripTitle = findViewById(R.id.et_add_trip_title);

        this.etStartDate = findViewById(R.id.et_add_start_date);
        this.etEndDate = findViewById(R.id.et_add_end_date);

        etStartDate.setFocusable(false);
        etEndDate.setFocusable(false);

        this.etStartLocation = findViewById(R.id.et_add_start_location);
        this.etEndLocation = findViewById(R.id.et_add_end_location);
        this.etTripDescription = findViewById(R.id.et_add_description);

        this.ivTripImg = findViewById(R.id.iv_add_trip_image);
        this.fabAddTripImg = findViewById(R.id.fab_add_trip_image);

        //initialize date picker
        initDatePickerDialog();

        //initialize trip type dropdown
        initDropdown();

        //initialize db
        db = new DatabaseHelper(this);

        this.ibLocSearch = findViewById(R.id.ib_add_search_location);

        //launches map for location reference
        this.ibLocSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddTripActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });

        db = new DatabaseHelper(this);

        this.save = findViewById(R.id.btn_create_trip);
        this.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String trip_title = etTripTitle.getText().toString().trim();
                String start_date = etStartDate.getText().toString().trim();
                String end_date = etEndDate.getText().toString().trim();
                String start_location = etStartLocation.getText().toString().trim();
                String end_location = etEndLocation.getText().toString().trim();
                String trip_description = etTripDescription.getText().toString().trim();

                TextView tv = (TextView) type_dropdown.getSelectedView();
                String trip_type = tv.getText().toString().trim();

                //if iv has image
                //get image bitmap
                byte[] trip_pic;
                if (ImageHelper.hasImage(ivTripImg)) {
                    Bitmap bm = ((BitmapDrawable) ivTripImg.getDrawable()).getBitmap();
                    trip_pic = ImageHelper.toByteArray(bm);
                }
                else {
                    trip_pic = null;
                }

                if(trip_title.isEmpty()||start_date.isEmpty()||end_date.isEmpty()||
                        start_location.isEmpty()||end_location.isEmpty()){
                    Toast.makeText(AddTripActivity.this, "Please enter all required fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    Trip trip = new Trip();

                    trip.setTripTitle(trip_title);
                    trip.setStartDate(start_date);
                    trip.setEndDate(end_date);
                    trip.setStartLocation(start_location);
                    trip.setEndLocation(end_location);
                    trip.setTripType(trip_type);
                    trip.setTripPicId(trip_pic);

                    if(trip_description.isEmpty())
                        trip.setDescription("");
                    else
                        trip.setDescription(trip_description);

                    db.addTrip(trip, PreferenceUtils.getEmail(AddTripActivity.this));
                    Intent intent = new Intent(AddTripActivity.this, TripsActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        //activity to add an image taken from files to an image view
        choosePicLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    Uri chosenPic = data.getData();
                    String[] path = {MediaStore.Images.Media.DATA};
                    if (chosenPic != null) {
                        Cursor cursor = getContentResolver().query(chosenPic, path, null, null, null);
                        if (cursor != null) {
                            cursor.moveToFirst();
                            int columnIndex = cursor.getColumnIndex(path[0]);
                            String picPath = cursor.getString(columnIndex);
                            ivTripImg.setImageBitmap(BitmapFactory.decodeFile(picPath));
                            cursor.close();
                        }
                    }
                }
            }
        });

        this.fabAddTripImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //asks permission to access media file before launching gallery
                getStoragePermission();
            }
        });
    }

    public void initDatePickerDialog(){

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        etStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddTripActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month++; //month index starts at 0, so adding 1 will set it to display at Jan
                        String date = String.format("%02d", month)+"/"+String.format("%02d", day)+"/"+year;

                        Log.d("startSet", String.valueOf(month));

                        startCal = Calendar.getInstance();
                        startCal.set(Calendar.YEAR, year);
                        startCal.set(Calendar.MONTH, month-1); //minus 1 to get actual index of start month
                        startCal.set(Calendar.DAY_OF_MONTH, day);


                        etStartDate.setText(date);
                    }
                }, year, month, day);

                //disables dates after end date if end date has been selected
                if(endCal!=null){
                    datePickerDialog.getDatePicker().setMaxDate(endCal.getTimeInMillis());
                }

                //disables past dates
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
                datePickerDialog.show();
            }
        });

        etEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddTripActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month++; //month value starts at 0, so adding 1 will set it at Jan
                        String date = String.format("%02d", month)+"/"+String.format("%02d", day)+"/"+year;

                        endCal = Calendar.getInstance();
                        endCal.set(Calendar.YEAR, year);
                        endCal.set(Calendar.MONTH, month-1); //minus 1 to get actual index of start month
                        endCal.set(Calendar.DAY_OF_MONTH, day);

                        etEndDate.setText(date);
                    }
                }, year, month, day);

                //disables dates before start date if start date has been selected
                if(startCal!=null){
                    datePickerDialog.getDatePicker().setMinDate(startCal.getTimeInMillis()-1000);
                }

                else{
                    datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
                }

                datePickerDialog.show();
            }
        });
    }

//    public void initLocationMaps(){
//
//        this.etStartLocation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(AddTripActivity.this, MapActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        this.etEndLocation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(AddTripActivity.this, MapActivity.class);
//                startActivity(intent);
//            }
//        });
//    }

    public void initDropdown(){
        this.type_dropdown = (Spinner) findViewById(R.id.dropdown_add_trip_type);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddTripActivity.this,
                R.layout.item_dropdown, getResources().getStringArray(R.array.type_options));

        arrayAdapter.setDropDownViewResource(R.layout.item_dropdown);
        type_dropdown.setAdapter(arrayAdapter);
    }

    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setTitle("Cancel Adding Trip")
                .setMessage("Would you like to cancel adding a trip?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(AddTripActivity.this, TripsActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    private void getStoragePermission(){
        String [] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if(ContextCompat.checkSelfPermission(getApplicationContext(),
                READ_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getApplicationContext(),
                        WRITE_STORAGE) == PackageManager.PERMISSION_GRANTED){
            //launch gallery
            Intent choosePic = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            choosePicLauncher.launch(choosePic);
        }
        else{
            ActivityCompat.requestPermissions(this, permissions, STORAGE_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case STORAGE_PERMISSION_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent choosePic = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    choosePicLauncher.launch(choosePic);
                } else {
                    //denied
                }
                break;
        }
    }


//    public boolean validDates(String start, String end){
//        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
//        Date startDate = null;
//        Date endDate = null;
//        try {
//            startDate = format.parse(start);
//            endDate = format.parse(end);
//
//            //if end date is set before the start date
//            if(endDate.before(startDate))
//                return false;
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        return true;
//    }
}

