package com.example.sofia.orth_ch_guide;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Sofia on 13.09.2016.
 */
public class Church implements Parcelable{

    String photos;
    public String dedication;
    public String parson;
    public double longitude;
    public double latitude;
    public String address;
    public String services;
    public String fete;
    public String diocese;
    public String style;
    public int century;
    public String short_history;
    public boolean wooden;


    public Church()
    {}

    public Church(String dedication, String parson, double latitude, double longitude, String address, String services, String fete,String style, int century, String short_history, boolean wooden, String diocese, String photos) {
        this.dedication = dedication;
        this.parson = parson;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.services = services;
        this.fete = fete;
        this.diocese = diocese;
        this.style = style;
        this.century=century;
        this.short_history=short_history;
        this.wooden=wooden;
        this.photos = photos;

    }


    protected Church(Parcel in) {
        dedication = in.readString();
        parson = in.readString();
        address = in.readString();
        services = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        fete = in.readString();
        diocese = in.readString();
        style = in.readString();
        century = in.readInt();
        short_history = in.readString();
        photos = in.readString();

    }

    public static final Creator<Church> CREATOR = new Creator<Church>() {
        @Override
        public Church createFromParcel(Parcel in) {
            return new Church(in);
        }

        @Override
        public Church[] newArray(int size) {
            return new Church[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(dedication);
        dest.writeString(parson);
        dest.writeString(address);
        dest.writeString(services);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeString(fete);
        dest.writeString(diocese);
        dest.writeString(style);
        dest.writeInt(century);
        dest.writeString(short_history);
        dest.writeString(photos);
    }

}
