package com.example.nuno.tickets_payment_client.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.nuno.tickets_payment_client.MainActivity;
import com.example.nuno.tickets_payment_client.MyShowsActivity;
import com.example.nuno.tickets_payment_client.NextShowsActivity;
import com.example.nuno.tickets_payment_client.R;

public class ShowsFragment extends Fragment {

    private MainActivity mainActivity;

    private Button.OnClickListener mOnButtonClickListener
            = new Button.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.next_shows_button:
                    nextActivity(new Intent(getActivity(), NextShowsActivity.class));
                    break;
                case R.id.my_shows_button:
                    nextActivity(new Intent(getActivity(), MyShowsActivity.class));
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shows, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainActivity = (MainActivity) getActivity();

        // click listener on next shows button
        mainActivity.findViewById(R.id.next_shows_button).setOnClickListener(mOnButtonClickListener);

        // click listener on my shows button
        mainActivity.findViewById(R.id.my_shows_button).setOnClickListener(mOnButtonClickListener);
    }

    public void nextActivity(Intent intent) {
        startActivity(intent);
    }
}
