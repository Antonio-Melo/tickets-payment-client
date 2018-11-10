package com.example.nuno.tickets_payment_client.recycler_adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nuno.tickets_payment_client.R;
import com.example.nuno.tickets_payment_client.logic_objects.Show;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MyShowsRecyclerAdapter extends RecyclerView.Adapter<MyShowsRecyclerAdapter.MyShowViewHolder> {

    private ArrayList<Show> ticketsList;

    public MyShowsRecyclerAdapter(ArrayList<Show> ticketsList) {
        this.ticketsList = ticketsList;
    }

    @NonNull
    @Override
    public MyShowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;

        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_shows_item, viewGroup, false);
        MyShowViewHolder vHolder = new MyShowViewHolder(v);

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyShowViewHolder viewHolder, int i) {

        viewHolder.nameText.setText(ticketsList.get(i).getName());
        viewHolder.artistText.setText(ticketsList.get(i).getArtist());

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        viewHolder.dateText.setText(dateFormat.format(ticketsList.get(i).getDate()));
        viewHolder.priceText.setText(Double.toString(ticketsList.get(i).getPrice()) + "€");

    }

    @Override
    public int getItemCount() {
        return ticketsList.size();
    }

    public static class MyShowViewHolder extends RecyclerView.ViewHolder {

        private TextView nameText;
        private TextView artistText;
        private TextView dateText;
        private TextView priceText;

        public MyShowViewHolder(@NonNull View itemView) {
            super(itemView);

            nameText = itemView.findViewById(R.id.my_shows_name_text_view);
            artistText = itemView.findViewById(R.id.my_shows_artist_text_view);
            dateText = itemView.findViewById(R.id.my_shows_date_text_view);
            priceText = itemView.findViewById(R.id.my_shows_price_text_view);

        }
    }

}
