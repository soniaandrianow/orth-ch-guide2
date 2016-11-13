package com.example.sofia.orth_ch_guide;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Sofia on 26.09.2016.
 */
public class Diocese extends Fragment {

    public ArrayList<Church> churches = new ArrayList<>();
    ListView listView;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.list_of_churches_in_diocese, container, false);
        listView = (ListView) view.findViewById(R.id.listView);

        Bundle extras = getArguments();
        if(extras!=null)
        {
            churches = extras.getParcelableArrayList("selected");
        }
        else {
            System.out.println("null");
        }

        final Context context = getActivity().getApplicationContext();
        listView.setAdapter(new AdapterForListOfChurchesByDiocese(context, R.layout.church_on_list, churches));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, TabHostChurch.class);
                intent.putExtra("cerkiew", churches.get(position));
                startActivity(intent);
            }
        });
        return view;
    }
}
