package com.example.nuno.tickets_payment_client.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nuno.tickets_payment_client.MainActivity;
import com.example.nuno.tickets_payment_client.R;
import com.example.nuno.tickets_payment_client.logic.API;
import com.example.nuno.tickets_payment_client.logic.Show;
import com.example.nuno.tickets_payment_client.logic.Ticket;
import com.example.nuno.tickets_payment_client.logic.User;
import com.example.nuno.tickets_payment_client.recycler_adapters.MyShowsRecyclerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class TicketsFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MyShowsRecyclerAdapter adapter;
    private ArrayList<Ticket> userTickets;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tickets, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = getActivity().findViewById(R.id.my_shows_recycler_view);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        SharedPreferences sp = this.getActivity().getSharedPreferences("Login", MODE_PRIVATE);
        User user = MainActivity.getUserSession(sp);
        Log.d("TICKETS", user.getUserUUID().toString());

        API.getUserTickets(this, user.getUserUUID().toString());

        userTickets = new ArrayList<>();
        adapter = new MyShowsRecyclerAdapter(userTickets, this);
        recyclerView.setAdapter(adapter);
    }

    public void setUserTickets(JSONArray userTicketsJSON) {
        //this.userTickets = userTickets;
        Log.d("TICKETS", userTicketsJSON.toString());

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat outputFormat= new SimpleDateFormat("dd/MM/yyyy' | 'HH:mm");
        for (int index = 0; index < userTicketsJSON.length(); index++){
            try {

                JSONObject ticketJSON = userTicketsJSON.getJSONObject(index);
                Show show = new Show(ticketJSON.getString("showId"), ticketJSON.getString("showName"),
                        ticketJSON.getString("artist"), ticketJSON.getDouble("price"),
                        outputFormat.format(inputFormat.parse(ticketJSON.getString("date"))));

                JSONArray ticketsUUIDJSON = ticketJSON.getJSONArray("tickets");
                ArrayList<String> ticketsUUID = new ArrayList<>();
                for (int i = 0; i < ticketsUUIDJSON.length(); i++) {
                    ticketsUUID.add(ticketsUUIDJSON.getJSONObject(i).getString("uuid"));
                }

                userTickets.add(new Ticket(ticketsUUID, ticketJSON.getInt("nrTickets"), show));


            } catch (JSONException | ParseException e) {
                e.printStackTrace();
            }
        }

        adapter.notifyDataSetChanged();
    }

    public ArrayList<Ticket> getUserTickets() {
        return userTickets;
    }
}
