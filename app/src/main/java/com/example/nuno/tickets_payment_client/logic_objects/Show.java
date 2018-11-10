package com.example.nuno.tickets_payment_client.logic_objects;

import java.util.Date;

public class Show {

    private String name;
    private String artist;
    private double price;
    private Date date;

    public Show(String n, String a, double p, Date d) {
        this.name = n;
        this.artist = a;
        this.price = p;
        this.date = d;
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

    public Date getDate() {
        return date;
    }
}
