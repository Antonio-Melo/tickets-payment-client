package com.example.nuno.tickets_payment_client.logic;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.nuno.tickets_payment_client.MainActivity;
import com.example.nuno.tickets_payment_client.fragments.ShowsFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class API {

    private static final String TAG = "API";
    private static final String EMULATOR_IP = "10.0.2.2";
    private static final String LOCAL_IP_ADDRESS = "10.227.155.2";
    private static String server_ip = LOCAL_IP_ADDRESS;

    public static void getShows(final ShowsFragment showsFragment){

        RequestQueue queue = Volley.newRequestQueue(showsFragment.getContext());
        String url = "http://" + server_ip + ":3000/shows/";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());
                showsFragment.setNextShows(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Getting shows error");
            }
        });

        queue.add(jsonArrayRequest);
    }

    public static void register(final Context context, final User user) {

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://" + server_ip + ":3000/users/signup";

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
            creditCard.put("expiryMonth", user.getCreditCard().getExpiringMonth());
            creditCard.put("expiryYear", user.getCreditCard().getExpiringYear());

            jsonBody.put("creditCard", creditCard);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d(TAG, "register success");

                            try {
                                Log.d(TAG, response.toString());

                                Intent intent = new Intent(context, MainActivity.class);

                                SharedPreferences sp = context.getSharedPreferences("Login", MODE_PRIVATE);
                                MainActivity.saveUserSession(sp, response.get("uuid").toString(), user.getUsername(),
                                        user.getName(), user.getEmail());

                                context.startActivity(intent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, "register error");
                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
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

    public static void login(final Context context, final String username, final String password, final String userPublicKey) {

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://" + server_ip +":3000/users/signin";

        final JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("username", username);
            jsonBody.put("password", password);
            jsonBody.put("publicKey", userPublicKey);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "login success");

                try {
                    Log.d(TAG, response.toString());

                    Intent intent = new Intent(context, MainActivity.class);

                    SharedPreferences sp = context.getSharedPreferences("Login", MODE_PRIVATE);
                    MainActivity.saveUserSession(sp, response.get("uuid").toString(), response.getString("username"),
                            response.getString("name"), response.getString("email"));

                    context.startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "login error");
            }
        });

        queue.add(jsonObjectRequest);
    }

}
