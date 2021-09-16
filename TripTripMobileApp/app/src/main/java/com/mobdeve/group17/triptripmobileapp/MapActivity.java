package com.mobdeve.group17.triptripmobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "MapActivity";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;

    private EditText etSearchLocation;
    private ImageView ivGps;

    private boolean mLocationPermissionsGranted = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        etSearchLocation = findViewById(R.id.et_search_location);
        ivGps = findViewById(R.id.iv_gps);

        getLocationPermission();

//        Places.initialize(getApplicationContext(), "AIzaSyD7EHw_Ss7DtvZ0bSy5EvK7AIMfvmkhpX8");
//
//        PlacesClient placesClient = Places.createClient(this);

//        etSearchLocation.setFocusable(false);
//        etSearchLocation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS,
//                        Place.Field.LAT_LNG, Place.Field.NAME);
//
//                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,
//                        fieldList).build(MapActivity.this);
//
//                startActivityForResult(intent, 100);
//            }
//        });
    }

    @Override
    public void onMapReady(@NonNull @NotNull GoogleMap googleMap) {
        Toast.makeText(this, "Map is ready", Toast.LENGTH_SHORT).show();
        mMap = googleMap;

        //if permissions for getting locations is granted
        if (mLocationPermissionsGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED) {
                return;
            }

            //marks current location on map
            mMap.setMyLocationEnabled(true);

            //removes default location button that centers map on current location,
            // since the search bar will block it anyway
            mMap.getUiSettings().setMyLocationButtonEnabled(false);

            initComponents();
        }
    }

    private void initComponents(){
        etSearchLocation.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                //sets enter key to trigger search
                if(actionId== EditorInfo.IME_ACTION_SEARCH ||
                        actionId==EditorInfo.IME_ACTION_DONE ||
                        actionId==KeyEvent.ACTION_DOWN ||
                        actionId==KeyEvent.KEYCODE_ENTER){

                    //execute search method
                    geoLocate();
                }

                return false;
            }
        });

        //clicking on gps image centers camera back to current location on map
        ivGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MapActivity", "onClick: clicked gps icon");
                getDeviceLocation();
            }
        });

        //hides keyboard
        hideSoftKeyboard();
    }

    private void geoLocate(){
        Log.d("MapActivity", "geoLocate: geolocating");

        String searchText = etSearchLocation.getText().toString().trim();

        Geocoder geocoder = new Geocoder(MapActivity.this);
        List<Address> list = new ArrayList<>();

        try{
            list = geocoder.getFromLocationName(searchText, 1);

        } catch (IOException e){
            Log.e("MapActivity", "geoLocate: IOException: "+e.getMessage());
            Toast.makeText(this, "Location not found.", Toast.LENGTH_SHORT).show();
        }

        //if search results are found
        if (list.size()>0){
            Address address = list.get(0);

            Log.d("MapActivity", "geoLocate: found a location: "+address.toString());
     //       Toast.makeText(this, address.toString(), Toast.LENGTH_SHORT).show();

            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM,
                    address.getAddressLine(0));
        }
    }

    private void getDeviceLocation(){
        Log.d("MapActivity", "getDeviceLocation: getting current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            if(mLocationPermissionsGranted){
                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task task) {
                        if(task.isSuccessful()){
                            Log.d("MapActivity", "onComplete: current location found");
                            Location currentLocation = (Location) task.getResult();

                            //move camera to current location
                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                    DEFAULT_ZOOM, "Current Location");
                        }

                        else{
                            Log.d("MapActivity", "onComplete: current location null");
                            Toast.makeText(MapActivity.this, "Unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch (SecurityException e){
            Log.e("MapActivity", "getDeviceLocation: SecurityException - "+ e.getMessage());
        }
    }

    /**
     * moves camera to current location (if found) on Google map
     * @param latLng current location in latitude and longitude
     * @param zoom zoom level of camera
     */
    private void moveCamera(LatLng latLng, float zoom, String title){
        Log.d("MapActivity", "moveCamera: move cam to lat: " + latLng.latitude+" lng: "+latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        //places marker on searched locations
        if (!title.equals("Current Location")){
            MarkerOptions options = new MarkerOptions().position(latLng).title(title);
            mMap.addMarker(options);
        }

        //hide keyboard after searching
        hideSoftKeyboard();
    }

    /**
     * initializes Google Map
     */
    private void initMap(){
        Log.d("MapActivity", "initMap: initializing map");
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(MapActivity.this);
    }

    /**
     * gets permissions for location services
     */
    private void getLocationPermission(){
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION)== PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this.getApplicationContext(),
                        COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;
                initMap();
        }
        else{
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mLocationPermissionsGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false;
                            return;
                        }
                    }

                    mLocationPermissionsGranted = true;

                    //initialize map
                    initMap();
                }
        }
    }

    /**
     * hides keyboard for searching
     */
    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}