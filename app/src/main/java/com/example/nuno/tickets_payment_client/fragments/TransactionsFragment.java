package com.example.nuno.tickets_payment_client.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.nuno.tickets_payment_client.MainActivity;
import com.example.nuno.tickets_payment_client.R;
import com.example.nuno.tickets_payment_client.logic.API;
import com.example.nuno.tickets_payment_client.logic.Transaction;
import com.example.nuno.tickets_payment_client.logic.User;
import com.example.nuno.tickets_payment_client.logic.Voucher;
import com.example.nuno.tickets_payment_client.recycler_adapters.TransactionsRecyclerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class TransactionsFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TransactionsRecyclerAdapter adapter;
    private ArrayList<Transaction> userTransactions;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_transactions, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = getActivity().findViewById(R.id.my_shows_recycler_view);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        SharedPreferences sp = this.getActivity().getSharedPreferences("Login", MODE_PRIVATE);
        User user = MainActivity.getUserSession(sp);

        API.getUserTransactions(this, user.getUserUUID().toString());

        userTransactions = new ArrayList<>();
        adapter = new TransactionsRecyclerAdapter(userTransactions);
        recyclerView.setAdapter(adapter);
    }

    public void setUserTransactions(JSONArray userTransactionsJSON, String ownerUuid) {

        for (int index = 0; index < userTransactionsJSON.length(); index++){
            try {

                JSONObject transactionJSON = userTransactionsJSON.getJSONObject(index);
                userTransactions.add(new Transaction(transactionJSON.getString("type"),
                        transactionJSON.getDouble("amount"), ownerUuid));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        adapter.notifyDataSetChanged();
    }
}
