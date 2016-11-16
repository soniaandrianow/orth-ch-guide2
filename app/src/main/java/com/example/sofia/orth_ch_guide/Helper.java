package com.example.sofia.orth_ch_guide;

import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.support.v4.app.ActivityCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Sofia on 11.11.2016.
 */
public class Helper {

    ArrayList<Church> churches = new ArrayList<>();
    ArrayList<Church> chosen = new ArrayList<>();
    DatabaseHelper dbhelper;
    String today;

    public Helper() {
        dbhelper = MainActivity.dbhelper;
        churches = createList(dbhelper.print());
    }

    public ArrayList createList(Cursor cursor) {

        ArrayList<Church> newlist = new ArrayList<>();

        String dedication, parson, address, services, fete, diocese, style, short_history;
        double latitude, longitude;
        int century;
        boolean wooden;
        String photos;

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
            wooden = cursor.getInt(cursor.getColumnIndex("wooden")) > 0;
            photos = cursor.getString(cursor.getColumnIndex("photos"));
            newlist.add(new Church(dedication, parson, latitude, longitude, address, services, fete, style, century, short_history, wooden, diocese, photos));
        }

        return newlist;
    }

    public ArrayList<Church> selectByFete() {

        chosen.clear();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM");
        Calendar calendar = Calendar.getInstance();
        today = dateFormat.format(calendar.getTime());

        for (int i = 0; i < churches.size(); i++) {
            if (today.equals(churches.get(i).fete)) {
                chosen.add(churches.get(i));
            }
        }
        return chosen;
    }

    public ArrayList<Church> selectByDistance(int count, Context context) {
        chosen.clear();

        double longi = 0.0;
        double lati = 0.0;
        ArrayList<Church> chosen = new ArrayList<>(count);
        GPSLocator gpsLocator = new GPSLocator(context);
        if (gpsLocator.canGetLocation) {
            longi = gpsLocator.getLongitude();
            lati = gpsLocator.getLatitude();
        } else {
            gpsLocator.showAlert();
        }
        System.out.println(longi + " // " + lati);
        for (int k = 0; k < count; k++) {
            chosen.add(churches.get(k));
            System.out.println(churches.get(k).dedication);
        }
        for (int i = 0; i < churches.size(); i++) {
            for (int j = 0; j < count; j++) {
                if (Math.abs(lati - chosen.get(j).latitude) > Math.abs(lati - churches.get(i).latitude) && Math.abs(longi - chosen.get(j).longitude) > Math.abs(longi - churches.get(i).longitude)) {
                    if(!chosen.contains(churches.get(i))) {
                        chosen.set(j, churches.get(i));
                        System.out.println(chosen.get(j).dedication);
                        System.out.println("***" + (Math.abs((lati - chosen.get(j).latitude)) + "//" + Math.abs((longi - chosen.get(j).longitude))));
                        break;
                    }
                }
            }
        }

        return chosen;
    }

    public ArrayList<Church> search(int chosen_century, boolean wooden){
        chosen.clear();
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
        return chosen;
    }

    public ArrayList<Church> selectByDiocese(String diocese){
        //System.out.println("Przekazana nazwa"+ diocese);
        chosen.clear();
        for (int i = 0; i < churches.size() ; i++) {
            {
                if(churches.get(i).diocese.equals(diocese)){
                    chosen.add(churches.get(i));
                }
            }
        }
        return chosen;
    }
}
