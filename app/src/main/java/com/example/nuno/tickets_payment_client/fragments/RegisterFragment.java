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
import android.widget.Spinner;

import com.example.nuno.tickets_payment_client.R;
import com.example.nuno.tickets_payment_client.RegisterActivity;
import com.example.nuno.tickets_payment_client.logic_objects.User;

public class RegisterFragment extends Fragment {

    private RegisterActivity registerActivity;

    private Button.OnClickListener mOnButtonClickListener
            = new Button.OnClickListener() {

        @Override
        public void onClick(View v) {
            User user = new User();

            user.setName(((EditText)registerActivity.findViewById(R.id.register_name_input)).getText().toString());
            user.setEmail(((EditText)registerActivity.findViewById(R.id.register_email_input)).getText().toString());
            user.setNif(((EditText)registerActivity.findViewById(R.id.register_nif_input)).getText().toString());
            user.setUsername(((EditText)registerActivity.findViewById(R.id.register_username_input)).getText().toString());
            user.setPassword(((EditText)registerActivity.findViewById(R.id.register_password_input)).getText().toString());
            user.setCreditCardType(((Spinner)registerActivity.findViewById(R.id.register_credit_card_type_spinner)).getSelectedItem().toString());

            // Create key pair

            // Call API

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

        // click listener create account button
        registerActivity.findViewById(R.id.register_create_account_button).setOnClickListener(mOnButtonClickListener);
    }
}
