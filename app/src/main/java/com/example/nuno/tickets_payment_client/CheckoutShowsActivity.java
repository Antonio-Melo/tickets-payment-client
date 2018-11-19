package com.example.nuno.tickets_payment_client;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.nuno.tickets_payment_client.logic.Show;
import com.example.nuno.tickets_payment_client.logic.User;
import com.example.nuno.tickets_payment_client.recycler_adapters.CheckoutShowsRecyclerAdapter;
import com.example.nuno.tickets_payment_client.recycler_adapters.NextShowsRecyclerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class CheckoutShowsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CheckoutShowsRecyclerAdapter adapter;
    private ArrayList<Show> showsToBuy = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_shows);

        recyclerView = findViewById(R.id.checkout_shows_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        SharedPreferences sp = getSharedPreferences("Login", MODE_PRIVATE);
        final User user = MainActivity.getUserSession(sp);

        showsToBuy = getIntent().getParcelableArrayListExtra("CART_SHOWS");

        adapter = new CheckoutShowsRecyclerAdapter(showsToBuy);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab_checkout_shows);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (adapter.getNumberOfQuantitiesFilled() < adapter.getCheckoutShows().size())
                    return;

                try {
                    JSONObject order = new JSONObject();
                    order.put("uuid", user.getUserUUID());
                    // Call API
                    JSONObject tickets = new JSONObject();
                    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                    SimpleDateFormat outputFormat= new SimpleDateFormat("dd/MM/yyyy' | 'HH:mm");
                    int index = 0;
                    for (Show show: showsToBuy) {
                        JSONArray showArray = new JSONArray();
                        showArray.put(inputFormat.format(outputFormat.parse(show.getDate())));
                        showArray.put(adapter.getQuantities().get(index));
                        tickets.put(show.getName(), showArray);
                        index++;
                    }
                    order.put("tickets", tickets);

                    Log.d("CHECKOUT", order.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Snackbar snackbar = Snackbar.make(CheckoutShowsActivity.this.findViewById(R.id.checkout_coordinator_layout),
                        R.string.checkout_shows_success_buy, Snackbar.LENGTH_LONG);

                snackbar.show();

                /*Intent intent = new Intent(CheckoutShowsActivity.this, NextShowsActivity.class);
                startActivity(intent);*/
            }
        });
    }
}
