package com.example.tayor.karz.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tayor.karz.BaseActivity;
import com.example.tayor.karz.Model.Car;
import com.example.tayor.karz.R;
import com.example.tayor.karz.views.car.BookCarActivity;
import com.example.tayor.karz.views.car.CarFragment;
import com.example.tayor.karz.views.history.HistoryFragment;
import com.example.tayor.karz.views.profile.ProfileActivity;
import com.example.tayor.karz.views.sign_in.SignInActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.io.File;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class MainActivity extends BaseActivity implements
        CarFragment.OnListFragmentInteractionListener,HistoryFragment.OnHistoryListFragmentInteractionListener {
    private TextView mTextMessage;
    private TextView mOptionalMessage;
    private CardView cardView;
    private FrameLayout frameLayout;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.s_list_of_available_cars);
                    mOptionalMessage.setVisibility(View.INVISIBLE);
                    if (getSupportFragmentManager().findFragmentByTag("carfrag") == null)
                        removeFragment();
                    showFragment();
                    return true;
                case R.id.history:
                    mTextMessage.setText(R.string.history);
                    mOptionalMessage.setVisibility(View.VISIBLE);
                    if (getSupportFragmentManager().findFragmentByTag("hisfrag") == null)
                        removeFragment();
                    showHistoryFragment();
                    return true;
                case R.id.currently_booked:
                    mTextMessage.setText(R.string.currently_booked);
                    mOptionalMessage.setVisibility(View.INVISIBLE);
                    removeFragment();
                    return true;
            }
            return false;
        }
    };

    void removeFragment() {
        if (getSupportFragmentManager().findFragmentByTag("carfrag") != null)
            getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentByTag("carfrag")).commit();
        if (getSupportFragmentManager().findFragmentByTag("hisfrag") != null)
            getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentByTag("hisfrag")).commit();
    }

    void showFragment() {
        CarFragment carFragment = new CarFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame, carFragment, "carfrag")
                .commit();
    }

    void showHistoryFragment() {
        HistoryFragment historyFragment = new HistoryFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame, historyFragment, "hisfrag")
                .commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        Log.d("absolutePath", rootPath);
        RealmConfiguration configuration = new RealmConfiguration.Builder().name("localdata").directory(new File(rootPath + "/realm")).build();
        Realm.setDefaultConfiguration(configuration);

        mTextMessage = findViewById(R.id.message);
        mOptionalMessage = findViewById(R.id.optional_text);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        cardView = findViewById(R.id.card);
        showFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.book_car_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.profile:
                Toast.makeText(this, "profile selected", Toast.LENGTH_SHORT).show();
                intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
                return true;
            case R.id.log_out:
                FirebaseAuth.getInstance().signOut();
                intent = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListFragmentInteraction(Car car) {
        Toast.makeText(this, car.getName(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, BookCarActivity.class);
        intent.putExtra(getString(R.string.s_car_tag), car);
        startActivity(intent);
    }

    @Override
    public void onHistoryListFragmentInteraction() {

    }
}
