package com.example.sofia.orth_ch_guide;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;

/**
 * Created by Sofia on 26.09.2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "churchesOfPoland";
    public static final String TABLE_CHURCHES = "churches";
    public static final String FIELD_ROWID = "_id";
    public static final String FIELD_DEDICATION = "dedication";
    public static final String FIELD_PARSON = "parson";
    public static final String FIELD_LONGITUDE = "longitude";
    public static final String FIELD_lATITUDE = "latitude";
    public static final String FIELD_ADDRESS = "address";
    public static final String FIELD_SERVICES = "services";
    public static final String FIELD_FETE = "fete";
    public static final String FIELD_DIOCESE = "diocese";

    private SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        //super(context, name, factory, version);
        super(context, DATABASE_NAME, null, 1);
        //this.db = db;
        this.db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table " + TABLE_CHURCHES + " ( " +
                FIELD_ROWID + " integer primary key autoincrement, " +
                FIELD_DEDICATION + " text, " +
                FIELD_PARSON + " text, " +
                FIELD_lATITUDE + " double, " +
                FIELD_LONGITUDE + " double, " +
                FIELD_ADDRESS + " text, " +
                FIELD_SERVICES + " text, " +
                FIELD_FETE + " text, " +
                FIELD_DIOCESE + " text );"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean isEmpty() {
        String count = "SELECT count(*) FROM " + TABLE_CHURCHES;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if (icount > 0) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor print() {
        String[] columns = {FIELD_ROWID,
                FIELD_DEDICATION, FIELD_PARSON, FIELD_LONGITUDE, FIELD_lATITUDE, FIELD_ADDRESS, FIELD_SERVICES, FIELD_FETE, FIELD_DIOCESE};
        Cursor cursor = db.query(TABLE_CHURCHES, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int deleteAll() {
        int count = db.delete(TABLE_CHURCHES, null, null);
        return count;
    }

    public Cursor selectDiocese(String input) throws SQLException {
        Cursor cursor = null;
        if(input == null || input.length() == 0){
            cursor = db.query(TABLE_CHURCHES, new String[]{FIELD_ROWID,
                    FIELD_DEDICATION, FIELD_PARSON, FIELD_ADDRESS, FIELD_LONGITUDE, FIELD_lATITUDE, FIELD_SERVICES, FIELD_FETE, FIELD_DIOCESE},
                    null, null, null, null, null);
        }
        else {
            cursor = db.query(true, TABLE_CHURCHES, new String[]{FIELD_ROWID,
                    FIELD_DEDICATION, FIELD_PARSON, FIELD_ADDRESS, FIELD_LONGITUDE, FIELD_lATITUDE, FIELD_SERVICES, FIELD_FETE, FIELD_DIOCESE},
                    FIELD_DIOCESE + " like '%" + input + "%'", null, null, null, null, null);
        }
        if(cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    public void addChurch(String dedication, String parson, double latitude, double longitude, String address, String services, String fete, String diocese){
        ContentValues values = new ContentValues();
        dedication = Character.toString(dedication.charAt(0)).toUpperCase()+dedication.substring(1);
        values.put(FIELD_DEDICATION, dedication);
        values.put(FIELD_PARSON, parson);
        values.put(FIELD_lATITUDE, latitude);
        values.put(FIELD_LONGITUDE, longitude);
        values.put(FIELD_ADDRESS, address);
        values.put(FIELD_SERVICES, services);
        values.put(FIELD_FETE, fete);
        values.put(FIELD_DIOCESE, diocese);
        db.insertOrThrow(TABLE_CHURCHES, null, values);
    }


    public void addDefault(){
        addChurch("Białystok 1", "adsahf", 53.16, 23.20, "Białystok, ul. Duża 2", "Niedziela: 10.00", "20.09", "Białostocko-Gdańska");
        addChurch("Białystok 2", "mnbcvh", 53.15, 23.12, "Białystok, ul. Mała 1", "Niedziela: 10.30", "25.09", "Białostcko-Gdańska");
        addChurch("Gdańsk", "plokpk", 54.37, 18.62, "Gdańsk, ul. Gdanska 12a", "Niedziela: 8.00", "1.10", "Białostocko-Gdańska");
        addChurch("Turkowice - monaster", "Któraś siostra", 50.4, 23.44, "Turkowice, Jedyna droga", "Niedziela: 10.00", "28.09", "Lubelsko-Chełmska");
        addChurch("Hrubieszów", "Proboszcz 1", 50.8, 23.89, "Hrub, ul. Krótka 1", "Niedziela: 10.30", "25.10", "Lubelsko-Chełmska");
        addChurch("Bończa", "Proboszcz 2", 50.9, 23.42, "Bończa, ul. Długa 12, Jedyna droga", "Niedziela: 8.00", "30.09", "Lubelsko-Chełmska");
        addChurch("Wrocław", "adsahf", 51.1, 17.0, "Wrocław, ul. Wszystkich Swiętych 1", "Niedziela: 10.00", "21.09", "Wrocławsko-Szczecińska");
        addChurch("Legnica", "mnbcvh", 51.2, 16.16, "Legnica, ul. Mała 1", "Niedziela: 10.30", "10.10", "Wrocławsko-Szczecińska");
        addChurch("Jelenia Góra", "plokpk", 50.9, 15.7, "Jelenia Góra, ul. Rynek 12a", "Niedziela: 8.00", "28.09", "Wrocławsko-Szczecińska");

    }
}
