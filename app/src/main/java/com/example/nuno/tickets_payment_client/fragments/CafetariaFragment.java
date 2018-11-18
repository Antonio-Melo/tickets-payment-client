package com.example.nuno.tickets_payment_client.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.example.nuno.tickets_payment_client.logic.User;
import com.example.nuno.tickets_payment_client.logic.Voucher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class CafetariaFragment extends Fragment {

    private ArrayList<Voucher> userVouchers;

    private int nOfFoodVouchers = 0;
    private int nOfDrinkVouchers = 0;
    private int nOfFivePercentVouchers = 0;

    private EditText nOfPopCornEditText;
    private EditText nOfSandwichEditText;
    private EditText nOfCoffeeEditText;
    private EditText nOfSodaEditText;
    private Spinner spinner1;
    private Spinner spinner2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cafetaria, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nOfPopCornEditText = getActivity().findViewById(R.id.cafetaria_quantity_of_popcorn_holder);
        nOfSandwichEditText = getActivity().findViewById(R.id.cafetaria_quantity_of_sandwich_holder);
        nOfCoffeeEditText = getActivity().findViewById(R.id.cafetaria_quantity_of_coffee_holder);
        nOfSodaEditText = getActivity().findViewById(R.id.cafetaria_quantity_of_soda_holder);
        spinner1 = getActivity().findViewById(R.id.cafetaria_spinner_1);
        spinner2 = getActivity().findViewById(R.id.cafetaria_spinner_2);

        SharedPreferences sp = this.getActivity().getSharedPreferences("Login", MODE_PRIVATE);
        User user = MainActivity.getUserSession(sp);

        userVouchers = new ArrayList<>();

        API.getUserVouchers(this, user.getUserUUID().toString());

        getActivity().findViewById(R.id.cafetaria_submit_button).setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                int nOfPopCorn = Integer.valueOf(nOfPopCornEditText.getText().toString());
                int nOfSandwich = Integer.valueOf(nOfSandwichEditText.getText().toString());
                int nOfCoffee = Integer.valueOf(nOfCoffeeEditText.getText().toString());
                int nOfSoda= Integer.valueOf(nOfSodaEditText.getText().toString());
                String voucher1Type = spinner1.getSelectedItem().toString();
                String voucher2Type = spinner2.getSelectedItem().toString();
                Log.d("CAFETARIA", "chamando a api");
            }
        });
    }

    public void setUserVouchers(JSONArray userVouchersJSON, String ownerUuid) {

        for (int index = 0; index < userVouchersJSON.length(); index++){
            try {

                JSONObject voucherJSON = userVouchersJSON.getJSONObject(index);
                userVouchers.add(new Voucher(voucherJSON.getString("uuid"), ownerUuid,
                        voucherJSON.getString("type"), voucherJSON.getBoolean("validated")));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        nOfDrinkVouchers = getNumberOfVoucherPerType("DRINK");
        ((TextView)getActivity().findViewById(R.id.cafetaria_number_of_drink_vouchers)).setText("You have " + nOfDrinkVouchers + " drink vouchers");
        nOfFoodVouchers = getNumberOfVoucherPerType("FOOD");
        ((TextView)getActivity().findViewById(R.id.cafetaria_number_of_food_vouchers)).setText("You have " + nOfFoodVouchers + " food vouchers");
        nOfFivePercentVouchers = getNumberOfVoucherPerType("FIVEPERCENTE");
        ((TextView)getActivity().findViewById(R.id.cafetaria_number_of_fivepercent_vouchers)).setText("You have " + nOfFivePercentVouchers + " 5% vouchers");

    }

    public int getNumberOfVoucherPerType(String type) {
        int numberOfVouchers = 0;
        for (Voucher voucher: userVouchers) {
            if (voucher.getType().equals(type)) numberOfVouchers++;
        }

        return numberOfVouchers;
    }
}
