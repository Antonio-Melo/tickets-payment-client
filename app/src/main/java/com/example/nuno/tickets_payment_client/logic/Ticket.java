package com.example.nuno.tickets_payment_client.logic;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Ticket implements Parcelable {

    private ArrayList<String> ticketsUuids;
    private int numberOfTickets;
    private Show show;
    private boolean validated = false;

    public Ticket(ArrayList<String> uuid, int numberOfTickets, Show show) {
        this.ticketsUuids = uuid;
        this.numberOfTickets = numberOfTickets;
        this.show = show;
    }

    protected Ticket(Parcel in) {
        ticketsUuids = (ArrayList<String>) in.readSerializable();
        numberOfTickets = in.readInt();
        show = in.readParcelable(Show.class.getClassLoader());
        validated = in.readByte() != 0;
    }

    public static final Creator<Ticket> CREATOR = new Creator<Ticket>() {
        @Override
        public Ticket createFromParcel(Parcel in) {
            return new Ticket(in);
        }

        @Override
        public Ticket[] newArray(int size) {
            return new Ticket[size];
        }
    };

    public Show getShow() {
        return show;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public ArrayList<String> getTicketsUuids() {
        return ticketsUuids;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(ticketsUuids);
        dest.writeInt(numberOfTickets);
        dest.writeParcelable(show, 0);
        dest.writeByte((byte) (validated ? 1 : 0));
    }
}
