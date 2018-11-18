package com.example.nuno.tickets_payment_client.logic;

public class Transaction {

    private String type;
    private double amount;
    private String ownerUuid;

    public Transaction(String type, double amount, String ownerUuid) {
        this.type = type;
        this.amount = amount;
        this.ownerUuid = ownerUuid;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }
}
