package com.example.sofia.orth_ch_guide;

import android.content.Intent;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.churches_fete);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        today_text = (TextView)findViewById(R.id.todaysFete);
        listView = (ListView)findViewById(R.id.listView2);

        churches.add(new Church(R.drawable.logo, "Białystok 1", "adsahf", 53.16, 23.20, "Białystok, ul. Duża 2", "Niedziela: 10.00", "20.09"));
        churches.add(new Church(R.drawable.logo, "Białystok 2", "mnbcvh", 53.15, 23.12, "Białystok, ul. Mała 1", "Niedziela: 10.30", "25.09"));
        churches.add( new Church(R.drawable.logo, "Gdańsk", "plokpk", 54.37, 18.62, "Gdańsk, ul. Gdanska 12a", "Niedziela: 8.00", "1.10"));
        churches.add(new Church(R.drawable.logo, "Turkowice - monaster", "Któraś siostra", 50.4, 23.44, "Turkowice, Jedyna droga", "Niedziela: 10.00", "22.09"));
        churches.add(new Church(R.drawable.logo, "Hrubieszów", "Proboszcz 1", 50.8, 23.89, "Hrub, ul. Krótka 1", "Niedziela: 10.30", "25.10"));
        churches.add(new Church(R.drawable.logo, "Bończa", "Proboszcz 2", 50.9, 23.42, "Bończa, ul. Długa 12, Jedyna droga", "Niedziela: 8.00", "30.09"));
        churches.add(new Church(R.drawable.logo, "Wrocław", "adsahf", 51.1, 17.0, "Wrocław, ul. Wszystkich Swiętych 1", "Niedziela: 10.00", "21.09"));
        churches.add(new Church(R.drawable.logo, "Legnica", "mnbcvh", 51.2, 16.16, "Legnica, ul. Mała 1", "Niedziela: 10.30", "10.10"));
        churches.add( new Church(R.drawable.logo, "Jelenia Góra", "plokpk", 50.9, 15.7, "Jelenia Góra, ul. Rynek 12a", "Niedziela: 8.00", "03.05"));


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
            if(today.equals(churches.get(i).fete)){
                chosen.add(churches.get(i));
            }
        }
    }
}
