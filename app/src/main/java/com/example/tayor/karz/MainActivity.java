package com.example.tayor.karz;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tayor.karz.Model.Car;

public class MainActivity extends AppCompatActivity implements
        CarFragment.OnListFragmentInteractionListener,BookCarFragment.OnFragmentInteractionListener,PaymentFragment.OnPaymentFragmentInteractionListener {

    private TextView mTextMessage;
    private CardView cardView;
    private FrameLayout frameLayout;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.s_list_of_available_cars);
                    if(getSupportFragmentManager().findFragmentByTag("carfrag") == null)
                        removeFragment();
                        showFragment();
                    return true;
                case R.id.book_car:
                    mTextMessage.setText(R.string.book_car);
                    removeFragment();
                    bookCarFragment();
                    return true;
                case R.id.history:
                    mTextMessage.setText(R.string.history);
                    removeFragment();
                    return true;
                case R.id.currently_booked:
                    mTextMessage.setText(R.string.currently_booked);
                    removeFragment();
                    return true;
            }
            return false;
        }
    };

    void removeFragment(){
        if(getSupportFragmentManager().findFragmentByTag("carfrag") != null)
             getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentByTag("carfrag")).commit();
        if(getSupportFragmentManager().findFragmentByTag("bookcar") != null)
            getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentByTag("bookcar")).commit();
    }

    void showFragment(){
        CarFragment carFragment = new CarFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame,carFragment,"carfrag")
                .commit();

    }

    void bookCarFragment(){
        BookCarFragment bookCarFragment = new BookCarFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame,bookCarFragment,"bookcar").commit();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // frameLayout = findViewById(R.id.frame2);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        cardView = findViewById(R.id.card);
        //showFragment();


    }

    @Override
    public void onListFragmentInteraction(Car car) {
        Toast.makeText(this, car.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFragmentInteraction() {
        PaymentFragment paymentFragment = new PaymentFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame,paymentFragment,"payment").commit();
    }

    @Override
    public void onPaymentFragmentInteraction(Uri uri) {

    }
}
