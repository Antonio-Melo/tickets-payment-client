package com.example.nuno.tickets_payment_client.logic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreditCard {

    private String type;
    private String number;
    private String expiringMonth;
    private String expiringYear;
    private String cvv;

    private static final Pattern VALID_CREDIT_CARD_NUMBER_REGEX =
            Pattern.compile("^\\d{16}$");

    private static final Pattern VALID_CREDIT_CARD_CVV_REGEX =
            Pattern.compile("^\\d{3}$");

    public CreditCard () {}

    public static boolean validateCreditCardNumber(String creditCardNumberStr) {
        Matcher matcher = VALID_CREDIT_CARD_NUMBER_REGEX.matcher(creditCardNumberStr);
        return matcher.find();
    }

    public static boolean validateCreditCardCvv(String creditCardCvvStr) {
        Matcher matcher = VALID_CREDIT_CARD_CVV_REGEX.matcher(creditCardCvvStr);
        return matcher.find();
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getType() {
        return type;
    }

    public String getNumber() {
        return number;
    }

    public void setExpiringMonth(String expiringMonth) {
        this.expiringMonth = expiringMonth;
    }

    public void setExpiringYear(String expiringYear) {
        this.expiringYear = expiringYear;
    }

    public String getValidityMonth() {
        String[] date = toString().split("/");
        return date[0];
    }

    public String getValidityYear(){
        String[] date = toString().split("/");
        return date[1];
    }

    public String getCvv() {
        return cvv;
    }
}
