package com.example.nuno.tickets_payment_client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.nuno.tickets_payment_client.logic.Show;

import java.util.ArrayList;

public class CheckoutShowsActivity extends AppCompatActivity {

    private ArrayList<Show> showsToBuy = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_shows);

        showsToBuy = getIntent().getParcelableArrayListExtra("CART_SHOWS");

    }
}
