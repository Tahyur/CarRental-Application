package com.example.tayor.karz.views.receipt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tayor.karz.BaseActivity;
import com.example.tayor.karz.R;
import com.example.tayor.karz.views.MainActivity;

public class ReceiptActivity extends BaseActivity {
private Button ok_bn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_receipt);

        ok_bn = findViewById(R.id.ok);
        ok_bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homepage();
            }
        });
    }

    private void homepage() {
        Intent intent = new Intent(ReceiptActivity.this,MainActivity.class);
        startActivity(intent);
    }
}
