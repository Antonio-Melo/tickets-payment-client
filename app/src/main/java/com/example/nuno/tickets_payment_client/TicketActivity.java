package com.example.nuno.tickets_payment_client;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nuno.tickets_payment_client.logic.Ticket;
import com.example.nuno.tickets_payment_client.logic.User;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import org.json.JSONException;
import org.json.JSONObject;

public class TicketActivity extends AppCompatActivity {

    public final static int WHITE = 0xFFFFFFFF;
    public final static int BLACK = 0xFF000000;
    public final static int WIDTH = 800;
    public final static int HEIGHT = 800;

    private Ticket ticket;

    private ImageView qrCodeImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        ticket = getIntent().getExtras().getParcelable("USER_TICKET");

        ((TextView)findViewById(R.id.show_name_text_view)).setText(ticket.getShow().getName());
        ((TextView)findViewById(R.id.show_artist_text_view)).setText(ticket.getShow().getArtist());
        ((TextView)findViewById(R.id.show_date_text_view)).setText(ticket.getShow().getDate());
        ((TextView)findViewById(R.id.show_number_of_tickets)).setText(String.valueOf(ticket.getNumberOfTickets()));

        try {
            SharedPreferences sp = this.getSharedPreferences("Login", MODE_PRIVATE);
            User user = MainActivity.getUserSession(sp);
            JSONObject valueToQRCode = new JSONObject();
            valueToQRCode.put("uuid", user.getUserUUID().toString());
            valueToQRCode.put("tickets", ticket.getTicketsUuids());

            qrCodeImageView = findViewById(R.id.ticket_qr_code);

            Bitmap bitmap = encodeAsBitmap(valueToQRCode.toString());
            qrCodeImageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    Bitmap encodeAsBitmap(String str) throws WriterException {
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, null);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }

        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }
}
