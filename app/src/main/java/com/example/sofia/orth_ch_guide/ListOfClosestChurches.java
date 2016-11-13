package com.example.sofia.orth_ch_guide;

import android.*;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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

    LinearLayout layout;
    private final static int NUMBER_OF_CLOSEST = 2;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 0;
    ArrayList<Church> selected = new ArrayList<>(NUMBER_OF_CLOSEST);
    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_of_churches_in_diocese);
        layout = (LinearLayout)findViewById(R.id.layoutLinear);
        Toolbar toolbar = new Toolbar(this);
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        layout.addView(toolbar, 0);
        setSupportActionBar(toolbar);
        listView = (ListView)findViewById(R.id.listView);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
        }


        selected = new Helper().selectByDistance(NUMBER_OF_CLOSEST, this);

        listView.setAdapter(new AdapterForListOfChurchesByDiocese(this, R.layout.church_on_list, selected));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getBaseContext(), TabHostChurch.class);
                intent.putExtra("cerkiew", selected.get(position));
                startActivity(intent);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    selected = new Helper().selectByDistance(NUMBER_OF_CLOSEST, this);
                    listView.setAdapter(new AdapterForListOfChurchesByDiocese(this, R.layout.church_on_list, selected));
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(getBaseContext(), TabHostChurch.class);
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
