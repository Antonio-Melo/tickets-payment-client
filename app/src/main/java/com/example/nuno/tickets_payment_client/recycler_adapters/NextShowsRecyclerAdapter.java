package com.example.nuno.tickets_payment_client.recycler_adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.nuno.tickets_payment_client.R;
import com.example.nuno.tickets_payment_client.logic.Show;

import org.json.JSONArray;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class NextShowsRecyclerAdapter extends RecyclerView.Adapter<NextShowsRecyclerAdapter.NextShowViewHolder> {

    private ArrayList<Show> showsList;

    private ArrayList<Show> showsCart;

    public NextShowsRecyclerAdapter(ArrayList<Show> showsList) {
        this.showsList = showsList;
        this.showsCart = new ArrayList<>();
    }

    @NonNull
    @Override
    public NextShowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;

        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_shows_item, viewGroup, false);

        v.setTag(showsList.get(i));

        NextShowsRecyclerAdapter.NextShowViewHolder vHolder = new NextShowsRecyclerAdapter.NextShowViewHolder(v);

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final NextShowViewHolder viewHolder, final int i) {

        viewHolder.nameText.setText(showsList.get(i).getName());
        viewHolder.artistText.setText(showsList.get(i).getArtist());

        viewHolder.dateText.setText(showsList.get(i).getDate());
        viewHolder.priceText.setText(Double.toString(showsList.get(i).getPrice()) + "€");

       /* viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("NEXT SHOWS", "boas");
                Show show = (Show)v.getTag();
                Log.d("NEXT SHOWS", showsList.get(i).getName());
            }
        });*/

        viewHolder.button.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                Show show = showsList.get(i);
                if (showsCart.contains(show)) {
                    showsCart.remove(show);
                    viewHolder.button.setText(R.string.shows_add_to_cart);
                }
                else {
                    showsCart.add(show);
                    viewHolder.button.setText(R.string.shows_added_to_cart);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return showsList.size();
    }

    public ArrayList<Show> getShowsCart() {
        return showsCart;
    }

    public static class NextShowViewHolder extends RecyclerView.ViewHolder {

        private TextView nameText;
        private TextView artistText;
        private TextView dateText;
        private TextView priceText;
        private Button button;

        public NextShowViewHolder(@NonNull View itemView) {
            super(itemView);

            nameText = itemView.findViewById(R.id.my_shows_name_text_view);
            artistText = itemView.findViewById(R.id.my_shows_artist_text_view);
            dateText = itemView.findViewById(R.id.my_shows_date_text_view);
            priceText = itemView.findViewById(R.id.my_shows_price_text_view);
            button = itemView.findViewById(R.id.next_shows_button_add_to_cart);

        }
    }
}
