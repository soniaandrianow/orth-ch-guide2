package com.example.sofia.orth_ch_guide;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by Sofia on 13.09.2016.
 */
public class ListOfAllChurchesByDioceseActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Wrocławsko-Szczecińska"));
        tabLayout.addTab(tabLayout.newTab().setText("Białostocko-Gdańska"));
        tabLayout.addTab(tabLayout.newTab().setText("Lubelsko-Chełmska"));
        tabLayout.addTab(tabLayout.newTab().setText("Warszawsko-Bielska"));
        tabLayout.addTab(tabLayout.newTab().setText("Lódzko-Poznańska"));
        tabLayout.addTab(tabLayout.newTab().setText("Przemysko-Nowosądecka"));
        //tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);

        viewPager = (ViewPager) findViewById(R.id.pager);

        SwipeViewsPagerForDioceses adapter = new SwipeViewsPagerForDioceses(getSupportFragmentManager(), tabLayout.getTabCount(), getApplicationContext());

        viewPager.setAdapter(adapter);

        tabLayout.addOnTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
        System.out.println("*******************"+tab.getPosition());

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
