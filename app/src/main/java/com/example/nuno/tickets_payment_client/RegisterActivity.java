package com.example.nuno.tickets_payment_client;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.nuno.tickets_payment_client.fragments.LoginFragment;
import com.example.nuno.tickets_payment_client.fragments.RegisterFragment;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.register_activity);

        BottomNavigationView navigation = findViewById(R.id.register_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // first fragment active
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_register_container, new RegisterFragment()).commit();
    }
}
