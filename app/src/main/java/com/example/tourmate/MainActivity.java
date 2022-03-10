package com.example.tourmate;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity implements LoginAuth.UserAuthListener, LoginAuth.UserRegListener, RegAuth.UserLoginListener, RegAuth.UserRegListener{


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_trip:
                    replaceFragment(new TripFragment());
                    return true;
                case R.id.navigation_camera:
                    replaceFragment(new MemoriesFragment());
                    return true;
                case R.id.navigation_wallet:
                    replaceFragment(new WalletFragment());
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        replaceFragment(new LoginAuth());


    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentContainer, fragment);
        ft.commit();
    }

    @Override
    public void onLoginSuccessfull() {
        replaceFragment(new TripFragment());
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setVisibility(View.VISIBLE);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    @Override
    public void onRegSuccesfull() {
        replaceFragment(new RegAuth());
    }

    @Override
    public void onlogSuccessfullsignup() {
        replaceFragment(new LoginAuth());

    }

    @Override
    public void onregSuccessfullsignup() {

    }

}
