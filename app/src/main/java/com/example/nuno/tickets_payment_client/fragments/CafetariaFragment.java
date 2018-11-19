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
import android.widget.AdapterView;
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

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;

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

    private static final Pattern VALID_ORDER_QUANTITY =
            Pattern.compile("^([+-]?[1-9]\\d*|0)$");

    public static boolean validateOrderQuantity(String quantity) {
        Matcher matcher = VALID_ORDER_QUANTITY.matcher(quantity);
        return matcher.find();
    }

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
                String input = nOfPopCornEditText.getText().toString();
                int nOfPopCorn = 0;
                if (validateOrderQuantity(input)){
                    nOfPopCorn = Integer.valueOf(nOfPopCornEditText.getText().toString());
                }
                else {
                    nOfPopCornEditText.setError("Only non negative numbers");
                    return;
                }

                input = nOfSandwichEditText.getText().toString();
                int nOfSandwich = 0;
                if (validateOrderQuantity(input)){
                    nOfSandwich = Integer.valueOf(nOfSandwichEditText.getText().toString());
                }
                else {
                    nOfSandwichEditText.setError("Only non negative numbers");
                    return;
                }

                input = nOfCoffeeEditText.getText().toString();
                int nOfCoffee = 0;
                if (validateOrderQuantity(input)){
                    nOfCoffee = Integer.valueOf(nOfCoffeeEditText.getText().toString());
                }
                else {
                    nOfCoffeeEditText.setError("Only non negative numbers");
                    return;
                }

                input = nOfSodaEditText.getText().toString();
                int nOfSoda = 0;
                if (validateOrderQuantity(input)){
                    nOfSoda = Integer.valueOf(nOfSodaEditText.getText().toString());
                }
                else {
                    nOfSodaEditText.setError("Only non negative numbers");
                    return;
                }

                String voucher1Type = spinner1.getSelectedItem().toString();
                JSONArray vouchers = new JSONArray();

                ArrayList<Voucher> voucherTypeSpinner1 = getVoucherByType(voucher1Type);
                if (voucherTypeSpinner1.size() > 0) {
                    vouchers.put(voucherTypeSpinner1.get(0).getUuid());
                }
                else if (!voucher1Type.equals("NONE")) {
                    ((TextView)getActivity().findViewById(R.id.order_error)).setText(R.string.cafetaria_order_error);
                    getActivity().findViewById(R.id.order_error).setVisibility(View.VISIBLE);
                    return;
                }

                String voucher2Type = spinner2.getSelectedItem().toString();
                if (voucher1Type.equals("FIVEPERCENT") && voucher2Type.equals("FIVEPERCENT")) {
                    ((TextView)getActivity().findViewById(R.id.order_error)).setText(R.string.cafetaria_fivepercent_order_error);
                    getActivity().findViewById(R.id.order_error).setVisibility(View.VISIBLE);
                }
                ArrayList<Voucher> voucherTypeSpinner2 = getVoucherByType(voucher2Type);
                if (voucherTypeSpinner2.size() > 0) {
                    vouchers.put(voucherTypeSpinner2.get(0).getUuid());
                }
                else if (!voucher2Type.equals("NONE")){
                    ((TextView)getActivity().findViewById(R.id.order_error)).setText(R.string.cafetaria_order_error);
                    getActivity().findViewById(R.id.order_error).setVisibility(View.VISIBLE);
                    return;
                }

                SharedPreferences sp = getActivity().getSharedPreferences("Login", MODE_PRIVATE);
                User user = MainActivity.getUserSession(sp);

                JSONObject orderJson = new JSONObject();
                try {
                    orderJson.put("uuid", user.getUserUUID().toString());
                    orderJson.put("vouchers", vouchers);
                    API.validateVouchers(CafetariaFragment.this, orderJson);
                    JSONObject orderElements = new JSONObject();
                    orderElements.put("coffee", nOfCoffee);
                    orderElements.put("soda", nOfSoda);
                    orderElements.put("popcorn", nOfPopCorn);
                    orderElements.put("sandwich", nOfSandwich);
                    orderJson.put("order", orderElements);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                KeyStore keyStore = null;
                try {
                    keyStore = KeyStore.getInstance("AndroidKeyStore");
                    keyStore.load(null);
                    KeyStore.Entry entry = keyStore.getEntry(user.getUsername(), null);
                    PrivateKey privateKey = ((KeyStore.PrivateKeyEntry) entry).getPrivateKey();

                    JwtBuilder jws = Jwts.builder();
                    Log.d("JWT", jws.setPayload(orderJson.toString()).signWith(privateKey).compact());
                    API.orderCafetaria(CafetariaFragment.this, jws.setPayload(orderJson.toString()).signWith(privateKey).compact());
                } catch (Exception e) {
                    e.printStackTrace();
                }
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

    public ArrayList<Voucher> getVoucherByType(String type) {
        ArrayList<Voucher> typeVoucher = new ArrayList<>();

        for (Voucher voucher: userVouchers) {
            if (voucher.getType().equals(type)) {
                typeVoucher.add(voucher);
            }
        }

        return typeVoucher;
    }

}
