package com.example.sofia.orth_ch_guide;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * Created by Sofia on 22.09.2016.
 */
public class MapOfAll extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    ArrayList <Church> churches = new ArrayList<>();
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 0;
    public double longi;
    public double lati;
    LatLng camPos;
    GoogleMap googleMap;
    DatabaseHelper dbhelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_of_all);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapOfAll);
        mapFragment.getMapAsync(this);

        dbhelper = MainActivity.dbhelper;
        //dbhelper = new DatabaseHelper(getApplicationContext());

        churches = createList(dbhelper.print());
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        for (int i = 0; i < churches.size(); i++) {
            LatLng position = new LatLng(churches.get(i).latitude, churches.get(i).longitude);
            googleMap.addMarker(new MarkerOptions().position(position).title(churches.get(i).dedication));

        }
        googleMap.setOnMarkerClickListener(this);
        this.googleMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
        }

        getCurrentLocation();

    }

    public void getCurrentLocation()
    {
        GPSLocator gpsLocator = new GPSLocator(this);
        if (gpsLocator.canGetLocation) {
            longi = gpsLocator.getLongitude();
            lati = gpsLocator.getLatitude();
        } else {
            gpsLocator.showAlert();
        }
        googleMap.addMarker(new MarkerOptions().position(new LatLng(lati, longi)).title("Your location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        CameraUpdate center=
                CameraUpdateFactory.newLatLng(new LatLng(lati, longi));
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(5);

        googleMap.moveCamera(center);
        googleMap.animateCamera(zoom);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        for (int i = 0; i < churches.size(); i++) {
            if(marker.getTitle().equals(churches.get(i).dedication)){
                System.out.println("***"+marker.getTitle()+"==="+churches.get(i).dedication);
                Intent intent = new Intent(getBaseContext(), TabHostChurch.class);
                intent.putExtra("cerkiew", churches.get(i));
                startActivity(intent);
            }
        }
        return false;
    }

    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getCurrentLocation();
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }

    public ArrayList createList(Cursor cursor){

        ArrayList<Church>newlist = new ArrayList<>();
        String dedication, parson, address, services, fete, diocese, style, short_history;
        double latitude, longitude;
        int century;
        boolean wooden;

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            dedication = cursor.getString(cursor.getColumnIndex("dedication"));
            parson = cursor.getString(cursor.getColumnIndex("parson"));
            address = cursor.getString(cursor.getColumnIndex("address"));
            latitude = cursor.getDouble(cursor.getColumnIndex("latitude"));
            longitude = cursor.getDouble(cursor.getColumnIndex("longitude"));
            services = cursor.getString(cursor.getColumnIndex("services"));
            fete = cursor.getString(cursor.getColumnIndex("fete"));
            diocese = cursor.getString(cursor.getColumnIndex("diocese"));
            style = cursor.getString(cursor.getColumnIndex("style"));
            short_history = cursor.getString(cursor.getColumnIndex("short_history"));
            century = cursor.getInt(cursor.getColumnIndex("century"));
            wooden = cursor.getInt(cursor.getColumnIndex("wooden"))>0;
            newlist.add(new Church(new int[]{R.drawable.ch1, R.drawable.ch2, R.drawable.ch3}, dedication, parson, latitude, longitude, address, services, fete, style, century, short_history, wooden, diocese));
        }
        return newlist;
    }

}
