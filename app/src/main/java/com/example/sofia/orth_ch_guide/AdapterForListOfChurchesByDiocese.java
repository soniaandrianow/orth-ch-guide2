package com.example.sofia.orth_ch_guide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sofia on 13.09.2016.
 */
public class AdapterForListOfChurchesByDiocese extends ArrayAdapter<Church> {

    private final Context context;
    private final int resource;
    private final ArrayList<Church> objects;
    private LayoutInflater inflater;


    public AdapterForListOfChurchesByDiocese(Context context, int resource, ArrayList<Church> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
        inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        v = inflater.inflate(resource, (ViewGroup) convertView, false);
        Church church = objects.get(position);
        if (church != null) {
            TextView t = (TextView) v.findViewById(R.id.name);
            ImageView i = (ImageView) v.findViewById(R.id.image);

            t.setText(church.dedication);
            i.setImageResource(R.drawable.logo);
        }
        return v;
    }

    public void clear(){
        objects.clear();
    }
}
