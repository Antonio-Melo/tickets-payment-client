package com.example.nuno.tickets_payment_client.logic;

public class Voucher {

    private String uuid;
    private String ownerUuid;
    private String type;
    private boolean validated;

    public Voucher(String uuid, String ownerUuid, String type, boolean validated) {
        this.uuid = uuid;
        this.ownerUuid = ownerUuid;
        this.type = type;
        this.validated = validated;
    }

    public String getType() {
        return type;
    }
}
