package com.example.sofia.orth_ch_guide;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Sofia on 22.09.2016.
 */
public class ChurchesByFete extends AppCompatActivity {

    TextView today_text;
    ListView listView;
    ArrayList<Church> churches = new ArrayList<>();
    ArrayList<Church> chosen = new ArrayList<>();
    String today;
    DatabaseHelper dbhelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.churches_fete);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        today_text = (TextView)findViewById(R.id.todaysFete);
        listView = (ListView)findViewById(R.id.listView2);

        dbhelper = new DatabaseHelper(getApplicationContext());

        dbhelper.deleteAll();
        dbhelper.addDefault();
        /*if(dbhelper.isEmpty())
        {
            dbhelper.addDefault();
        }*/

        churches = createList(dbhelper.print());
        //System.out.println(churches.size());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM");
        Calendar calendar = Calendar.getInstance();
        //System.out.println("%%%%%%"+calendar.getTime());
        today = dateFormat.format(calendar.getTime());

        select();

        if(!chosen.isEmpty()) {
            today_text.setText("Dzisiaj jest "+today+",\nświęto obchodzą: ");
            listView.setAdapter(new AdapterForListOfChurchesByDiocese(this, R.layout.church_on_list, chosen));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getBaseContext(), TabHostChurch.class);
                    //System.out.println("+++++"+bial.get(position).address+" // "+bial.get(position).parson+" // "+bial.get(position).services);
                    intent.putExtra("cerkiew", chosen.get(position));
                    startActivity(intent);
                }
            });
        }
        else{
            today_text.setText("Dzisiaj jest "+today+"\nnie ma cerkwi obchodzących święta parafialne");
        }


    }

    public void select()
    {
        for (int i = 0; i < churches.size(); i++) {
            System.out.println(churches.get(i).fete);
            if(today.equals(churches.get(i).fete)){
                chosen.add(churches.get(i));
            }
        }
    }

    public ArrayList createList(Cursor cursor){

        ArrayList<Church>newlist = new ArrayList<>();

        String dedication, parson, address, services, fete, diocese;
        double latitude, longitude;

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            dedication = cursor.getString(cursor.getColumnIndex("dedication"));
            parson = cursor.getString(cursor.getColumnIndex("parson"));
            address = cursor.getString(cursor.getColumnIndex("address"));
            latitude = cursor.getDouble(cursor.getColumnIndex("latitude"));
            longitude = cursor.getDouble(cursor.getColumnIndex("longitude"));
            services = cursor.getString(cursor.getColumnIndex("services"));
            fete = cursor.getString(cursor.getColumnIndex("fete"));
            diocese = cursor.getString(cursor.getColumnIndex("diocese"));
            newlist.add(new Church(R.drawable.logo, dedication, parson, latitude, longitude, address, services, fete, diocese));
        }

        return newlist;
    }

}
