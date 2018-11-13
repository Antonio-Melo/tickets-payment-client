package com.example.nuno.tickets_payment_client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.nuno.tickets_payment_client.logic.Show;
import com.example.nuno.tickets_payment_client.recycler_adapters.MyShowsRecyclerAdapter;

import java.util.ArrayList;
import java.util.Date;

public class MyShowsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MyShowsRecyclerAdapter adapter;
    private ArrayList<Show> userShows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_shows);

        recyclerView = findViewById(R.id.my_shows_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        // data
        userShows = new ArrayList<>();
        userShows.add(new Show("Outra vez no Porto","Lartiste", 30.14, new Date(2019, 4, 4)));
        userShows.add(new Show("Maluma baby","Maluma", 30.14, new Date(2019, 4, 4)));
        userShows.add(new Show("J Balvin, baila reggaeton","J Balvin", 30.14, new Date(2019, 4, 4)));
        userShows.add(new Show("Vira o disco e toca o mesmo","Toy", 30.14, new Date(2019, 4, 4)));
        adapter = new MyShowsRecyclerAdapter(userShows);
        recyclerView.setAdapter(adapter);

    }


}
