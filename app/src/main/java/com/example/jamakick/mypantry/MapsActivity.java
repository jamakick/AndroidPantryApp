package com.example.jamakick.mypantry;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    //set constant for the permission request
    int PERMISSION_REQUEST = 100;

    private static final int flag1 = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //create our map
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //check permissions just in case they were not added in the mainactivity
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST);

            return;
        }

        else {
            //if they are added we create some pins in bloomington and put the map there
            //same code as examples in class
            mMap.setMyLocationEnabled(true);
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

            UiSettings settings = mMap.getUiSettings();
            settings.setZoomControlsEnabled(true);
            settings.setScrollGesturesEnabled(true);
            settings.setTiltGesturesEnabled(true);
            settings.setRotateGesturesEnabled(true);


            LatLng sice = new LatLng(39.1730, -86.5230);
            mMap.addMarker(new MarkerOptions().position(sice).title("SICE"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sice, 12));

            LatLng wells = new LatLng(39.1710, -86.5168);
            mMap.addMarker(new MarkerOptions().position(wells).title("Wells Library"));

            LatLng jordan = new LatLng(39.1649, -86.5211);
            mMap.addMarker(new MarkerOptions().position(jordan).title("Jordan Hall"));
        }
    }

    public void backToMain(View v) {

        //takes you back to the Pantry
        Intent intent1 = new Intent(this, MainActivity.class);

        intent1.addFlags(flag1);

        startActivity(intent1);

    }

}
