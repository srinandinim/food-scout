package com.example.srina.finalproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

public class SearchScreen extends FragmentActivity {

    BottomNavigationView navigation;
    ConstraintLayout constraintLayout;

    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_screen);

        navigation = findViewById(R.id.navigation);
        constraintLayout = findViewById(R.id.constraintLayout);

        final EnterInfo enterInfo = new EnterInfo();
        final Favorites favorites = new Favorites();
        final Results results = new Results();
        final PlainResults plainResults = new PlainResults();

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.constraintLayout, enterInfo);
        fragmentTransaction.commit();

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_search:
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.constraintLayout, enterInfo);
                        fragmentTransaction.commit();
                        return true;
                    case R.id.navigation_results:
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.constraintLayout, plainResults);
                        fragmentTransaction.commit();
                        return true;
                    case R.id.navigation_favorites:
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.constraintLayout, favorites);
                        fragmentTransaction.commit();
                        return true;

                }
                return false;
            }
        });

    }

}
