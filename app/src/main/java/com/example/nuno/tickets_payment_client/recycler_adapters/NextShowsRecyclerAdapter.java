package com.example.nuno.tickets_payment_client.recycler_adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nuno.tickets_payment_client.R;
import com.example.nuno.tickets_payment_client.logic.Show;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class NextShowsRecyclerAdapter extends RecyclerView.Adapter<NextShowsRecyclerAdapter.NextShowViewHolder> {

    private ArrayList<Show> showsList;

    public NextShowsRecyclerAdapter(ArrayList<Show> showsList) {
        this.showsList = showsList;
    }

    @NonNull
    @Override
    public NextShowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;

        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_shows_item, viewGroup, false);
        NextShowsRecyclerAdapter.NextShowViewHolder vHolder = new NextShowsRecyclerAdapter.NextShowViewHolder(v);

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NextShowViewHolder viewHolder, int i) {

        viewHolder.nameText.setText(showsList.get(i).getName());
        viewHolder.artistText.setText(showsList.get(i).getArtist());

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        viewHolder.dateText.setText(dateFormat.format(showsList.get(i).getDate()));
        viewHolder.priceText.setText(Double.toString(showsList.get(i).getPrice()) + "€");

    }

    @Override
    public int getItemCount() {
        return showsList.size();
    }

    public static class NextShowViewHolder extends RecyclerView.ViewHolder {

        private TextView nameText;
        private TextView artistText;
        private TextView dateText;
        private TextView priceText;

        public NextShowViewHolder(@NonNull View itemView) {
            super(itemView);

            nameText = itemView.findViewById(R.id.my_shows_name_text_view);
            artistText = itemView.findViewById(R.id.my_shows_artist_text_view);
            dateText = itemView.findViewById(R.id.my_shows_date_text_view);
            priceText = itemView.findViewById(R.id.my_shows_price_text_view);

        }
    }
}
