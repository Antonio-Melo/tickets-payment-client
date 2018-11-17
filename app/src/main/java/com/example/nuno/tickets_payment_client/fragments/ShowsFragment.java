package com.example.nuno.tickets_payment_client.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nuno.tickets_payment_client.CheckoutShowsActivity;
import com.example.nuno.tickets_payment_client.R;
import com.example.nuno.tickets_payment_client.logic.API;
import com.example.nuno.tickets_payment_client.logic.Show;
import com.example.nuno.tickets_payment_client.recycler_adapters.NextShowsRecyclerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ShowsFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private NextShowsRecyclerAdapter adapter;
    private ArrayList<Show> nextShows;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shows, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        recyclerView = getActivity().findViewById(R.id.next_shows_recycler_view);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        nextShows = new ArrayList<>();

        API.getShows(this);

        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowsFragment.this.getActivity(), CheckoutShowsActivity.class);
                intent.putParcelableArrayListExtra("CART_SHOWS", adapter.getShowsCart());
                startActivity(intent);
            }
        });

        adapter = new NextShowsRecyclerAdapter(nextShows, fab);
        recyclerView.setAdapter(adapter);
    }

    public void setNextShows(JSONArray nextShowsJSON) {

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat outputFormat= new SimpleDateFormat("dd/MM/yyyy' | 'HH:mm");
        for (int index = 0; index < nextShowsJSON.length(); index++){
            try {

                JSONObject show = nextShowsJSON.getJSONObject(index);
                nextShows.add(new Show(show.getString("_id"), show.getString("name"), show.getString("artist"),
                        Double.parseDouble(show.getString("price")),
                        outputFormat.format(inputFormat.parse(show.getString("date")))));
            } catch (JSONException | ParseException e) {
                e.printStackTrace();
            }
        }

        adapter.notifyDataSetChanged();
    }
}
