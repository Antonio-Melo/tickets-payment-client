package com.example.nuno.tickets_payment_client.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nuno.tickets_payment_client.MainActivity;
import com.example.nuno.tickets_payment_client.R;
import com.example.nuno.tickets_payment_client.logic.User;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {

    private User user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public static HomeFragment newInstance(User user) {
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setUser(user);
        return homeFragment;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        user = new User();
        SharedPreferences sp = this.getActivity().getSharedPreferences("Login", MODE_PRIVATE);
        if (sp.getBoolean("loggedIn", false)) user = MainActivity.getUserSession(sp);

        ((TextView)view.findViewById(R.id.home_text_view)).setText(user.getName());
    }
}
