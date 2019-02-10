package com.example.deeppatel.car_rerntal;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

public class Home extends AppCompatActivity {

    private TextView mTextMessage;
    android.support.v7.app.ActionBar actionBar;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                   // mTextMessage.setText(R.string.title_home);

                    actionBar.setTitle(R.string.title_home);

                    return true;
                case R.id.navigation_customer:
                    //mTextMessage.setText(R.string.title_customer);
                    actionBar.setTitle(R.string.title_customer);
                    return true;
                case R.id.navigation_car:
                    //mTextMessage.setText(R.string.title_car);
                    actionBar.setTitle(R.string.title_car);
                    return true;

                case R.id.navigation_rent:
                    //mTextMessage.setText(R.string.string_rent);
                    actionBar.setTitle(R.string.string_rent);
                    return true;

                case R.id.navigation_return_car:
                    //mTextMessage.setText(R.string.return_car);
                    actionBar.setTitle(R.string.return_car);
                    return true;

            }
            return false;
        }
    };

    Drawable searchButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        actionBar = getSupportActionBar();

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
