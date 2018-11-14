package com.example.nuno.tickets_payment_client.logic;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nuno.tickets_payment_client.MainActivity;
import com.example.nuno.tickets_payment_client.NextShowsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class API {

    private static final String TAG = "API";

    public static void postRequest(RequestQueue queue, String url, final JSONObject jsonBody, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                responseListener, errorListener){
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
    }

    public static void getRequest(RequestQueue queue, String url, final Map<String, String> params, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                responseListener, errorListener){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/json");
                return params;
            }
        };

        queue.add(jsonObjectRequest);
    }

    public static void getShows(final NextShowsActivity nextShowsActivity){
        RequestQueue queue = Volley.newRequestQueue(nextShowsActivity.getBaseContext());
        String url = "http://10.0.2.2:3000/shows/";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, "sucesso");
                Log.d(TAG, response.toString());
                nextShowsActivity.setNextShows(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "error");
            }
        });

        queue.add(jsonArrayRequest);
    }

    public static void register(final Context context, final User user) {

        RequestQueue queue = Volley.newRequestQueue(context);
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

            postRequest(queue, url, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d(TAG, response.toString());

                    try {
                        Log.d(TAG, response.get("uuid").toString());
                        user.setUserUUID(UUID.fromString(response.get("uuid").toString()));

                        Intent intent = new Intent(context, MainActivity.class);

                        Bundle bundle = new Bundle();
                        bundle.putString("uuid", response.get("uuid").toString());
                        bundle.putString("username", user.getUsername());
                        bundle.putString("name", user.getName());
                        bundle.putString("password", user.getPassword());
                        bundle.putString("email", user.getEmail());
                        bundle.putString("nif", user.getNif());
                        intent.putExtra("user", bundle);
                        context.startActivity(intent);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, error.toString());
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
