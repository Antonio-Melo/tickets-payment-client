package com.example.nuno.tickets_payment_client.fragments;

import android.os.Bundle;
import android.security.KeyPairGeneratorSpec;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.nuno.tickets_payment_client.R;
import com.example.nuno.tickets_payment_client.RegisterActivity;
import com.example.nuno.tickets_payment_client.logic_objects.CreditCard;
import com.example.nuno.tickets_payment_client.logic_objects.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.security.auth.x500.X500Principal;

public class RegisterFragment extends Fragment {

    static final int KEY_SIZE = 512;
    static final String ANDROID_KEYSTORE = "AndroidKeyStore";
    static final String KEY_ALGO = "RSA";
    // static final String SIGN_ALGO = "SHA256WithRSA";
    private final String TAG = "RegisterFragment";

    private RegisterActivity registerActivity;

    private Button.OnClickListener mOnButtonClickListener
            = new Button.OnClickListener() {

        @Override
        public void onClick(View v) {
            User user = new User();

            EditText editText;
            String input;
            boolean valid = true;

            editText = registerActivity.findViewById(R.id.register_name_input);
            input = editText.getText().toString();
            if (User.validateName(input)) {
                user.setName(input);
            }
            else {
                editText.setError("Must be a valid name, don't use numbers");
                valid = false;
            }

            editText = registerActivity.findViewById(R.id.register_email_input);
            input = editText.getText().toString();
            if (User.validateEmail(input)) {
                user.setEmail(input);
            }
            else {
                editText.setError("Must be a valid email");
                valid = false;
            }

            editText = registerActivity.findViewById(R.id.register_nif_input);
            input = editText.getText().toString();
            if (User.validateNif(input)) {
                user.setNif(input);
            }
            else {
                editText.setError("Must be 9 numbers");
                valid = false;
            }

            editText = registerActivity.findViewById(R.id.register_username_input);
            input = editText.getText().toString();
            if (User.validateUsername(input)) {
                user.setUsername(input);
            }
            else {
                editText.setError("Must be between 8 and 20 letters/numbers");
                valid = false;
            }

            editText = registerActivity.findViewById(R.id.register_password_input);
            input = editText.getText().toString();
            if (User.validatePassword(input)) {
                user.setPassword(input);
            }
            else {
                editText.setError("Must be grater than 8 characters and have at least a number/letter");
                valid = false;
            }

            CreditCard creditCard = new CreditCard();

            Spinner spinner = registerActivity.findViewById(R.id.register_credit_card_type_spinner);
            creditCard.setType(spinner.getSelectedItem().toString());

            editText = registerActivity.findViewById(R.id.register_credit_card_number_input);
            input = editText.getText().toString();
            if (CreditCard.validateCreditCardNumber(input)) {
                creditCard.setNumber(input);
            }
            else {
                editText.setError("Must be a 16 digit number");
                valid = false;
            }

            spinner = registerActivity.findViewById(R.id.register_credit_card_expiring_month_spinner);
            creditCard.setExpiringMonth(spinner.getSelectedItem().toString());

            spinner = registerActivity.findViewById(R.id.register_credit_card_expiring_year_spinner);
            creditCard.setExpiringYear(spinner.getSelectedItem().toString());

            editText = registerActivity.findViewById(R.id.register_credit_card_cvv_input);
            input = editText.getText().toString();
            if (CreditCard.validateCreditCardCvv(input)) {
                creditCard.setCvv(input);
            }
            else {
                editText.setError("Must be a 3 digit number");
                valid = false;
            }

            /*User user = new User();
            user.setName("Runo Namos");
            user.setEmail("runo@gmail.com");
            user.setNif("123456789");
            user.setUsername("runonamos");
            user.setPassword("runonamos1");

            CreditCard creditCard = new CreditCard();
            creditCard.setNumber("4111111111111111");
            creditCard.setType("VISA");
            creditCard.setCvv("123");
            creditCard.setExpiringMonth("2018");
            creditCard.setExpiringYear("12");*/

            user.setCreditCard(creditCard);

            // Create key pair
            generateAndStoreKeys(user);

            // Call API
            if (valid) {
                Log.d(TAG, "Calling API");
                callApi(user);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        registerActivity = (RegisterActivity)getActivity();

        // click listener on create account button
        registerActivity.findViewById(R.id.register_create_account_button).setOnClickListener(mOnButtonClickListener);
    }

    public void generateAndStoreKeys(User user) {
        try {
            KeyStore ks = KeyStore.getInstance(ANDROID_KEYSTORE);
            ks.load(null);
            KeyStore.Entry entry = ks.getEntry(user.getUsername(), null);
            if (null == null) {
                Calendar start = new GregorianCalendar();
                Calendar end = new GregorianCalendar();
                end.add(Calendar.YEAR, 20);
                KeyPairGenerator kgen = KeyPairGenerator.getInstance(KEY_ALGO, ANDROID_KEYSTORE);
                AlgorithmParameterSpec spec = new KeyPairGeneratorSpec.Builder(registerActivity)
                        .setKeySize(KEY_SIZE)
                        .setAlias(user.getUsername())
                        .setSubject(new X500Principal("CN=" + user.getUsername()))
                        .setSerialNumber(BigInteger.valueOf(12121212))
                        .setStartDate(start.getTime())
                        .setEndDate(end.getTime())
                        .build();
                kgen.initialize(spec);
                KeyPair kp = kgen.generateKeyPair();

                user.setUserPublicKey(new String(Base64.encode(kp.getPublic().getEncoded(), Base64.DEFAULT)));
            }
        }
        catch (Exception ex) {
            Log.d(TAG, ex.getMessage());
        }
    }

    public void callApi(final User user) {

        RequestQueue queue = Volley.newRequestQueue(this.getActivity());
        String url = "http://10.0.2.2:3000/users/signup";

        try {
            final JSONObject jsonBody = new JSONObject();

            jsonBody.put("username", user.getUsername());
            jsonBody.put("name", user.getName());
            jsonBody.put("password", user.getPassword());
            jsonBody.put("nif", user.getNif());
            jsonBody.put("email", user.getEmail());
            jsonBody.put("publicKey", user.getUserPublicKey());

            JSONObject creditCard = new JSONObject();

            creditCard.put("cardType", user.getCreditCard().getType());
            creditCard.put("number", user.getCreditCard().getNumber());
            creditCard.put("cvv", user.getCreditCard().getCvv());
            creditCard.put("expiryMonth", "12");
            creditCard.put("expiryYear", "2018");

            jsonBody.put("creditCard", creditCard);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d(TAG, response.toString());

                    try {
                        Log.d(TAG, response.get("uuid").toString());
                        UUID userUUID = UUID.fromString(response.get("uuid").toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, error.toString());
                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("Content-Type","application/json");
                    return params;
                }

                @Override
                public byte[] getBody() {
                    try {
                        return jsonBody == null ? null : jsonBody.toString().getBytes("utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            };

                queue.add(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
