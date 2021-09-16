package com.mobdeve.group17.triptripmobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

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
import android.widget.SearchView;
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
import com.mobdeve.group17.triptripmobileapp.utils.Constants;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap map;
    SupportMapFragment mapFragment;
    SearchView svLocation;

    String setLocation ="";
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        svLocation = findViewById(R.id.et_search_location);
        svLocation.setQueryHint("Enter an address, city, or country");
        svLocation.setIconifiedByDefault(false);
//
//        Intent intent =getIntent();
//        id = intent.getIntExtra(Constants.ET_KEY, 0);
//        etLocation = (TextView)

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        svLocation.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = svLocation.getQuery().toString();
                List<Address> addressList = null;

                if(location!=null || !location.equals("")) {
                    Geocoder geocoder = new Geocoder(MapActivity.this);

                    try{
                        addressList = geocoder.getFromLocationName(location,1);

                    } catch (IOException e) {
                        Log.e("MapActivity", "geoLocate: IOException: "+e.getMessage());
                        Toast.makeText(MapActivity.this, "Location not found", Toast.LENGTH_SHORT).show();

                        finish();
                    }

                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    Log.d("MapActivity", "onQueryTextSubmit address: "+ address.getLocality()+" "+address.getCountryName());

                    //if known name is available
                    if(!address.getFeatureName().isEmpty()) {
                        setLocation = address.getFeatureName()+" - "+
                                address.getLocality()+", "+address.getCountryName();;
                    }
                    //if no known name, only city and country is known
                    else if(!address.getLocality().isEmpty()){
                        setLocation = address.getLocality()+", "+address.getCountryName();
                    }
                    //if only country is known
                    else{
                        setLocation = address.getCountryName();
                    }

                    map.addMarker(new MarkerOptions().position(latLng).title(setLocation));
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));

                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull @NotNull GoogleMap googleMap) {
        map = googleMap;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if(svLocation.getQuery().toString().isEmpty()){
            Toast.makeText(MapActivity.this, "Your searched location is: "+
                    setLocation, Toast.LENGTH_LONG).show();
        }

        else{
            Toast.makeText(MapActivity.this, "Your searched location is: "+
                    svLocation.getQuery().toString()+" at "+setLocation, Toast.LENGTH_LONG).show();
        }

    }
}
