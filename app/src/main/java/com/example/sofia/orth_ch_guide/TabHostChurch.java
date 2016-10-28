package com.example.sofia.orth_ch_guide;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Sofia on 15.09.2016.
 */
public class TabHostChurch extends AppCompatActivity implements OnMapReadyCallback{

    TabHost tabhost;
    ImageView image;
    TextView name;
    TextView address;
    TextView parson;
    TextView hours;
    Intent intent;
    String position;
    Church ch;
    TextView sh_history;
    //private GoogleMap googleMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.church_details_tab_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabhost = (TabHost)findViewById(R.id.tabHost);

        tabhost.setup();

        TabHost.TabSpec spec = tabhost.newTabSpec("Dane kontaktowe");
        spec.setContent(R.id.linearLayout);
        spec.setIndicator("Dane Kontaktowe");
        tabhost.addTab(spec);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

        //image = (ImageView)findViewById(R.id.church_image);
        name = (TextView) findViewById(R.id.church_name);
        address = (TextView) findViewById(R.id.church_address);
        parson = (TextView) findViewById(R.id.church_parson);



        intent=getIntent();
        //position = intent.getStringExtra("id");
        ch = (Church) intent.getParcelableExtra("cerkiew");
        //image.setImageResource(ch.image);
        name.setText(ch.dedication);
        address.setText(ch.address);
        parson.setText("Proboszcz parafii: "+ch.parson);
        viewPager.setAdapter(new CustomSwipeAdapter(this, ch));

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

        /*System.out.println(ch.dedication);
        System.out.println(ch.address);
        System.out.println(ch.parson);
        System.out.println(ch.services);*/
       /* System.out.println(ch.short_history);
        System.out.println(ch.style);
        System.out.println(ch.wooden);
*/
        spec = tabhost.newTabSpec("Godziny");
        spec.setContent(R.id.linearLayout2);
        spec.setIndicator("Godziny Nabożeństw");
        tabhost.addTab(spec);

        hours = (TextView)findViewById(R.id.church_hours);
        hours.setText(ch.services);

        spec = tabhost.newTabSpec("Historia");
        spec.setContent(R.id.linearLayout3);
        spec.setIndicator("Historia cerkwi");
        tabhost.addTab(spec);

        sh_history = (TextView)findViewById(R.id.history);
        sh_history.setText(ch.short_history);
        System.out.println(ch.short_history);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LatLng position = new LatLng(ch.latitude, ch.longitude);
        googleMap.addMarker(new MarkerOptions().position(position).title(ch.dedication));
        CameraUpdate center=
                CameraUpdateFactory.newLatLng(position);
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(10);
        googleMap.moveCamera(center);
        googleMap.animateCamera(zoom);
    }
}
