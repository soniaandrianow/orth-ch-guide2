package com.example.sofia.orth_ch_guide;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_of_all);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapOfAll);
        mapFragment.getMapAsync(this);

        churches.add(new Church(R.drawable.logo, "Białystok 1", "adsahf", 53.16, 23.20, "Białystok, ul. Duża 2", "Niedziela: 10.00", "20.09"));
        churches.add(new Church(R.drawable.logo, "Białystok 2", "mnbcvh", 53.15, 23.12, "Białystok, ul. Mała 1", "Niedziela: 10.30", "25.09"));
        churches.add( new Church(R.drawable.logo, "Gdańsk", "plokpk", 54.37, 18.62, "Gdańsk, ul. Gdanska 12a", "Niedziela: 8.00", "1.10"));
        churches.add(new Church(R.drawable.logo, "Turkowice - monaster", "Któraś siostra", 50.4, 23.44, "Turkowice, Jedyna droga", "Niedziela: 10.00", "20.09"));
        churches.add(new Church(R.drawable.logo, "Hrubieszów", "Proboszcz 1", 50.8, 23.89, "Hrub, ul. Krótka 1", "Niedziela: 10.30", "25.10"));
        churches.add(new Church(R.drawable.logo, "Bończa", "Proboszcz 2", 50.9, 23.42, "Bończa, ul. Długa 12, Jedyna droga", "Niedziela: 8.00", "30.09"));
        churches.add(new Church(R.drawable.logo, "Wrocław", "adsahf", 51.1, 17.0, "Wrocław, ul. Wszystkich Swiętych 1", "Niedziela: 10.00", "21.09"));
        churches.add(new Church(R.drawable.logo, "Legnica", "mnbcvh", 51.2, 16.16, "Legnica, ul. Mała 1", "Niedziela: 10.30", "10.10"));
        churches.add( new Church(R.drawable.logo, "Jelenia Góra", "plokpk", 50.9, 15.7, "Jelenia Góra, ul. Rynek 12a", "Niedziela: 8.00", "03.05"));


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


        /*CameraPosition camPos = new CameraPosition.Builder()
                .target(new LatLng(lati, longi))
                .build();
        CameraUpdate camUpd = CameraUpdateFactory.newCameraPosition(camPos);
        googleMap.animateCamera(camUpd);*/
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
        System.out.println("******"+longi + " // " + lati);
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
}