package com.example.sofia.orth_ch_guide;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;

import java.util.ArrayList;

/**
 * Created by Sofia on 27.10.2016.
 */
public class SearchForChurches extends AppCompatActivity {

    ArrayList<Church> churches = new ArrayList<>();
    ArrayList<Church> chosen = new ArrayList<>();
    DatabaseHelper dbhelper;
    EditText cent;
    Switch wood;
    int chosen_century;
    boolean wooden;
    ListView listView;
    Button search_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_for_churches);
        cent = (EditText)findViewById(R.id.editText);
        wood = (Switch)findViewById(R.id.switch1);
        listView = (ListView)findViewById(R.id.listView2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //dbhelper = new DatabaseHelper(getApplicationContext());
        search_btn = (Button) findViewById(R.id.button6);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("wyszukujesz");
                chosen.clear();
                try{
                    chosen_century = Integer.parseInt(cent.getText().toString());
                }catch(NumberFormatException ex){
                    chosen_century = 0;
                }
                wooden = wood.isChecked();
                select();
                dbhelper = MainActivity.dbhelper;
                churches = createList(dbhelper.print());
                if(!chosen.isEmpty()) {
                    listView.setAdapter(new AdapterForListOfChurchesByDiocese(getApplicationContext(), R.layout.church_on_list, chosen));
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(getBaseContext(), TabHostChurch.class);
                            intent.putExtra("cerkiew", chosen.get(position));
                            startActivity(intent);
                        }
                    });
                }
                else{

                }

            }
        });

    }

    public ArrayList createList(Cursor cursor){

        ArrayList<Church>newlist = new ArrayList<>();

        String dedication, parson, address, services, fete, diocese, style, short_history;
        double latitude, longitude;
        int century;
        boolean wooden;

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            dedication = cursor.getString(cursor.getColumnIndex("dedication"));
            parson = cursor.getString(cursor.getColumnIndex("parson"));
            address = cursor.getString(cursor.getColumnIndex("address"));
            latitude = cursor.getDouble(cursor.getColumnIndex("latitude"));
            longitude = cursor.getDouble(cursor.getColumnIndex("longitude"));
            services = cursor.getString(cursor.getColumnIndex("services"));
            fete = cursor.getString(cursor.getColumnIndex("fete"));
            diocese = cursor.getString(cursor.getColumnIndex("diocese"));
            style = cursor.getString(cursor.getColumnIndex("style"));
            short_history = cursor.getString(cursor.getColumnIndex("short_history"));
            century = cursor.getInt(cursor.getColumnIndex("century"));
            wooden = cursor.getInt(cursor.getColumnIndex("wooden"))>0;
            newlist.add(new Church(new int[]{R.drawable.ch1, R.drawable.ch2, R.drawable.ch3}, dedication, parson, latitude, longitude, address, services, fete, style, century, short_history, wooden, diocese));
        }

        return newlist;
    }

    public void select(){
        if(chosen_century!=0){
            for (int i = 0; i < churches.size(); i++) {
                if (churches.get(i).century == chosen_century) {
                    chosen.add(churches.get(i));
                }
            }
        }else{
            for (int i = 0; i < churches.size(); i++) {
                chosen.add(churches.get(i));
            }
        }
        if(wooden){
            for (int i = 0; i < chosen.size(); i++) {
                if(!chosen.get(i).wooden){
                    chosen.remove(i);
                }
            }
        }
    }
}
