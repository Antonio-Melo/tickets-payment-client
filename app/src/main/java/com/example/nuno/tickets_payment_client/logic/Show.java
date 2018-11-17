package com.example.nuno.tickets_payment_client.logic;

import android.os.Parcel;
import android.os.Parcelable;

public class Show implements Parcelable {

    private String id;
    private String name;
    private String artist;
    private double price;
    private String date;

    public Show(String i, String n, String a, double p, String d) {
        this.id = i;
        this.name = n;
        this.artist = a;
        this.price = p;
        this.date = d;
    }

    protected Show(Parcel in) {
        id = in.readString();
        name = in.readString();
        artist = in.readString();
        price = in.readDouble();
        date = in.readString();
    }

    public static final Creator<Show> CREATOR = new Creator<Show>() {
        @Override
        public Show createFromParcel(Parcel in) {
            return new Show(in);
        }

        @Override
        public Show[] newArray(int size) {
            return new Show[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public double getPrice() {
        return price;
    }

    public String getDate() {
        return date;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(artist);
        dest.writeString(Double.toString(price));
        dest.writeString(date);
    }
}
