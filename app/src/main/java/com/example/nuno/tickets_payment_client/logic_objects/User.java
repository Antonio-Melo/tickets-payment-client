package com.example.nuno.tickets_payment_client.logic_objects;

import java.security.PrivateKey;
import java.sql.Date;
import java.util.UUID;

public class User {

    private String name;
    private String email;
    private String nif;
    private String username;
    private String password;
    private String creditCardType;
    private String creditCardNumber;
    private Date creditCardValidity;

    private UUID userUUID;
    private PrivateKey userPrivKey;

    public User() { }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCreditCardType(String creditCardType) {
        this.creditCardType = creditCardType;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public void setCreditCardValidity(Date creditCardValidity) {
        this.creditCardValidity = creditCardValidity;
    }
}
