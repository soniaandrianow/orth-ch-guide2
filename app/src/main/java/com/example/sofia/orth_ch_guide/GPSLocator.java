package com.example.sofia.orth_ch_guide;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.security.Provider;

/**
 * Created by Sofia on 19.09.2016.
 */
public class GPSLocator extends Service implements LocationListener {

    private final Context context;
    boolean GPSEnabled = false;
    boolean networkEnabled = false;
    boolean canGetLocation = false;

    Location location;
    double latitude;
    double longitude;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATE = 10;
    private static final long MIN_TIME_BETWEEN_UPDATES = 1000 * 60 * 1;



    protected LocationManager locationManager;

    public GPSLocator(Context context) {
        this.context = context;
        getLocation();
    }

    public Location getLocation() {
        try {
            locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
            GPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!GPSEnabled && !networkEnabled) {

            } else {

                this.canGetLocation = true;
                if (networkEnabled) {

                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BETWEEN_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATE, this);
                    Log.d("Network", "Network");

                    if(locationManager != null){
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if(location!=null){
                            latitude = location.getLatitude();
                            longitude=location.getLongitude();
                        }
                    }
                }
                if(GPSEnabled){
                    if (location == null){
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BETWEEN_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATE, this);
                        Log.d("GPS enabled", "GPS enabled");
                        if (locationManager != null) {
                            location=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if(location != null){
                                latitude=location.getLatitude();
                                longitude=location.getLongitude();
                            }
                        }
                    }
                }
            }
        }
        catch(SecurityException se){
            se.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return location;
    }

    public void stopGPS() {
        try {
            if (locationManager != null) {
                locationManager.removeUpdates(GPSLocator.this);
            }
        } catch (SecurityException se) {
            se.printStackTrace();
        }
    }

    public  double getLatitude(){
        if(location != null){
            latitude=location.getLatitude();
        }
        return latitude;
    }

    public  double getLongitude(){
        if(location != null){
            longitude=location.getLongitude();
        }
        return longitude;
    }

    public boolean canGetLocation(){
        return this.canGetLocation;
    }

    public void showAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("GPS Settings");
        alertDialog.setMessage("GPS isn't enabled. Do you want to enable it?");
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
