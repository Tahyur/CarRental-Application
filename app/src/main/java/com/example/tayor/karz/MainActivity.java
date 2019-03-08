package com.example.tayor.karz;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.tayor.karz.Model.Car;
import com.example.tayor.karz.dummy.DummyContent;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends BaseActivity implements
        CarFragment.OnListFragmentInteractionListener,HistoryFragment.OnHistoryListFragmentInteractionListener {
   // private FirebaseAuth mAuth;
    private TextView mTextMessage;
    private TextView mOptionalMessage;
  //  private android.support.v7.widget.Toolbar mTopToolbar;
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
                    if(getSupportFragmentManager().findFragmentByTag("carfrag") == null)
                        removeFragment();
                        showFragment();
                    return true;
                case R.id.history:
                    mTextMessage.setText(R.string.history);
                    mOptionalMessage.setVisibility(View.VISIBLE);
                    if(getSupportFragmentManager().findFragmentByTag("hisfrag") == null)
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

    void removeFragment(){
        if(getSupportFragmentManager().findFragmentByTag("carfrag") != null)
             getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentByTag("carfrag")).commit();
        if(getSupportFragmentManager().findFragmentByTag("hisfrag") != null)
            getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentByTag("hisfrag")).commit();
    }

    void showFragment(){
        CarFragment carFragment = new CarFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame,carFragment,"carfrag")
                .commit();
    }

    void showHistoryFragment(){
        HistoryFragment historyFragment = new HistoryFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame,historyFragment,"hisfrag")
                .commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    //    mAuth = FirebaseAuth.getInstance();
   //     mTopToolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(mTopToolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTextMessage = findViewById(R.id.message);
        mOptionalMessage = findViewById(R.id.optional_text);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        cardView = findViewById(R.id.card);
        showFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.book_car_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch(item.getItemId()){
            case R.id.profile:
                Toast.makeText(this, "profile selected", Toast.LENGTH_SHORT).show();
                intent = new Intent(MainActivity.this,ProfileActivity.class);
                startActivity(intent);
                return true;
            case R.id.log_out:
                FirebaseAuth.getInstance().signOut();
                intent = new Intent(MainActivity.this,SignInActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListFragmentInteraction(Car car) {
        Toast.makeText(this, car.getName(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this,BookCarActivity.class);
        intent.putExtra(getString(R.string.s_car_tag),car);
        startActivity(intent);
    }

    @Override
    public void onHistoryListFragmentInteraction(DummyContent.DummyItem item) {
        Intent intent = new Intent(MainActivity.this,HistoryDetailsActivity.class);
        intent.putExtra("item", item);
        startActivity(intent);
    }

//    @Override
//    public void onFragmentInteraction() {
//        PaymentFragment paymentFragment = new PaymentFragment();
//        getSupportFragmentManager().beginTransaction().replace(R.id.frame,paymentFragment,"payment").commit();
//    }

}
