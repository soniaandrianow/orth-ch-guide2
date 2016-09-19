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
public class BialostockoGdanska extends Fragment {

    ArrayList<Church> bial = new ArrayList<>();
    ListView listView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.list_of_churches_in_diocese, container, false);
        listView = (ListView) view.findViewById(R.id.listView);
        bial.add(new Church(R.drawable.logo, "Białystok 1", "adsahf", 3.0, 1.0, "Białystok, ul. Duża 2", "Niedziela: 10.00", "20.09"));
        bial.add(new Church(R.drawable.logo, "Białystok 2", "mnbcvh", 3.0, 1.0, "Białystok, ul. Mała 1", "Niedziela: 10.30", "25.09"));
        bial.add( new Church(R.drawable.logo, "Gdańsk", "plokpk", 3.0, 1.0, "Gdańsk, ul. Gdanska 12a", "Niedziela: 8.00", "1.10"));
        //listView.setAdapter(new AdapterForListOfChurchesByDiocese(getContext(), R.layout.diecezja, lubl));
        final Context context = getActivity().getApplicationContext();
        listView.setAdapter(new AdapterForListOfChurchesByDiocese(context, R.layout.church_on_list, bial));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, TabHostChurch.class);
                System.out.println("+++++"+bial.get(position).address+" // "+bial.get(position).parson+" // "+bial.get(position).services);
                intent.putExtra("cerkiew", bial.get(position));
                startActivity(intent);
            }
        });
        return view;
    }
}
