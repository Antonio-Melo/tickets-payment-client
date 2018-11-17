package com.example.nuno.tickets_payment_client;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.nuno.tickets_payment_client.fragments.CafetariaFragment;
import com.example.nuno.tickets_payment_client.fragments.ShowsFragment;
import com.example.nuno.tickets_payment_client.fragments.TicketsFragment;
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
                    selectedFragment = new ShowsFragment();
                    break;
                case R.id.navigation_shows:
                    selectedFragment = new TicketsFragment();
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

        SharedPreferences sp = this.getSharedPreferences("Login", MODE_PRIVATE);
        if (sp.getBoolean("loggedIn", false)) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ShowsFragment()).commit();
        }
        else {
            changeToRegisterActivity();
        }
    }

    public void changeToRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.options_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.options_menu_sign_out_item:
                SharedPreferences sp = this.getSharedPreferences("Login", MODE_PRIVATE);
                removeUserSession(sp);
                changeToRegisterActivity();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public static void saveUserSession(SharedPreferences sp, String uuid, String username, String name, String email) {
        SharedPreferences.Editor Ed = sp.edit();

        Ed.putString("uuid", uuid);
        Ed.putString("username", username);
        Ed.putString("name", name);
        Ed.putString("email", email);
        Ed.putBoolean("loggedIn", true);

        Ed.commit();
    }

    public static User getUserSession(SharedPreferences sp) {
        User user = new User();

        user.setUserUUID(UUID.fromString(sp.getString("uuid", null)));
        user.setUsername(sp.getString("username", null));
        user.setName(sp.getString("name", null));
        user.setEmail(sp.getString("email", null));

        return user;
    }

    public static void removeUserSession(SharedPreferences sp) {
        SharedPreferences.Editor Ed = sp.edit();

        Ed.putString("uuid", null);
        Ed.putString("username", null);
        Ed.putString("name", null);
        Ed.putString("email", null);
        Ed.putBoolean("loggedIn", false);

        Ed.commit();
    }
}
