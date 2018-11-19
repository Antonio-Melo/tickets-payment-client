package com.example.nuno.tickets_payment_client;

import android.os.Bundle;
import android.security.KeyPairGeneratorSpec;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;

import com.example.nuno.tickets_payment_client.fragments.LoginFragment;
import com.example.nuno.tickets_payment_client.fragments.RegisterFragment;
import com.example.nuno.tickets_payment_client.logic.User;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.security.auth.x500.X500Principal;

public class RegisterActivity extends AppCompatActivity {

    static final int KEY_SIZE = 2048;
    static final String ANDROID_KEYSTORE = "AndroidKeyStore";
    static final String KEY_ALGO = "RSA";
    // static final String SIGN_ALGO = "SHA256WithRSA";
    private final String TAG = "RegisterActivity";

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
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_register_container, new LoginFragment()).commit();
    }

    public void generateAndStoreKeys(User user) {
        try {
            KeyStore ks = KeyStore.getInstance(ANDROID_KEYSTORE);
            ks.load(null);
            KeyStore.Entry entry = ks.getEntry(user.getUsername(), null);
            if (entry == null) {
                Calendar start = new GregorianCalendar();
                Calendar end = new GregorianCalendar();
                end.add(Calendar.YEAR, 20);
                KeyPairGenerator kgen = KeyPairGenerator.getInstance(KEY_ALGO, ANDROID_KEYSTORE);
                AlgorithmParameterSpec spec = new KeyPairGeneratorSpec.Builder(this)
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
}
