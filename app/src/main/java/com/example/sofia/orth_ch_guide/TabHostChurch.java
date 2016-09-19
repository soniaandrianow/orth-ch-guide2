package com.example.sofia.orth_ch_guide;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Sofia on 15.09.2016.
 */
public class TabHostChurch extends AppCompatActivity implements OnMapReadyCallback{

    TabHost tabhost;
    ImageView image;
    TextView name;
    TextView address;
    TextView hours;
    Intent intent;
    String position;
    Church ch;
    //private GoogleMap googleMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_host_church);
        tabhost = (TabHost)findViewById(R.id.tabHost);

        tabhost.setup();

        TabHost.TabSpec spec = tabhost.newTabSpec("Dane kontaktowe");
        spec.setContent(R.id.linearLayout);
        spec.setIndicator("Dane Kontaktowe");
        tabhost.addTab(spec);

        image = (ImageView)findViewById(R.id.church_image);
        name = (TextView) findViewById(R.id.church_name);
        address = (TextView) findViewById(R.id.church_address);



        intent=getIntent();
        //position = intent.getStringExtra("id");
        ch = (Church) intent.getParcelableExtra("cerkiew");
        image.setImageResource(ch.image);
        name.setText(ch.dedication);
        address.setText(ch.address);

        /*try{
            if(googleMap == null) {*/
                MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
                mapFragment.getMapAsync(this);

                //googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
            //}
            //googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            /*LatLng position = new LatLng(ch.latitude, ch.longitude);
            Marker churchMarker = googleMap.addMarker(new MarkerOptions().position(position).title(ch.dedication));*/
        /*}
        catch (Exception e){
            e.printStackTrace();
        }*/

        System.out.println(ch.dedication);
        System.out.println(ch.address);
        System.out.println(ch.parson);
        System.out.println(ch.services);

        spec = tabhost.newTabSpec("Godziny");
        spec.setContent(R.id.linearLayout2);
        spec.setIndicator("Godziny Nabożeństw");
        tabhost.addTab(spec);

        hours = (TextView)findViewById(R.id.church_hours);
        hours.setText(ch.services);



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LatLng position = new LatLng(ch.latitude, ch.longitude);
        googleMap.addMarker(new MarkerOptions().position(position).title(ch.dedication));
    }
}
