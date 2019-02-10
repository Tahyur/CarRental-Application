package com.example.deeppatel.car_rerntal;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Stack;

public class Home extends AppCompatActivity {

    private TextView mTextMessage;
    private android.support.v7.app.ActionBar actionBar;
    private Stack fragmentStack;
    private BottomNavigationView navigation;
    //Get the fragment manipulators ready for the transaction
    FragmentManager fragmentManager = getSupportFragmentManager();

    //Based on the button clicks, the fragments must be added to the activity

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    addFragment(item.getItemId());

                    return true;

                case R.id.navigation_customer:

                    addFragment(item.getItemId());

                    return true;

                case R.id.navigation_car:

                    addFragment(item.getItemId());

                    return true;

                case R.id.navigation_rent:

                    addFragment(item.getItemId());

                    return true;

                case R.id.navigation_return_car:

                    addFragment(item.getItemId());

                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        actionBar = getSupportActionBar();;

        mTextMessage = (TextView) findViewById(R.id.message);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Initialize the stack
        fragmentStack = new Stack();
        //Add HomeFragment object to it
        addFragment(R.id.navigation_home);

    }


    //Custom methods


    public void setActionBarTitle(String title){

        actionBar.setTitle(title);

    }

    public void setBottomNavigationItemChecked(int navId){

        switch (navId) {
            case R.id.navigation_home:

                navigation.getMenu().findItem(navId).setChecked(true);

                break;

            case R.id.navigation_customer:

                navigation.getMenu().findItem(navId).setChecked(true);

                break;

            case R.id.navigation_car:

                navigation.getMenu().findItem(navId).setChecked(true);

                break;

            case R.id.navigation_rent:

                navigation.getMenu().findItem(navId).setChecked(true);

                break;

            case R.id.navigation_return_car:

                navigation.getMenu().findItem(navId).setChecked(true);

                break;

        }

    }

    public  void addFragment(int fragmentId){

        FragmentTransaction fragmentTransaction;

        HomeFragment homeFragment;
        CustomerFragment customerFragment;
        RentACarFragment rentACarFragment;
        ReturnACarFragment returnACarFragment;
        CarFragment carFragment;

        //Based on the navigation button clicked, the fragments must be added to the activity

        switch (fragmentId){

            case R.id.navigation_home:

                fragmentTransaction = fragmentManager.beginTransaction();

                //Initialize the fragment
                homeFragment = new HomeFragment();
                //Add the fragment to screen
                fragmentTransaction.add(R.id.fragment_container, homeFragment);
                //Add the fragment to custom fragment stack
                fragmentStack.push(homeFragment);
                fragmentTransaction.commit();

                break;

            case R.id.navigation_customer:

                fragmentTransaction = fragmentManager.beginTransaction();

                //Initialize the fragment
                customerFragment = new CustomerFragment();
                //Add the fragment to screen
                fragmentTransaction.add(R.id.fragment_container, customerFragment);
                //Add the fragment to custom fragment stack
                fragmentStack.push(customerFragment);
                fragmentTransaction.commit();

                break;

            case R.id.navigation_car:

                fragmentTransaction = fragmentManager.beginTransaction();

                //Initialize the fragment
                carFragment = new CarFragment();
                //Add the fragment to screen
                fragmentTransaction.add(R.id.fragment_container, carFragment);
                //Add the fragment to custom fragment stack
                fragmentStack.push(carFragment);
                fragmentTransaction.commit();

                break;

            case R.id.navigation_rent:

                fragmentTransaction = fragmentManager.beginTransaction();

                //Initialize the fragment
                rentACarFragment = new RentACarFragment();
                //Add the fragment to screen
                fragmentTransaction.add(R.id.fragment_container, rentACarFragment);
                //Add the fragment to custom fragment stack
                fragmentStack.push(rentACarFragment);
                fragmentTransaction.commit();

                break;

            case R.id.navigation_return_car:

                fragmentTransaction = fragmentManager.beginTransaction();

                //Initialize the fragment
                returnACarFragment = new ReturnACarFragment();
                //Add the fragment to screen
                fragmentTransaction.add(R.id.fragment_container, returnACarFragment);
                //Add the fragment to custom fragment stack
                fragmentStack.push(returnACarFragment);
                fragmentTransaction.commit();

                break;

        }

    }

}
