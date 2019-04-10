package com.example.tayor.karz.views.history;

import android.os.Bundle;
import android.widget.TextView;

import com.example.tayor.karz.BaseActivity;
import com.example.tayor.karz.R;

public class HistoryDetailsActivity extends BaseActivity {
    TextView car_name,model_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_details);
        car_name = findViewById(R.id.car_name);
        model_name = findViewById(R.id.model_name);

//        DummyContent.DummyItem item = getIntent().getParcelableExtra("item");
//
//        car_name.setText(item.id);
//        model_name.setText(item.content);

    }
}
