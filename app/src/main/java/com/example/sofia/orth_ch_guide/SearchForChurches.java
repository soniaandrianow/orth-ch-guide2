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
                chosen = new Helper().search(chosen_century, wooden);
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

}
