package com.example.nuno.tickets_payment_client.recycler_adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.nuno.tickets_payment_client.R;
import com.example.nuno.tickets_payment_client.logic.Show;

import java.util.ArrayList;

public class CheckoutShowsRecyclerAdapter extends RecyclerView.Adapter<CheckoutShowsRecyclerAdapter.CheckoutShowViewHolder> {

    private ArrayList<Show> checkoutShows;
    private int numberOfQuantitiesFilled = 0;

    public CheckoutShowsRecyclerAdapter(ArrayList<Show> checkoutShows) {
        this.checkoutShows = checkoutShows;
    }

    @NonNull
    @Override
    public CheckoutShowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;

        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_checkout_shows, viewGroup, false);

        CheckoutShowsRecyclerAdapter.CheckoutShowViewHolder vHolder = new CheckoutShowsRecyclerAdapter.CheckoutShowViewHolder(v);

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CheckoutShowViewHolder viewHolder, int i) {
        viewHolder.nameText.setText(checkoutShows.get(i).getName());
        viewHolder.artistText.setText(checkoutShows.get(i).getArtist());

        viewHolder.dateText.setText(checkoutShows.get(i).getDate());
        viewHolder.priceText.setText(Double.toString(checkoutShows.get(i).getPrice()) + "€");

        viewHolder.quantityEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                numberOfQuantitiesFilled++;
            }
        });
    }

    public ArrayList<Show> getCheckoutShows() {
        return checkoutShows;
    }

    public int getNumberOfQuantitiesFilled() {
        return numberOfQuantitiesFilled;
    }

    @Override
    public int getItemCount() {
        return checkoutShows.size();
    }

    public static class CheckoutShowViewHolder extends RecyclerView.ViewHolder {

        private TextView nameText;
        private TextView artistText;
        private TextView dateText;
        private TextView priceText;
        private EditText quantityEditText;

        public CheckoutShowViewHolder(@NonNull View itemView) {
            super(itemView);

            nameText = itemView.findViewById(R.id.checkout_shows_name_text_view);
            artistText = itemView.findViewById(R.id.checkout_shows_artist_text_view);
            dateText = itemView.findViewById(R.id.checkout_shows_date_text_view);
            priceText = itemView.findViewById(R.id.checkout_shows_price_text_view);
            quantityEditText = itemView.findViewById(R.id.checkout_shows_quantity);
        }
    }
}
