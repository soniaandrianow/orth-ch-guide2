package com.example.sofia.orth_ch_guide;

import android.*;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sofia on 19.09.2016.
 */
public class ListOfClosestChurches extends AppCompatActivity {

    ArrayList<Church> churches = new ArrayList<>();
    double longi;
    double lati;
    TextView longT;
    LinearLayout layout;
    TextView latT;
    private final static int NUMBER_OF_CLOSEST = 3;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 0;

    ArrayList<Church> selected = new ArrayList<>(NUMBER_OF_CLOSEST);
    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_of_churches_in_diocese);
        layout = (LinearLayout)findViewById(R.id.layoutLinear);
        Toolbar toolbar = new Toolbar(this);
        layout.addView(toolbar, 0);
        setSupportActionBar(toolbar);
        //longT = (TextView)findViewById(R.id.locationLon);
        //latT = (TextView) findViewById(R.id.locationLat);
        listView = (ListView)findViewById(R.id.listView);
        //setContentView(R.layout.list_of_churches_in_diocese);
        churches.add(new Church(R.drawable.logo, "Białystok 1", "adsahf", 53.16, 23.20, "Białystok, ul. Duża 2", "Niedziela: 10.00", "20.09"));
        churches.add(new Church(R.drawable.logo, "Białystok 2", "mnbcvh", 53.15, 23.12, "Białystok, ul. Mała 1", "Niedziela: 10.30", "25.09"));
        churches.add( new Church(R.drawable.logo, "Gdańsk", "plokpk", 54.37, 18.62, "Gdańsk, ul. Gdanska 12a", "Niedziela: 8.00", "1.10"));
        churches.add(new Church(R.drawable.logo, "Turkowice - monaster", "Któraś siostra", 50.4, 23.44, "Turkowice, Jedyna droga", "Niedziela: 10.00", "20.09"));
        churches.add(new Church(R.drawable.logo, "Hrubieszów", "Proboszcz 1", 50.8, 23.89, "Hrub, ul. Krótka 1", "Niedziela: 10.30", "25.10"));
        churches.add(new Church(R.drawable.logo, "Bończa", "Proboszcz 2", 50.9, 23.42, "Bończa, ul. Długa 12, Jedyna droga", "Niedziela: 8.00", "30.09"));
        churches.add(new Church(R.drawable.logo, "Wrocław", "adsahf", 51.1, 17.0, "Wrocław, ul. Wszystkich Swiętych 1", "Niedziela: 10.00", "21.09"));
        churches.add(new Church(R.drawable.logo, "Legnica", "mnbcvh", 51.2, 16.16, "Legnica, ul. Mała 1", "Niedziela: 10.30", "10.10"));
        churches.add( new Church(R.drawable.logo, "Jelenia Góra", "plokpk", 50.9, 15.7, "Jelenia Góra, ul. Rynek 12a", "Niedziela: 8.00", "03.05"));

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
        }

        selectClosest();

        listView.setAdapter(new AdapterForListOfChurchesByDiocese(this, R.layout.church_on_list, selected));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getBaseContext(), TabHostChurch.class);
                //System.out.println("+++++"+bial.get(position).address+" // "+bial.get(position).parson+" // "+bial.get(position).services);
                intent.putExtra("cerkiew", selected.get(position));
                startActivity(intent);
            }
        });

    }

    public void selectClosest()
    {
        ArrayList<Church> chosen = new ArrayList<>(NUMBER_OF_CLOSEST);
        GPSLocator gpsLocator = new GPSLocator(this);
        if(gpsLocator.canGetLocation) {
            longi = gpsLocator.getLongitude();
            lati = gpsLocator.getLatitude();
        }
        else {gpsLocator.showAlert();}
        System.out.println(longi+" // "+lati);
        for (int k = 0; k < NUMBER_OF_CLOSEST; k++) {
            chosen.add(churches.get(k));
            System.out.println(churches.get(k).dedication);
        }
        for (int i = 0; i < churches.size(); i++) {
            for (int j = 0; j < NUMBER_OF_CLOSEST; j++) {
                if(Math.abs(lati - chosen.get(j).latitude)>Math.abs(lati-churches.get(i).latitude) || Math.abs(longi - chosen.get(j).longitude)>Math.abs(longi-churches.get(i).longitude)){
                    chosen.set(j, churches.get(i));
                    System.out.println(chosen.get(j).dedication);
                    System.out.println("***"+(Math.abs((lati - chosen.get(j).latitude))+"//"+Math.abs((longi - chosen.get(j).longitude))));
                    break;
                }
            }
        }

        selected = chosen;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    selectClosest();
                    listView.setAdapter(new AdapterForListOfChurchesByDiocese(this, R.layout.church_on_list, selected));
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(getBaseContext(), TabHostChurch.class);
                            //System.out.println("+++++"+bial.get(position).address+" // "+bial.get(position).parson+" // "+bial.get(position).services);
                            intent.putExtra("cerkiew", selected.get(position));
                            startActivity(intent);
                        }
                    });

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }


}