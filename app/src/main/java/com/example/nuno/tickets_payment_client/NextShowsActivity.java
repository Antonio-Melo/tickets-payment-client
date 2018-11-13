package com.example.nuno.tickets_payment_client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.nuno.tickets_payment_client.logic.Show;
import com.example.nuno.tickets_payment_client.recycler_adapters.NextShowsRecyclerAdapter;

import java.util.ArrayList;
import java.util.Date;

public class NextShowsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private NextShowsRecyclerAdapter adapter;
    private ArrayList<Show> nextShows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_shows);

        recyclerView = findViewById(R.id.next_shows_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        // data
        nextShows = new ArrayList<>();
        nextShows.add(new Show("Outra vez no Porto","Lartiste", 30.14, new Date(2019, 4, 4)));
        nextShows.add(new Show("Maluma baby","Maluma", 30.14, new Date(2019, 4, 4)));
        nextShows.add(new Show("J Balvin, baila reggaeton","J Balvin", 30.14, new Date(2019, 4, 4)));
        nextShows.add(new Show("Vira o disco e toca o mesmo","Toy", 30.14, new Date(2019, 4, 4)));
        adapter = new NextShowsRecyclerAdapter(nextShows);
        recyclerView.setAdapter(adapter);
    }
}
