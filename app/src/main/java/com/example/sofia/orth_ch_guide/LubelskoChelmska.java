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
public class LubelskoChelmska extends Fragment {

    Church[] lubl_ch = new Church[]{
            new Church(R.drawable.logo, "Turkowice - monaster", "Któraś siostra", 3.0, 1.0, "Turkowice, Jedyna droga", "Niedziela: 10.00"),
            new Church(R.drawable.logo, "Hrubieszów", "Proboszcz 1", 3.0, 1.0, "Hrub, ul. Krótka 1", "Niedziela: 10.30"),
            new Church(R.drawable.logo, "Bończa", "Proboszcz 2", 3.0, 1.0, "Bończa, ul. Długa 12, Jedyna droga", "Niedziela: 8.00")
    };
    ArrayList<Church> lubl = new ArrayList<>();
    ListView listView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.list_of_churches_in_diocese, container, false);
        listView = (ListView) view.findViewById(R.id.listView);
        lubl.add(new Church(R.drawable.logo, "Turkowice - monaster", "Któraś siostra", 3.0, 1.0, "Turkowice, Jedyna droga", "Niedziela: 10.00"));
        lubl.add(new Church(R.drawable.logo, "Hrubieszów", "Proboszcz 1", 3.0, 1.0, "Hrub, ul. Krótka 1", "Niedziela: 10.30"));
        lubl.add(new Church(R.drawable.logo, "Bończa", "Proboszcz 2", 3.0, 1.0, "Bończa, ul. Długa 12, Jedyna droga", "Niedziela: 8.00"));
        //listView.setAdapter(new AdapterForListOfChurchesByDiocese(getContext(), R.layout.diecezja, lubl));
        final Context context = getActivity().getApplicationContext();
        listView.setAdapter(new AdapterForListOfChurchesByDiocese(context, R.layout.church_on_list, lubl));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, TabHostChurch.class);
                //System.out.println("+++++"+bial.get(position).address+" // "+bial.get(position).parson+" // "+bial.get(position).services);
                intent.putExtra("cerkiew", lubl.get(position));
                startActivity(intent);
            }
        });
        return view;
    }
}
