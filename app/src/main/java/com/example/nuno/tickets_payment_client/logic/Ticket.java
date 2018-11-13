package com.example.nuno.tickets_payment_client.logic;

public class Ticket {

    private String uuid;
    private int number;
    private User owner;
    private Show show;
    private boolean validated;

    public Ticket(String uuid, int number, User owner, Show show) {
        this.uuid = uuid;
        this.number = number;
        this.owner = owner;
        this.show = show;
    }

    public Show getShow() {
        return show;
    }
}
