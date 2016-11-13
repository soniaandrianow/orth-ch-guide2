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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.churches_fete);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        today_text = (TextView)findViewById(R.id.todaysFete);
        listView = (ListView)findViewById(R.id.listView2);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM");
        Calendar calendar = Calendar.getInstance();
        today = dateFormat.format(calendar.getTime());

        chosen = new Helper().selectByFete();

        if(!chosen.isEmpty()) {
            today_text.setText("Dzisiaj jest "+today+",\nświęto obchodzą: ");
            listView.setAdapter(new AdapterForListOfChurchesByDiocese(this, R.layout.church_on_list, chosen));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getBaseContext(), TabHostChurch.class);
                    intent.putExtra("cerkiew", chosen.get(position));
                    startActivity(intent);
                }
            });
        }
        else{
            today_text.setText("Dzisiaj jest "+today+"\nnie ma cerkwi obchodzących święta parafialne");
        }
    }

}
