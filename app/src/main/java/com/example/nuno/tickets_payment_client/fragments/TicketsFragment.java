package com.example.nuno.tickets_payment_client.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nuno.tickets_payment_client.R;
import com.example.nuno.tickets_payment_client.logic.Show;
import com.example.nuno.tickets_payment_client.recycler_adapters.MyShowsRecyclerAdapter;

import java.util.ArrayList;

public class TicketsFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MyShowsRecyclerAdapter adapter;
    private ArrayList<Show> userShows;

    /*private Button.OnClickListener mOnButtonClickListener
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
    };*/

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tickets, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       /* mainActivity = (MainActivity) getActivity();

        // click listener on next shows button
        mainActivity.findViewById(R.id.next_shows_button).setOnClickListener(mOnButtonClickListener);

        // click listener on my shows button
        mainActivity.findViewById(R.id.my_shows_button).setOnClickListener(mOnButtonClickListener);*/

        recyclerView = getActivity().findViewById(R.id.my_shows_recycler_view);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        // data
        userShows = new ArrayList<>();
        userShows.add(new Show("1","Outra vez no Porto","Lartiste", 30.14, "04/04/2019 | 20:00"));
        userShows.add(new Show("2","Maluma baby","Maluma", 30.14, "04/04/2019 | 20:00"));
        userShows.add(new Show("3","J Balvin, baila reggaeton","J Balvin", 30.14, "04/04/2019 | 20:00"));
        userShows.add(new Show("4","Vira o disco e toca o mesmo","Toy", 30.14, "04/04/2019 | 20:00"));
        adapter = new MyShowsRecyclerAdapter(userShows);
        recyclerView.setAdapter(adapter);
    }
}
