package com.example.nuno.tickets_payment_client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.nuno.tickets_payment_client.logic.Show;

public class TicketActivity extends AppCompatActivity {

    private Show show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        show = getIntent().getExtras().getParcelable("TICKET");

        ((TextView)findViewById(R.id.show_name_text_view)).setText(show.getName());
        ((TextView)findViewById(R.id.show_artist_text_view)).setText(show.getArtist());
        ((TextView)findViewById(R.id.show_date_text_view)).setText(show.getDate());

    }
}
