package com.example.nuno.tickets_payment_client.logic_objects;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreditCard {

    private String type;
    private String number;
    private String validity;
    private String cvv;

    private static final Pattern VALID_CREDIT_CARD_VALIDITY_DATE_REGEX =
            Pattern.compile("(((0)[0-9])|((1)[0-2]))(\\/)\\d{4}$");

    private static final Pattern VALID_CREDIT_CARD_NUMBER_REGEX =
            Pattern.compile("^\\d{16}$");

    private static final Pattern VALID_CREDIT_CARD_CVV_REGEX =
            Pattern.compile("^\\d{3}$");

    public CreditCard () {}

    public static boolean validateCreditCardValidityDate(String creditCardValidityDateStr) {
        Matcher matcher = VALID_CREDIT_CARD_VALIDITY_DATE_REGEX.matcher(creditCardValidityDateStr);
        return matcher.find();
    }

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

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}
