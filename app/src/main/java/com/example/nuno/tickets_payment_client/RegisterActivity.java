package com.example.nuno.tickets_payment_client;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.nuno.tickets_payment_client.fragments.LoginFragment;
import com.example.nuno.tickets_payment_client.fragments.RegisterFragment;
import com.example.nuno.tickets_payment_client.logic_objects.User;

public class RegisterActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item .getItemId()) {
                case R.id.navigation_register:
                    selectedFragment = new RegisterFragment();
                    break;
                case R.id.navigation_login:
                    selectedFragment = new LoginFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_register_container, selectedFragment).commit();

            return true;
        }
    };

    private Button.OnClickListener mOnButtonClickListener
            = new Button.OnClickListener() {

        @Override
        public void onClick(View v) {
            User user = new User();

            user.setName(((EditText)findViewById(R.id.register_name_input)).getText().toString());
            user.setEmail(((EditText)findViewById(R.id.register_email_input)).getText().toString());
            user.setNif(((EditText)findViewById(R.id.register_nif_input)).getText().toString());
            user.setUsername(((EditText)findViewById(R.id.register_username_input)).getText().toString());
            user.setPassword(((EditText)findViewById(R.id.register_password_input)).getText().toString());
            user.setCreditCardType(((Spinner)findViewById(R.id.register_credit_card_type_spinner)).getSelectedItem().toString());

            // Create key pair

            // Call API

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.register_activity);

        BottomNavigationView navigation = findViewById(R.id.register_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // first fragment active
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_register_container, new RegisterFragment()).commit();

        // click listener create account button
        findViewById(R.id.register_create_account_button).setOnClickListener(mOnButtonClickListener);
    }
}
