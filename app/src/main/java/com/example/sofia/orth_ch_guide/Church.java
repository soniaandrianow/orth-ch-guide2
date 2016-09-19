package com.example.sofia.orth_ch_guide;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sofia on 13.09.2016.
 */
public class Church implements Parcelable{

    public int image;
    public String dedication;
    public String parson;
    public double longitude;
    public double latitude;
    public String address;
    public String services;

    public Church()
    {}

    public Church(int image, String dedication, String parson, double longitude, double latitude, String address, String services) {
        this.image = image;
        this.dedication = dedication;
        this.parson = parson;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.services = services;
    }


    protected Church(Parcel in) {
        image = in.readInt();
        dedication = in.readString();
        parson = in.readString();
        address = in.readString();
        services = in.readString();
        longitude = in.readDouble();
        latitude = in.readDouble();

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
        dest.writeInt(image);
        dest.writeString(dedication);
        dest.writeString(parson);
        dest.writeString(address);
        dest.writeString(services);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }

}
