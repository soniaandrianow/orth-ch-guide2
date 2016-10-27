package com.example.sofia.orth_ch_guide;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Sofia on 13.09.2016.
 */
public class SwipeViewsPagerForDioceses extends FragmentStatePagerAdapter {

    int tabCount;
    DatabaseHelper dbhelper;
    ArrayList<Church>selected = new ArrayList<>();

    public SwipeViewsPagerForDioceses(FragmentManager fragmentManager, int tabCount, Context context) {
        super(fragmentManager);
        this.tabCount = tabCount;
        dbhelper = new DatabaseHelper(context);

        Cursor cursor = dbhelper.print();
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            System.out.println("+++++ "+cursor.getString(cursor.getColumnIndex("dedication"))+" - "+cursor.getString(cursor.getColumnIndex("diocese")));
        }
    }

    @Override
    public Fragment getItem(int position) {
        String input;
        Bundle extras1;
        Diocese diocese;

        switch(position) {
            case 0:
               input = "Wrocławsko-Szczecińska";
                try {
                    selected = createList(dbhelper.selectDiocese(input));
                }catch(SQLException ex){
                    ex.printStackTrace();
                }
               /* for (int i = 0; i < selected.size(); i++) {
                    System.out.println(selected.get(i).dedication);
                }*/
                extras1 = new Bundle();
                extras1.putParcelableArrayList("selected", selected);
                diocese = new Diocese();
                diocese.setArguments(extras1);
                return diocese;
            case 1:
                input = "Białostocko-Gdańska";
                try {
                    selected = createList(dbhelper.selectDiocese(input));
                }catch(SQLException ex){
                    ex.printStackTrace();
                }
               /* for (int i = 0; i < selected.size(); i++) {
                    System.out.println(selected.get(i).dedication);
                }*/
                extras1 = new Bundle();
                extras1.putParcelableArrayList("selected", selected);
                diocese = new Diocese();
                diocese.setArguments(extras1);
                return diocese;
            case 2:
                input = "Lubelsko-Chełmska";
                try {
                    selected = createList(dbhelper.selectDiocese(input));
                }catch(SQLException ex){
                    ex.printStackTrace();
                }
               /* for (int i = 0; i < selected.size(); i++) {
                    System.out.println(selected.get(i).dedication);
                }*/
                extras1 = new Bundle();
                extras1.putParcelableArrayList("selected", selected);
                diocese = new Diocese();
                diocese.setArguments(extras1);
                return diocese;
            case 3:
                input = "Białostocko-Gdańska";
                try {
                    selected = createList(dbhelper.selectDiocese(input));
                }catch(SQLException ex){
                    ex.printStackTrace();
                }
               /* for (int i = 0; i < selected.size(); i++) {
                    System.out.println(selected.get(i).dedication);
                }*/
                extras1 = new Bundle();
                extras1.putParcelableArrayList("selected", selected);
                diocese = new Diocese();
                diocese.setArguments(extras1);
                return diocese;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
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
            newlist.add(new Church(R.drawable.logo, dedication, parson, latitude, longitude, address, services, fete, style, century, short_history, wooden, diocese));
        }

        return newlist;
    }
}
