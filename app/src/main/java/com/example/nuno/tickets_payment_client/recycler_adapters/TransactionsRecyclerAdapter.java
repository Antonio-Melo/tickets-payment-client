package com.example.nuno.tickets_payment_client.recycler_adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nuno.tickets_payment_client.R;
import com.example.nuno.tickets_payment_client.logic.Transaction;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class TransactionsRecyclerAdapter extends RecyclerView.Adapter<TransactionsRecyclerAdapter.TransactionsViewHolder> {

    private ArrayList<Transaction> usersTransactions;

    public TransactionsRecyclerAdapter(ArrayList<Transaction> usersTransactions) {
        this.usersTransactions = usersTransactions;
    }

    @NonNull
    @Override
    public TransactionsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;

        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_transactions, viewGroup, false);
        TransactionsViewHolder vHolder = new TransactionsViewHolder(v);

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionsViewHolder viewHolder, int i) {
        viewHolder.typeText.setText(usersTransactions.get(i).getType() + " Transaction Type");
        viewHolder.amountText.setText("Amount " + usersTransactions.get(i).getAmount());
    }

    @Override
    public int getItemCount() {
        return usersTransactions.size();
    }

    public static class TransactionsViewHolder extends RecyclerView.ViewHolder {

        private TextView typeText;
        private TextView amountText;

        public TransactionsViewHolder(@NonNull View itemView) {
            super(itemView);

            typeText =  itemView.findViewById(R.id.transaction_fragment_type);
            amountText =  itemView.findViewById(R.id.transaction_fragment_amount);
        }
    }

}
