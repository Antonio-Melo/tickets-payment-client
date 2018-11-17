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
import com.example.nuno.tickets_payment_client.logic.Show;

import java.util.ArrayList;

public class MyShowsRecyclerAdapter extends RecyclerView.Adapter<MyShowsRecyclerAdapter.MyShowViewHolder> {

    private ArrayList<Show> showsList;

    private TicketsFragment ticketsFragment;

    public MyShowsRecyclerAdapter(ArrayList<Show> showsList, TicketsFragment ticketsFragment) {
        this.showsList = showsList;
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

        viewHolder.nameText.setText(showsList.get(i).getName());
        viewHolder.artistText.setText(showsList.get(i).getArtist());
        viewHolder.dateText.setText(showsList.get(i).getDate());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("MY SHOWS", showsList.get(i).getName());
                Log.d("MY SHOWS", "A trocar de actividade");
                Intent intent = new Intent(ticketsFragment.getActivity(), TicketActivity.class);
                //intent.putParcelableArrayListExtra("CART_SHOWS", adapter.getShowsCart());
                intent.putExtra("TICKET", showsList.get(i));
                ticketsFragment.getActivity().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return showsList.size();
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
