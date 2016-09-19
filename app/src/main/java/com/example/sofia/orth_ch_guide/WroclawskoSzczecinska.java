package com.example.sofia.orth_ch_guide;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Sofia on 13.09.2016.
 */
public class WroclawskoSzczecinska extends Fragment {

    ArrayList<Church> wroc = new ArrayList<>();
    ListView listView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.list_of_churches_in_diocese, container, false);
        listView = (ListView) view.findViewById(R.id.listView);
        wroc.add(new Church(R.drawable.logo, "Wrocław", "adsahf", 3.0, 1.0, "Wrocław, ul. Wszystkich Swiętych 1", "Niedziela: 10.00", "21.09"));
        wroc.add(new Church(R.drawable.logo, "Legnica", "mnbcvh", 3.0, 1.0, "Legnica, ul. Mała 1", "Niedziela: 10.30", "10.10"));
        wroc.add( new Church(R.drawable.logo, "Jelenia Góra", "plokpk", 3.0, 1.0, "Jelenia Góra, ul. Rynek 12a", "Niedziela: 8.00", "03.05"));
        final Context context = getActivity().getApplicationContext();
        listView.setAdapter(new AdapterForListOfChurchesByDiocese(context, R.layout.church_on_list, wroc));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, TabHostChurch.class);
                //System.out.println("+++++"+bial.get(position).address+" // "+bial.get(position).parson+" // "+bial.get(position).services);
                intent.putExtra("cerkiew", wroc.get(position));
                startActivity(intent);
            }
        });
        return view;
    }
}
