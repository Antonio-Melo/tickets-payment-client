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

import com.example.nuno.tickets_payment_client.R;
import com.example.nuno.tickets_payment_client.RegisterActivity;

public class LoginFragment extends Fragment {

    private RegisterActivity registerActivity;

    private Button.OnClickListener mOnButtonClickListener
            = new Button.OnClickListener() {

        @Override
        public void onClick(View v) {

            String username = ((EditText)registerActivity.findViewById(R.id.login_username_input)).getText().toString();
            String password = ((EditText)registerActivity.findViewById(R.id.login_password_input)).getText().toString();

            // Call API

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
