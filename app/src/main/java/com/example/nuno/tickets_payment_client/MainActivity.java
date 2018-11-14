package com.example.nuno.tickets_payment_client;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.example.nuno.tickets_payment_client.fragments.CafetariaFragment;
import com.example.nuno.tickets_payment_client.fragments.HomeFragment;
import com.example.nuno.tickets_payment_client.fragments.ShowsFragment;
import com.example.nuno.tickets_payment_client.logic.User;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private User user;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item .getItemId()) {
                case R.id.navigation_home:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.navigation_shows:
                    selectedFragment = new ShowsFragment();
                    break;
                case R.id.navigation_cafetaria:
                    selectedFragment = new CafetariaFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Log.d("MAIN", "Main a ser chamada");
       /* if (getIntent().hasExtra("user")) {
            Bundle bundle = getIntent().getBundleExtra("user");

            User user = new User();

            user.setUserUUID(UUID.fromString(bundle.getString("uuid")));
            user.setUsername(bundle.getString("username"));
            user.setName(bundle.getString("name"));
            user.setPassword(bundle.getString("password"));
            user.setEmail(bundle.getString("email"));
            user.setNif(bundle.getString("nif"));*/

            User user = new User();
            user.setName("Nuno Ramos");
            Fragment selectedFragment = HomeFragment.newInstance(user);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
      /*  }
        else {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
            finish();
        }*/
    }

    public User getUser() {
        return user;
    }
}
