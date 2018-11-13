package com.example.nuno.tickets_payment_client.logic;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {

    private String name;
    private String email;
    private String nif;
    private String username;
    private String password;

    private CreditCard creditCard;

    private UUID userUUID;
    private String userPublicKey;

    private static final Pattern VALID_NAME_REGEX =
            Pattern.compile("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$");

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$", Pattern.CASE_INSENSITIVE);

    private static final Pattern VALID_NIF_REGEX =
            Pattern.compile("^[0-9]{9}$");

    private static final Pattern VALID_USERNAME_REGEX =
            Pattern.compile("^(?=.{8,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$");

    private static final Pattern VALID_PASSWORD_REGEX =
            Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");

    public User() { }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
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

    public void setUserPublicKey(String userPublicKey) {
        this.userPublicKey = "-----BEGIN PUBLIC KEY-----\n" + userPublicKey + "-----END PUBLIC KEY-----";
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public void setUserUUID(UUID userUUID) {
        this.userUUID = userUUID;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getNif() {
        return nif;
    }

    public String getPassword() {
        return password;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public UUID getUserUUID() {
        return userUUID;
    }

    public String getUserPublicKey() {
        return userPublicKey;
    }

    public static boolean validateName(String nameStr) {
        Matcher matcher = VALID_NAME_REGEX.matcher(nameStr);
        return matcher.find();
    }

    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    public static boolean validateNif(String nifStr) {
        Matcher matcher = VALID_NIF_REGEX.matcher(nifStr);
        return matcher.find();
    }

    public static boolean validateUsername(String usernameStr) {
        Matcher matcher = VALID_USERNAME_REGEX.matcher(usernameStr);
        return matcher.find();
    }

    public static boolean validatePassword(String passwordStr) {
        Matcher matcher = VALID_PASSWORD_REGEX.matcher(passwordStr);
        return matcher.find();
    }
}
