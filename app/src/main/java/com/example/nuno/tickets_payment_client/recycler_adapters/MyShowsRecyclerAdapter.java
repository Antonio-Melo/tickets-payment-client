package com.example.nuno.tickets_payment_client.recycler_adapters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nuno.tickets_payment_client.R;
import com.example.nuno.tickets_payment_client.TicketActivity;
import com.example.nuno.tickets_payment_client.fragments.TicketsFragment;
import com.example.nuno.tickets_payment_client.logic.Ticket;

import java.util.ArrayList;

public class MyShowsRecyclerAdapter extends RecyclerView.Adapter<MyShowsRecyclerAdapter.MyShowViewHolder> {

    private ArrayList<Ticket> ticketList;

    private TicketsFragment ticketsFragment;

    public MyShowsRecyclerAdapter(ArrayList<Ticket> ticketList, TicketsFragment ticketsFragment) {
        this.ticketList = ticketList;
        this.ticketsFragment = ticketsFragment;
    }

    @NonNull
    @Override
    public MyShowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;

        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_my_tickets, viewGroup, false);
        MyShowViewHolder vHolder = new MyShowViewHolder(v);

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyShowViewHolder viewHolder, final int i) {

        viewHolder.nameText.setText(ticketList.get(i).getShow().getName());
        viewHolder.artistText.setText(ticketList.get(i).getShow().getArtist());
        viewHolder.dateText.setText(ticketList.get(i).getShow().getDate());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ticketsFragment.getActivity(), TicketActivity.class);
                intent.putExtra("USER_TICKET", ticketList.get(i));
                ticketsFragment.getActivity().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ticketList.size();
    }

    public static class MyShowViewHolder extends RecyclerView.ViewHolder {

        private TextView nameText;
        private TextView artistText;
        private TextView dateText;

        public MyShowViewHolder(@NonNull View itemView) {
            super(itemView);

            nameText = itemView.findViewById(R.id.my_shows_name_text_view);
            artistText = itemView.findViewById(R.id.my_shows_artist_text_view);
            dateText = itemView.findViewById(R.id.my_shows_date_text_view);

        }
    }

}
