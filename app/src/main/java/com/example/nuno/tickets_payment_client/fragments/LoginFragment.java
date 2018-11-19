package com.example.nuno.tickets_payment_client.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.nuno.tickets_payment_client.R;
import com.example.nuno.tickets_payment_client.RegisterActivity;
import com.example.nuno.tickets_payment_client.logic.API;
import com.example.nuno.tickets_payment_client.logic.User;

public class LoginFragment extends Fragment {

    private RegisterActivity registerActivity;

    private Button.OnClickListener mOnButtonClickListener
            = new Button.OnClickListener() {

        @Override
        public void onClick(View v) {

            ((TextView)registerActivity.findViewById(R.id.login_error)).setVisibility(View.INVISIBLE);

            String username = ((EditText)registerActivity.findViewById(R.id.login_username_input)).getText().toString();
            String password = ((EditText)registerActivity.findViewById(R.id.login_password_input)).getText().toString();

            User user = new User();
            user.setUsername(username);
            registerActivity.generateAndStoreKeys(user);

            API.login(LoginFragment.this, username, password, user.getUserPublicKey());
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        registerActivity = (RegisterActivity)getActivity();

        // click listener to login button
        registerActivity.findViewById(R.id.login_button).setOnClickListener(mOnButtonClickListener);
    }
}
